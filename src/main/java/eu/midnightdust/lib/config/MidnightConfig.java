package eu.midnightdust.lib.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

// MidnightConfig v0.1.0 //

/* Based on https://github.com/Minenash/TinyConfig
   Credits to Minenash - CC0-1.0
   You can copy this class to get a standalone version of MidnightConfig */

@SuppressWarnings("rawtypes")
public class MidnightConfig {

    private static final Pattern INTEGER_ONLY = Pattern.compile("(-?[0-9]*)");
    private static final Pattern DECIMAL_ONLY = Pattern.compile("-?([\\d]+\\.?[\\d]*|[\\d]*\\.?[\\d]+|\\.)");

    private static final List<EntryInfo> entries = new ArrayList<>();

    protected static class EntryInfo {
        Field field;
        Object widget;
        int width;
        Method dynamicTooltip;
        Map.Entry<TextFieldWidget,Text> error;
        Object defaultValue;
        Object value;
        String tempValue;
        boolean inLimits = true;
    }

    private static Class configClass;
    private static String translationPrefix;
    private static Path path;

    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .excludeFieldsWithModifiers(Modifier.PRIVATE)
            .setPrettyPrinting()
            .create();

    public static void init(String modid, Class<?> config) {
        translationPrefix = modid + ".midnightconfig.";
        path = FabricLoader.getInstance().getConfigDir().resolve(modid + ".json");
        configClass = config;

        for (Field field : config.getFields()) {
            Class<?> type = field.getType();
            EntryInfo info = new EntryInfo();

            Entry e;
            try { e = field.getAnnotation(Entry.class); }
            catch (Exception ignored) { continue; }

            info.width = e.width();
            info.field = field;

            if (type == int.class)         textField(info, Integer::parseInt, INTEGER_ONLY, e.min(), e.max(), true);
            else if (type == double.class) textField(info, Double::parseDouble, DECIMAL_ONLY, e.min(), e.max(),false);
            else if (type == String.class) textField(info, String::length, null, Math.min(e.min(),0), Math.max(e.max(),1),true);
            else if (type == boolean.class) {
                Function<Object,Text> func = value -> new LiteralText((Boolean) value ? "True" : "False").formatted((Boolean) value ? Formatting.GREEN : Formatting.RED);
                info.widget = new AbstractMap.SimpleEntry<ButtonWidget.PressAction, Function<Object, Text>>(button -> {
                    info.value = !(Boolean) info.value;
                    button.setMessage(func.apply(info.value));
                }, func);
            }
            else if (type.isEnum()) {
                List<?> values = Arrays.asList(field.getType().getEnumConstants());
                Function<Object,Text> func = value -> new TranslatableText(translationPrefix + "enum." + type.getSimpleName() + "." + info.value.toString());
                info.widget = new AbstractMap.SimpleEntry<ButtonWidget.PressAction, Function<Object,Text>>( button -> {
                    int index = values.indexOf(info.value) + 1;
                    info.value = values.get(index >= values.size()? 0 : index);
                    button.setMessage(func.apply(info.value));
                }, func);
            }
            else
                continue;

            entries.add(info);

            try { info.defaultValue = field.get(null); }
            catch (IllegalAccessException ignored) {}

            try {
                info.dynamicTooltip = config.getMethod(e.dynamicTooltip());
                info.dynamicTooltip.setAccessible(true);
            } catch (Exception ignored) {}

        }

        try { gson.fromJson(Files.newBufferedReader(path), config); }
        catch (Exception e) { write(); }

        for (EntryInfo info : entries) {
            try {
                info.value = info.field.get(null);
                info.tempValue = info.value.toString();
            }
            catch (IllegalAccessException ignored) {}
        }

    }

    private static void textField(EntryInfo info, Function<String,Number> f, Pattern pattern, double min, double max, boolean cast) {
        boolean isNumber = pattern != null;
        info.widget = (BiFunction<TextFieldWidget, ButtonWidget, Predicate<String>>) (t, b) -> s -> {
            s = s.trim();
            if (!(s.isEmpty() || !isNumber || pattern.matcher(s).matches()))
                return false;

            Number value = 0;
            boolean inLimits = false;
            System.out.println(((isNumber ^ s.isEmpty())));
            System.out.println(!s.equals("-") && !s.equals("."));
            info.error = null;
            if (!(isNumber && s.isEmpty()) && !s.equals("-") && !s.equals(".")) {
                value = f.apply(s);
                inLimits = value.doubleValue() >= min && value.doubleValue() <= max;
                info.error = inLimits? null : new AbstractMap.SimpleEntry<>(t, new LiteralText(value.doubleValue() < min ?
                        "§cMinimum " + (isNumber? "value" : "length") + (cast? " is " + (int)min : " is " + min) :
                        "§cMaximum " + (isNumber? "value" : "length") + (cast? " is " + (int)max : " is " + max)));
            }

            info.tempValue = s;
            t.setEditableColor(inLimits? 0xFFFFFFFF : 0xFFFF7777);
            info.inLimits = inLimits;
            b.active = entries.stream().allMatch(e -> e.inLimits);

            if (inLimits)
                info.value = isNumber? value : s;

            return true;
        };
    }

    public static void write() {
        try {
            if (!Files.exists(path)) Files.createFile(path);
            Files.write(path, gson.toJson(configClass.newInstance()).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Screen getScreen(Screen parent) {
        return new TinyConfigScreen(parent);
    }

    private static class TinyConfigScreen extends Screen {
        protected TinyConfigScreen(Screen parent) {
            super(new TranslatableText(MidnightConfig.translationPrefix + "title"));
            this.parent = parent;
        }
        private final Screen parent;

        // Real Time config update //
        @Override
        public void tick() {
            for (EntryInfo info : entries)
                try { info.field.set(null, info.value); }
                catch (IllegalAccessException ignore) {}
        }

        @Override
        protected void init() {
            super.init();
            this.addButton(new ButtonWidget(this.width / 2 - 154, this.height - 28, 150, 20, ScreenTexts.CANCEL, button -> {
                try { gson.fromJson(Files.newBufferedReader(path), configClass); }
                catch (Exception e) { write(); }

                for (EntryInfo info : entries) {
                    try {
                        info.value = info.field.get(null);
                        info.tempValue = info.value.toString();
                    }
                    catch (IllegalAccessException ignored) {}
                }
                    Objects.requireNonNull(client).openScreen(parent);
            }));

            ButtonWidget done = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height - 28, 150, 20, ScreenTexts.DONE, (button) -> {
                for (EntryInfo info : entries)
                    try { info.field.set(null, info.value); }
                    catch (IllegalAccessException ignore) {}
                write();
                Objects.requireNonNull(client).openScreen(parent);
            }));

            int y = 45;
            for (EntryInfo info : entries) {
                addButton(new ButtonWidget(width - 155, y, 40,20, new LiteralText("Reset").formatted(Formatting.RED), (button -> {
                    info.value = info.defaultValue;
                    info.tempValue = info.value.toString();
                    Objects.requireNonNull(client).openScreen(this);
                })));

                if (info.widget instanceof Map.Entry) {
                    Map.Entry<ButtonWidget.PressAction,Function<Object,Text>> widget = (Map.Entry<ButtonWidget.PressAction, Function<Object, Text>>) info.widget;
                    addButton(new ButtonWidget(width-110,y,info.width,20, widget.getValue().apply(info.value), widget.getKey()));
                }
                else {
                    TextFieldWidget widget = addButton(new TextFieldWidget(textRenderer, width-110, y, info.width, 20, null));
                    widget.setText(info.tempValue);

                    Predicate<String> processor = ((BiFunction<TextFieldWidget, ButtonWidget, Predicate<String>>) info.widget).apply(widget,done);
                    widget.setTextPredicate(processor);

                    children.add(widget);
                }
                y += 25;
            }

        }
        int aniX = this.width / 2;
        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            this.renderBackground(matrices);

            if (aniX < this.width / 2) {
                aniX = aniX +40;
            }

            int stringWidth = (int) (title.getString().length() * 2.75f);
            this.fillGradient(matrices, this.width / 2 - stringWidth, 10, this.width /2 + stringWidth, 29, -1072689136, -804253680);
            this.fillGradient(matrices, this.width / 2 - aniX, 35, width/2 + aniX, this.height - 40, -1072689136, -804253680);

            super.render(matrices, mouseX, mouseY, delta);
            drawCenteredText(matrices, textRenderer, title, width/2, 15, 0xFFFFFF);

            int y = 40;
            for (EntryInfo info : entries) {
                drawTextWithShadow(matrices, textRenderer, new TranslatableText(translationPrefix + info.field.getName()), 12, y + 10, 0xFFFFFF);

                if (info.error != null && info.error.getKey().isMouseOver(mouseX,mouseY))
                    renderTooltip(matrices, info.error.getValue(), mouseX, mouseY);
                else if (mouseY >= y && mouseY < (y + 25)) {
                    if (info.dynamicTooltip != null) {
                        try {
                            renderTooltip(matrices, (List<Text>) info.dynamicTooltip.invoke(null, entries), mouseX, mouseY);
                            y += 25;
                            continue;
                        } catch (Exception e) { e.printStackTrace(); }
                    }
                    String key = translationPrefix + info.field.getName() + ".tooltip";
                    if (I18n.hasTranslation(key)) {
                        List<Text> list = new ArrayList<>();
                        for (String str : I18n.translate(key).split("\n"))
                            list.add(new LiteralText(str));
                        renderTooltip(matrices, list, mouseX, mouseY);
                    }
                }
                y += 25;
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Entry {
        String dynamicTooltip() default "";
        int width() default 100;
        double min() default Double.MIN_NORMAL;
        double max() default Double.MAX_VALUE;
    }
}
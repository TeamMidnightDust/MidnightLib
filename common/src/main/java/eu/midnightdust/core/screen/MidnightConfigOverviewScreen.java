package eu.midnightdust.core.screen;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.*;
import java.util.*;

@Environment(EnvType.CLIENT)
public class MidnightConfigOverviewScreen extends Screen {

    public MidnightConfigOverviewScreen(Screen parent) {
        super(Text.translatable( "midnightlib.overview.title"));
        this.parent = parent;
    }
    private final Screen parent;
    private MidnightOverviewListWidget list;

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> Objects.requireNonNull(client).setScreen(parent)).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());

        this.list = new MidnightOverviewListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        if (this.client != null && this.client.world != null) this.list.setRenderBackground(false);
        this.addSelectableChild(this.list);
        List<String> sortedMods = new ArrayList<>(MidnightConfig.configClass.keySet());
        Collections.sort(sortedMods);
        sortedMods.forEach((modid) -> {
            if (!MidnightLibClient.hiddenMods.contains(modid)) {
                list.addButton(ButtonWidget.builder(Text.translatable(modid +".midnightconfig.title"), (button) ->
                        Objects.requireNonNull(client).setScreen(MidnightConfig.getScreen(this,modid))).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());
            }
        });
        super.init();
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredTextWithShadow(matrices, textRenderer, title, width / 2, 15, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
    @Environment(EnvType.CLIENT)
    public static class MidnightOverviewListWidget extends ElementListWidget<OverviewButtonEntry> {
        TextRenderer textRenderer;

        public MidnightOverviewListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
            super(minecraftClient, i, j, k, l, m);
            this.centerListVertically = false;
            textRenderer = minecraftClient.textRenderer;
        }
        @Override
        public int getScrollbarPositionX() {return this.width -7;}

        public void addButton(ClickableWidget button) {
            this.addEntry(OverviewButtonEntry.create(button));
        }
        @Override
        public int getRowWidth() { return 400; }
    }
    public static class OverviewButtonEntry extends ElementListWidget.Entry<OverviewButtonEntry> {
        private final ClickableWidget button;
        private final List<ClickableWidget> buttonList = new ArrayList<>();

        private OverviewButtonEntry(ClickableWidget button) {
            this.button = button;
            this.buttonList.add(button);
        }
        public static OverviewButtonEntry create(ClickableWidget button) {return new OverviewButtonEntry(button);}
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            button.setY(y);
            button.render(matrices, mouseX, mouseY, tickDelta);
        }
        public List<? extends Element> children() {return buttonList;}
        public List<? extends Selectable> selectableChildren() {return buttonList;}
    }
}
package eu.midnightdust.core.screen;

import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static eu.midnightdust.lib.config.MidnightConfig.MidnightConfigListWidget;

@Environment(EnvType.CLIENT)
public class MidnightConfigOverviewScreen extends Screen {

    public MidnightConfigOverviewScreen(Screen parent) {
        super(Text.translatable( "midnightlib.overview.title"));
        this.parent = parent;
    }
    private final Screen parent;
    private MidnightConfigListWidget list;

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> Objects.requireNonNull(client).setScreen(parent)).dimensions(this.width / 2 - 100, this.height - 26, 200, 20).build());

        this.addSelectableChild(this.list = new MidnightConfigListWidget(this.client, this.width, this.height - 57, 24, 25));
        List<String> sortedMods = new ArrayList<>(MidnightConfig.configClass.keySet());
        Collections.sort(sortedMods);
        sortedMods.forEach((modid) -> {
            if (!MidnightLib.hiddenMods.contains(modid)) {
                list.addButton(List.of(ButtonWidget.builder(Text.translatable(modid +".midnightconfig.title"), (button) ->
                        Objects.requireNonNull(client).setScreen(MidnightConfig.getScreen(this, modid))).dimensions(this.width / 2 - 125, this.height - 28, 250, 20).build()), null, null);
        }});
        super.init();
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 10, 0xFFFFFF);
    }
}
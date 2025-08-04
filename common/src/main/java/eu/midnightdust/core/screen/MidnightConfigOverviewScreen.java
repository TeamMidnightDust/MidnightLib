package eu.midnightdust.core.screen;

import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.config.MidnightConfig.MidnightConfigListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
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
    private MidnightConfigListWidget list;

    @Override
    protected void init() {
        this.addDrawableChild(
                ButtonWidget.builder(
                        ScreenTexts.DONE,
                        (button) ->
                                Objects.requireNonNull(client).setScreen(parent))
                        .dimensions(this.width / 2 - 100, this.height - 28, 200, 20)
                        .build());

        List<String> sortedMods = new ArrayList<>(MidnightConfig.configClass.keySet());
        Collections.sort(sortedMods);
        list = new MidnightConfigListWidget(this.client, this.width, this.height, this.height - 57, 25);
        sortedMods.forEach((modid) -> {
            if (!MidnightLib.hiddenMods.contains(modid)) {
                list.addButton(List.of(ButtonWidget.builder(Text.translatable(modid +".midnightconfig.title"), (button) ->
                        Objects.requireNonNull(client).setScreen(MidnightConfig.getScreen(this, modid))).dimensions(this.width / 2 - 125, this.height - 28, 250, 20).build()), null, null);
            }});
        if (this.client != null && this.client.world != null) this.list.setRenderBackground(false);
        this.addSelectableChild(this.list);
        super.init();

    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);

    }
}
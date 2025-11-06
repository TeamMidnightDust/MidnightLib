package eu.midnightdust.core.screen;

import eu.midnightdust.core.MidnightLib;
import eu.midnightdust.lib.config.MidnightConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import eu.midnightdust.lib.config.MidnightConfigListWidget;

public class MidnightConfigOverviewScreen extends Screen {

    public MidnightConfigOverviewScreen(Screen parent) {
        super(Component.translatable( "midnightlib.overview.title"));
        this.parent = parent;
    }
    private final Screen parent;
    private MidnightConfigListWidget list;

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> Objects.requireNonNull(minecraft).setScreen(parent)).bounds(this.width / 2 - 100, this.height - 26, 200, 20).build());

        this.addWidget(this.list = new MidnightConfigListWidget(this.minecraft, this.width, this.height - 57, 24, 25));
        List<String> sortedMods = new ArrayList<>(MidnightConfig.configInstances.keySet());
        Collections.sort(sortedMods);
        sortedMods.forEach((modid) -> {
            if (!MidnightLib.hiddenMods.contains(modid)) {
                list.addButton(List.of(Button.builder(Component.translatable(modid +".midnightconfig.title"), (button) ->
                        Objects.requireNonNull(minecraft).setScreen(MidnightConfig.getScreen(this, modid))).bounds(this.width / 2 - 125, this.height - 28, 250, 20).build()), null, null);
        }});
        super.init();
    }
    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        //? if >= 1.21 {
         super.render(context, mouseX, mouseY, delta);
        //?} else {
        /*super.renderBackground(context);
        *///?}
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredString(font, title, width / 2, 10, 0xFFFFFFFF);
        //? if < 1.21
        /*super.render(context, mouseX, mouseY, delta);*/
    }
}
package eu.midnightdust.core.screen;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
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
    private MidnightConfig.MidnightConfigListWidget list;

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> Objects.requireNonNull(client).setScreen(parent)).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());

        this.list = new MidnightConfig.MidnightConfigListWidget(this.client, this.width, this.height - 64, 32, 25);
        if (this.client != null && this.client.world != null) this.list.setRenderBackground(false);
        this.addSelectableChild(this.list);
        List<String> sortedMods = new ArrayList<>(MidnightConfig.configClass.keySet());
        Collections.sort(sortedMods);
        sortedMods.forEach((modid) -> {
            if (!MidnightLibClient.hiddenMods.contains(modid)) {
                list.addButton(List.of(ButtonWidget.builder(Text.translatable(modid +".midnightconfig.title"), (button) ->
                        Objects.requireNonNull(client).setScreen(MidnightConfig.getScreen(this,modid))).dimensions(this.width / 2 - 125, this.height - 28, 250, 20).build()), null, null);
            }
        });
        super.init();
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (client != null && client.world != null) super.renderInGameBackground(context);
        this.list.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, 15, 0xFFFFFF);
    }
    @Override public void renderBackground(DrawContext c, int x, int y, float d) {}
}
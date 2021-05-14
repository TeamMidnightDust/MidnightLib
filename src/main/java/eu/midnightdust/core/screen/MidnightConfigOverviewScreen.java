package eu.midnightdust.core.screen;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.*;
import java.util.*;

@Environment(EnvType.CLIENT)
public class MidnightConfigOverviewScreen extends Screen {

    public MidnightConfigOverviewScreen(Screen parent) {
        super(new TranslatableText( "midnightlib.overview.title"));
        this.parent = parent;
    }
    private final Screen parent;
    private MidnightOverviewListWidget list;

    @Override
    protected void init() {
        super.init();

        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 28, 200, 20, ScreenTexts.DONE, (button) -> {
            Objects.requireNonNull(client).openScreen(parent);
        }));

        this.list = new MidnightOverviewListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.children.add(this.list);
        MidnightConfig.configClass.forEach((modid, configClass) -> {
            list.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 28, 200, 20, new TranslatableText(modid +".midnightconfig.title"), (button) -> {
                Objects.requireNonNull(client).openScreen(MidnightConfig.getScreen(this,modid));
            }));
        });
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);

        int stringWidth = title.getString().length() + 47;
        super.render(matrices, mouseX, mouseY, delta);
        if (MidnightConfig.useTooltipForTitle) renderTooltip(matrices, title, width/2 - stringWidth, 27);
        else drawCenteredText(matrices, textRenderer, title, width / 2, 15, 0xFFFFFF);
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
        public int getScrollbarPositionX() { return this.width -7; }

        public void addButton(AbstractButtonWidget button) {
            this.addEntry(OverviewButtonEntry.create(button));
        }
        @Override
        public int getRowWidth() { return 400; }
    }
    public static class OverviewButtonEntry extends ElementListWidget.Entry<OverviewButtonEntry> {
        private final List<AbstractButtonWidget> buttons = new ArrayList<>();

        private OverviewButtonEntry(AbstractButtonWidget button) {
            this.buttons.add(button);
        }
        public static OverviewButtonEntry create(AbstractButtonWidget button) {
            return new OverviewButtonEntry(button);
        }
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.buttons.forEach((button) -> {
                button.y = y;
                button.render(matrices, mouseX, mouseY, tickDelta);
            });
        }
        public List<? extends Element> children() {
            return buttons;
        }
    }
}
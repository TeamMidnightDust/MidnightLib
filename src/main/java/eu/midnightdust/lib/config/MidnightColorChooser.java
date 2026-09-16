package eu.midnightdust.lib.config;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.function.Consumer;

public class MidnightColorChooser extends Screen {
    private static final Identifier BACKGROUND_SPRITE = Identifier.withDefaultNamespace("popup/background");
    private final Screen backgroundScreen;
    private final Consumer<Color> onConfirm;
    private final float[] hsb;
    private Color selectedColor;
    private Rectangle popupBounds;

    public MidnightColorChooser(final @NotNull Screen backgroundScreen, final Color selectedColor, final @Nullable Consumer<Color> onConfirm) {
        super(Component.translatable("midnightconfig.color_chooser.title"));
        this.backgroundScreen = backgroundScreen;
        this.onConfirm = onConfirm;
        this.selectedColor = selectedColor;
        this.height = backgroundScreen.height;
        this.width = backgroundScreen.width;
        this.hsb = Color.RGBtoHSB(selectedColor.getRed(), selectedColor.getGreen(), selectedColor.getBlue(), null);
    }

    @Override
    public void onClose() {
        //~ if >= 26.1 'setScreen' -> 'setScreenAndShow'
        minecraft.setScreenAndShow(backgroundScreen);
    }

    @Override
    public void init() {
        this.popupBounds = new Rectangle(width / 2 - 96, height / 2 - 48, 192, 96);

        MidnightSliderWidget hue = new MidnightSliderWidget(popupBounds.x + popupBounds.width / 2 - 20, popupBounds.y, 120, 20, hsb[0], d -> setFromHSB(d.floatValue(), null, null), d -> Component.translatable("midnightconfig.color_chooser.hue", (int) (d * 360)));
        this.addRenderableWidget(hue);
        MidnightSliderWidget saturation = new MidnightSliderWidget(popupBounds.x + popupBounds.width / 2 - 20, popupBounds.y + 25, 120, 20, hsb[1], d -> setFromHSB(null, d.floatValue(), null), d -> Component.translatable("midnightconfig.color_chooser.saturation", ((int) (d * 100.f)) / 100.f));
        this.addRenderableWidget(saturation);
        MidnightSliderWidget brightness = new MidnightSliderWidget(popupBounds.x + popupBounds.width / 2 - 20, popupBounds.y + 50, 120, 20, hsb[2], d -> setFromHSB(null, null, d.floatValue()), d -> Component.translatable("midnightconfig.color_chooser.brightness", ((int) (d * 100.f)) / 100.f));
        this.addRenderableWidget(brightness);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose())
                .bounds(popupBounds.x + popupBounds.width / 2 - 104, popupBounds.y + popupBounds.height - 16, 100, 20).build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            onConfirm.accept(selectedColor);
            onClose();
        }).bounds(popupBounds.x + popupBounds.width / 2 + 4, popupBounds.y + popupBounds.height - 16, 100, 20).build());
    }

    private void setFromHSB(@Nullable Float hue, @Nullable Float saturation, @Nullable Float brightness) {
        if (hue != null) hsb[0] = hue;
        if (saturation != null) hsb[1] = saturation;
        if (brightness != null) hsb[2] = brightness;
        this.selectedColor = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    @Override
    //~ if >= 26.1 'resize(Minecraft minecraft, ' -> '.resize('
    public void resize(int width, int height) {
        //~ if >= 26.1 '.resize(minecraft, ' -> '.resize(' {
        super.resize(width, height);
        if (this.backgroundScreen != null) {
            this.backgroundScreen.resize(width, height);
        }
        //~}
    }

    @Override
    //~ if >= 26.1 'renderBackground' -> 'extractBackground' {
    public void extractBackground(final @NotNull GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
        this.backgroundScreen.extractBackground(graphics, mouseX, mouseY, a);
        //? if >= 26.1 {
        graphics.nextStratum();
        //?} else {
        /*graphics.flush();
        RenderSystem.clear(256, Minecraft.ON_OSX);
        *///?}

        this.backgroundScreen.extractRenderState(graphics, -1, -1, 1.0f);

        //? if >= 26.1 {
        graphics.nextStratum();
        //?}

        //~ if >= 26.1 'render' -> 'extract'
        this.extractTransparentBackground(graphics);

        //graphics.blitNineSliced(BACKGROUND_SPRITE, this.popupBounds.x - 18, this.popupBounds.y - 18, this.popupBounds.width + 36, this.popupBounds.height + 36, 0, 0, 0, 0, 0);

        //~ if >= 26.1 '.blitSprite(' -> '.blitSprite(RenderPipelines.GUI_TEXTURED, '
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_SPRITE, this.popupBounds.x - 18, this.popupBounds.y - 18, this.popupBounds.width + 36, this.popupBounds.height + 36);
    }
    //~}

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        int textWidth = font.width(title) + 10;

        //~ if >= 26.1 '.blitSprite(' -> '.blitSprite(RenderPipelines.GUI_TEXTURED, '
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BACKGROUND_SPRITE, this.popupBounds.x + popupBounds.width / 2 - textWidth / 2, this.popupBounds.y - 16, textWidth, 10);
        graphics.centeredText(font, title, popupBounds.x + popupBounds.width / 2, popupBounds.y - 15, 0xFFFFFFFF);
        graphics.fill(popupBounds.x, popupBounds.y + 4, popupBounds.x + 64, popupBounds.y + 68, selectedColor.getRGB());
    }
}

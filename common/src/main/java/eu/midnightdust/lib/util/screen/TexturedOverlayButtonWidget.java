package eu.midnightdust.lib.util.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TexturedOverlayButtonWidget extends TexturedButtonWidget {

    public TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, Identifier texture, PressAction pressAction) {
        super(x, y, width, height, u, v, texture, pressAction);
    }

    public TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, PressAction pressAction) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, pressAction);
    }

    public TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, PressAction pressAction) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction);
    }

    public TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, PressAction pressAction, Text text) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, text);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int i = 66;
        if (!this.isNarratable()) {
            i += hoveredVOffset * 2;
        } else if (this.isSelected()) {
            i += hoveredVOffset;
        }
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        drawNineSlicedTexture(matrices, this.getX(), this.getY(), this.width, this.height, 4, 200, 20, 0, i);

        super.renderButton(matrices, mouseX, mouseY, delta);
    }
}

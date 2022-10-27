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
    public TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, PressAction pressAction) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction);
    }

    public TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, PressAction pressAction, Text text) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, text);
    }

    public TexturedOverlayButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, PressAction pressAction, TooltipSupplier tooltipSupplier, Text text) {
        super(x,y,width,height, u,v,hoveredVOffset,texture,textureWidth,textureHeight,pressAction,tooltipSupplier,text);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.method_46426(), this.method_46427(), 0, 46 + i * 20, this.width / 2, this.height);
        this.drawTexture(matrices, this.method_46426() + this.width / 2, this.method_46427(), 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);

        super.renderButton(matrices, mouseX, mouseY, delta);
    }
}

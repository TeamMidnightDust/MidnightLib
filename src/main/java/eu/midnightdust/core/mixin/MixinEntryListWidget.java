package eu.midnightdust.core.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import eu.midnightdust.core.config.MidnightLibConfig;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;

@Mixin(EntryListWidget.class)
public abstract class MixinEntryListWidget {

    @Inject(at = @At(value = "INVOKE",target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",shift = At.Shift.AFTER), method = "render", cancellable = true)
    private void custom_background(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!MidnightLibConfig.background_texture.equals("") && this.getClass().toString().toLowerCase(Locale.ROOT).contains("midnight"))
        RenderSystem.setShaderTexture(0, Identifier.tryParse(MidnightLibConfig.background_texture));
    }
}

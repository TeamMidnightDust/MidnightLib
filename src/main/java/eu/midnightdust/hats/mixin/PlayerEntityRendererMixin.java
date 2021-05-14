package eu.midnightdust.hats.mixin;

import eu.midnightdust.hats.bunny.BunnyEarsFeatureRenderer;
import eu.midnightdust.hats.christmas.ChristmasHatFeatureRenderer;
import eu.midnightdust.hats.tater.TinyPotatoFeatureRenderer;
import eu.midnightdust.hats.witch.WitchHatFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowSize) {
        super(ctx, model, shadowSize);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void addFeatures(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new WitchHatFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new ChristmasHatFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new BunnyEarsFeatureRenderer<>(this, ctx.getModelLoader()));
        this.addFeature(new TinyPotatoFeatureRenderer<>(this, ctx.getModelLoader()));
    }
}

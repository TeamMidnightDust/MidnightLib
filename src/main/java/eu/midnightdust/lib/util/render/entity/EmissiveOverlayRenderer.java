package eu.midnightdust.lib.util.render.entity;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class EmissiveOverlayRenderer<T extends LivingEntity> extends EyesFeatureRenderer<T, EntityModel<T>> {
    private final RenderLayer SKIN;

    public EmissiveOverlayRenderer(FeatureRendererContext<T, EntityModel<T>> featureRendererContext, Identifier texture) {
        super(featureRendererContext);
        SKIN = RenderLayer.getEyes(texture);
    }
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
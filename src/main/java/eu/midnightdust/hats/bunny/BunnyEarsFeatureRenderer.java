package eu.midnightdust.hats.bunny;

import eu.midnightdust.core.MidnightLibClient;
import eu.midnightdust.core.config.MidnightLibConfig;
import eu.midnightdust.hats.web.HatLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class BunnyEarsFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final String MOD_ID = MidnightLibClient.MOD_ID;
    public static final EntityModelLayer RABBIT_EARS_MODEL_LAYER = new EntityModelLayer(new Identifier("midnight-hats","bunny_ears"), "main");
    private static final UUID MOTSCHEN = UUID.fromString("a44c2660-630f-478f-946a-e518669fcf0c");

    private static final Identifier DEACTIVATED = new Identifier(MOD_ID,"textures/hats/empty.png");
    private static final Identifier RABBIT = new Identifier("textures/entity/rabbit/brown.png");
    private final BunnyEarsModel<T> bunnyEars;

    public BunnyEarsFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext, EntityModelLoader entityModelLoader) {
        super(featureRendererContext);
        this.bunnyEars = new BunnyEarsModel<>(entityModelLoader.getModelPart(RABBIT_EARS_MODEL_LAYER));
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(BunnyEarsModel.getModelData(), 64, 32);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        UUID uuid = livingEntity.getUuid();
        Identifier hat_type = getHat(uuid);

        if (!(hat_type == DEACTIVATED) && !HatLoader.PLAYER_HATS.containsKey(uuid) && !uuid.equals(MOTSCHEN)) {
            matrixStack.push();
            ((ModelWithHead) this.getContextModel()).getHead().rotate(matrixStack);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEntityCutoutNoCull(hat_type), false, false);
            this.bunnyEars.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }
    private Identifier getHat(UUID uuid) {
        if (MidnightLibConfig.event_hats && MidnightLibClient.EVENT.equals(MidnightLibClient.Event.EASTER))
            return RABBIT;
        else if (HatLoader.PLAYER_HATS != null && HatLoader.PLAYER_HATS.containsKey(uuid) && HatLoader.PLAYER_HATS.get(uuid).getHatType().contains("bunny"))
            return RABBIT;

        return DEACTIVATED;
    }

}

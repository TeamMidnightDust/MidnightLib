package eu.midnightdust.hats.witch;

import eu.midnightdust.hats.web.HatLoader;
import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.util.MidnightColorUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.UUID;

import static eu.midnightdust.core.MidnightLibClient.MOD_ID;

@Environment(EnvType.CLIENT)
public class WitchHatFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    public static final EntityModelLayer WITCH_HAT_MODEL_LAYER = new EntityModelLayer(new Identifier("midnight-hats","witch_hat"), "main");
    private static final UUID MOTSCHEN = UUID.fromString("a44c2660-630f-478f-946a-e518669fcf0c");

    private static final Identifier WITCH = new Identifier("textures/entity/witch.png");
    private static final Identifier OVERLAY = new Identifier(MOD_ID,"textures/hats/overlay.png");
    private static final Color MOTSCHEN_COLOR = MidnightColorUtil.radialRainbow(1,1);
    private static final Color ADOPTER_COLOR = MidnightColorUtil.hex2Rgb("ffffff");
    private static final Color MODDER_COLOR = MidnightColorUtil.hex2Rgb("7825b4");
    private static final Color FRIEND_COLOR = MidnightColorUtil.hex2Rgb("ff0234");
    private static final Color DONOR_COLOR = MidnightColorUtil.hex2Rgb("ff6c00");
    private static final Color SOCIAL_COLOR = MidnightColorUtil.hex2Rgb("238a9d");
    private final WitchHatModel<T> witchHat;
    private final MinecraftClient client = MinecraftClient.getInstance();

    public WitchHatFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext, EntityModelLoader entityModelLoader) {
        super(featureRendererContext);
        this.witchHat = new WitchHatModel<>(entityModelLoader.getModelPart(WITCH_HAT_MODEL_LAYER));
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(WitchHatModel.getModelData(), 64, 128);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        Color hat_type = getHat(livingEntity.getUuid());

        if (hat_type != null && !livingEntity.isInvisibleTo(client.player)) {
            if (hat_type == MOTSCHEN_COLOR) hat_type = MidnightColorUtil.radialRainbow(1,1);
            matrixStack.push();

            ((ModelWithHead) this.getContextModel()).getHead().rotate(matrixStack);
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(WITCH));
            this.witchHat.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV,1f,1f,1f,1);
            VertexConsumer glow = vertexConsumerProvider.getBuffer(RenderLayer.getBeaconBeam(OVERLAY,true));
            matrixStack.translate(0,0,-0.001f);
            this.witchHat.render(matrixStack, glow, 230, OverlayTexture.DEFAULT_UV, hat_type.getRed() / 255f, hat_type.getGreen() / 255f, hat_type.getBlue() / 255f, 1.0F);

            matrixStack.pop();
        }
    }
    private Color getHat(UUID uuid) {
        if (uuid.equals(MOTSCHEN)) {
            return MOTSCHEN_COLOR;
        } else if (HatLoader.PLAYER_HATS != null && HatLoader.PLAYER_HATS.containsKey(uuid)) {
            return switch (HatLoader.PLAYER_HATS.get(uuid).getHatType()) {
                case "adopter" -> ADOPTER_COLOR;
                case "contributer", "modder" -> MODDER_COLOR;
                case "friend" -> FRIEND_COLOR;
                case "donator", "donor" -> DONOR_COLOR;
                case "social" -> SOCIAL_COLOR;
                default -> MidnightColorUtil.hex2Rgb(HatLoader.PLAYER_HATS.get(uuid).getHatType());
            };
        }
        return null;
    }
}

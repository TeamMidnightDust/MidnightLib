package eu.midnightdust.hats.witch;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class WitchHatModel<T extends LivingEntity> extends SinglePartEntityModel<T> {
	private final ModelPart headwear;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;

    public WitchHatModel(ModelPart root) {
        headwear = root;
        root.setPivot(5.0F, -9.0F, -5.0F);

        bone = headwear.getChild("bone");
        bone.setPivot(-8.5F, -0.1F, 1.5F);
        setRotationAngle(bone, -0.0524F, 0.0F, 0.0349F);

        bone2 = bone.getChild("bone2");
        bone2.setPivot(1.5F, -4.0F, 1.5F);
        setRotationAngle(bone2, -0.1222F, 0.0F, 0.0698F);

        bone3 = bone2.getChild("bone3");
        bone3.setPivot(1.5F, -4.0F, 1.5F);
        setRotationAngle(bone3, -0.2618F, 0.0F, 0.1047F);
    }

    public static ModelData getModelData(){
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("headwear", ModelPartBuilder.create().uv(0, 64).cuboid(-10.0F, -0.1F, 0.0F, 10.0F, 2.0F, 10.0F), ModelTransform.NONE);
        ModelPartData modelPartData2 = modelPartData.addChild("bone", ModelPartBuilder.create().uv(0, 76).cuboid(0.0F, -4.0F, 0.0F, 7.0F, 4.0F, 7.0F), ModelTransform.rotation(-0.0524F, 0.0F, 0.0349F));
        ModelPartData modelPartData3 = modelPartData2.addChild("bone2", ModelPartBuilder.create().uv(0, 87).cuboid(0.0F, -4.0F, 0.0F, 4.0F, 4.0F, 4.0F), ModelTransform.rotation(-0.1222F, 0.0F, 0.0698F));
        modelPartData3.addChild("bone3", ModelPartBuilder.create().uv(0, 95).cuboid(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F), ModelTransform.rotation(-0.2618F, 0.0F, 0.1047F));

        return modelData;
    }

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

		headwear.render(matrixStack, buffer, packedLight, packedOverlay);
}

    @Override
    public ModelPart getPart() {
        return headwear;
    }

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
}

	}
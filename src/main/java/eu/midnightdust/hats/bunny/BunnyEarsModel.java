package eu.midnightdust.hats.bunny;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class BunnyEarsModel<T extends LivingEntity> extends SinglePartEntityModel<T> {
	private final ModelPart right_ear;

	public BunnyEarsModel(ModelPart root) {
		this.right_ear = root;

		right_ear.setPivot(0.0F, -3.0F, -1.0F);

}
	public static ModelData getModelData(){
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("right_ear", ModelPartBuilder.create().uv(52, 0).cuboid(-2.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F), ModelTransform.NONE);
		modelPartData.addChild("left_ear", ModelPartBuilder.create().uv(58, 0).mirrored().cuboid(0.5F, -9.0F, -1.0F, 2.0F, 5.0F, 1.0F), ModelTransform.NONE);
		return modelData;
	}

	public ModelPart getPart() {
		return this.right_ear;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		right_ear.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}
}
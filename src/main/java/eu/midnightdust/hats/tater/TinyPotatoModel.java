package eu.midnightdust.hats.tater;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class TinyPotatoModel <T extends LivingEntity> extends SinglePartEntityModel<T> {
	private final ModelPart tater;
	public TinyPotatoModel(ModelPart root) {
		tater = root;
		tater.setPivot(0.0F, -8.0F, 0.0F);
	}

	public static ModelData getModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("tater", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.NONE);

		return modelData;
	}
	public ModelPart getPart() {
		return this.tater;
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}
	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){

		tater.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}
package com.talhanation.camels.client.model;// Made with Blockbench 3.5.2
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.talhanation.camels.entities.EntityCamel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class  ModelEntityCamel<T extends EntityCamel> extends EntityModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer chest_1;
	private final ModelRenderer chest_2;
	private final ModelRenderer leg_front_right;
	private final ModelRenderer leg_front_left;
	private final ModelRenderer leg_back_right;
	private final ModelRenderer leg_back_left;
	private final ModelRenderer tail;
	private final ModelRenderer head;

	public ModelEntityCamel() {
		textureWidth = 128;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 2.0F);
		body.setTextureOffset(26, 0).addBox(-6.0F, -24.0F, -11.0F, 12.0F, 10.0F, 21.0F, 0.0F, false);
		body.setTextureOffset(104, 22).addBox(-3.0F, -27.0F, -10.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
		body.setTextureOffset(104, 22).addBox(-3.0F, -27.0F, 3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
		body.setTextureOffset(88, 0).addBox(-6.5F, -24.5F, -4.0F, 13.0F, 10.0F, 7.0F, 0.0F, false);

		chest_1 = new ModelRenderer(this);
		chest_1.setRotationPoint(9.0F, -15.0F, -4.0F);
		body.addChild(chest_1);
		chest_1.setTextureOffset(48, 37).addBox(-3.0F, -8.0F, 5.0F, 3.0F, 8.0F, 8.0F, 0.0F, true);

		chest_2 = new ModelRenderer(this);
		chest_2.setRotationPoint(9.0F, -15.0F, -4.0F);
		body.addChild(chest_2);
		chest_2.setTextureOffset(48, 37).addBox(-18.0F, -8.0F, 5.0F, 3.0F, 8.0F, 8.0F, 0.0F, false);

		leg_front_right = new ModelRenderer(this);
		leg_front_right.setRotationPoint(-4.0F, -14.0F, -5.0F);
		body.addChild(leg_front_right);
		leg_front_right.setTextureOffset(109, 37).addBox(-1.0F, 0.0F, -4.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);

		leg_front_left = new ModelRenderer(this);
		leg_front_left.setRotationPoint(4.0F, -14.0F, -5.0F);
		body.addChild(leg_front_left);
		leg_front_left.setTextureOffset(109, 37).addBox(-3.0F, 0.0F, -4.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);

		leg_back_right = new ModelRenderer(this);
		leg_back_right.setRotationPoint(-4.0F, -14.0F, 5.0F);
		body.addChild(leg_back_right);
		leg_back_right.setTextureOffset(109, 37).addBox(-1.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);

		leg_back_left = new ModelRenderer(this);
		leg_back_left.setRotationPoint(4.0F, -14.0F, 5.0F);
		body.addChild(leg_back_left);
		leg_back_left.setTextureOffset(109, 37).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, -23.0F, 10.0F);
		body.addChild(tail);
		tail.setTextureOffset(110, 22).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, -19.5F, -7.0F);
		body.addChild(head);
		head.setTextureOffset(0, 37).addBox(-4.0F, -3.5F, -12.0F, 8.0F, 7.0F, 10.0F, 0.0F, false);
		head.setTextureOffset(0, 18).addBox(-3.0F, -9.0F, -14.0F, 6.0F, 8.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-3.0F, -13.0F, -17.0F, 6.0F, 4.0F, 8.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-2.0F, -14.0F, -11.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(1.0F, -14.0F, -11.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(115, 10).addBox(-4.0F, -7.5F, -9.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(115, 10).addBox(2.0F, -7.5F, -9.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(114, 18).addBox(-3.0F, -8.5F, -2.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(115, 10).addBox(-4.0F, -8.5F, -6.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(115, 10).addBox(2.0F, -8.5F, -6.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T t, float v, float v1, float v2, float v3, float v4) {
		this.head.rotateAngleX = v4 * 0.0125F;
		this.head.rotateAngleY = v3 * 0.0125F;
		this.body.rotateAngleX = 0;
		this.leg_front_right.rotateAngleX = MathHelper.cos(v * 0.6662F) * 0.6F * v1;
		this.leg_front_left.rotateAngleX = MathHelper.cos(v * 0.6662F + 3.1415927F) * 0.6F * v1;
		this.leg_back_right.rotateAngleX = MathHelper.cos(v * 0.6662F + 3.1415927F) * 0.6F * v1;
		this.leg_back_left.rotateAngleX = MathHelper.cos(v * 0.6662F) * 0.6F * v1;
		boolean flag = !t.isChild() && t.hasChest();
		this.chest_1.showModel = flag;
		this.chest_2.showModel = flag;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		if (this.isChild) {
			matrixStack.push();
			matrixStack.scale(0.45454544F, 0.41322312F, 0.45454544F);
			matrixStack.translate(0.0D, 2.0625D, 0.0D);
			ImmutableList.of(this.leg_front_right, this.leg_front_left, this.leg_back_right, this.leg_back_left, this.chest_1, this.chest_2).forEach((p_228280_8_) -> {
				body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			matrixStack.pop();
		} else {

			matrixStack.push();
			matrixStack.scale(1.2F, 1.2F, 1.2F);
			matrixStack.translate(0.0D, -0.2D, 0.0D);
			ImmutableList.of(this.head, this.body, this.leg_front_right, this.leg_front_left, this.leg_back_right, this.leg_back_left, this.chest_1, this.chest_2).forEach((p_228279_8_) -> {
				body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			});
			matrixStack.pop();
		}
	}
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTick);

	}
}
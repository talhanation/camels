package com.talhanation.camels.client;

import com.talhanation.camels.entity.EntityCamel;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

/**
 * ModelCamel - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelCamel extends ModelQuadruped {

    public ModelRenderer Leg4;
    public ModelRenderer backLeftShin;
    public ModelRenderer backLeftHoof;
    public ModelRenderer Leg3;
    public ModelRenderer frontLeftShin;
    public ModelRenderer frontLeftHoof;
    public ModelRenderer Leg2;
    public ModelRenderer backRightShin;
    public ModelRenderer backRightHoof;
    public ModelRenderer Leg1;
    public ModelRenderer frontRightShin;
    public ModelRenderer frontRightHoof;
    public ModelRenderer horseLeftSaddleMetal;
    public ModelRenderer horseSaddleFront;
    public ModelRenderer horseRightSaddleRope;
    public ModelRenderer horseSaddleBottom;
    public ModelRenderer horseLeftSaddleRope;
    public ModelRenderer horseRightSaddleMetal;
    public ModelRenderer horseLeftRein;
    public ModelRenderer horseSaddleBack;
    public ModelRenderer horseRightRein;
    public ModelRenderer body;
    public ModelRenderer neck;
    public ModelRenderer tailBase;
    public ModelRenderer carpet_3;
    public ModelRenderer hump_2;
    public ModelRenderer carpet_1;
    public ModelRenderer carpet_2;
    public ModelRenderer hump_1;
    public ModelRenderer neck_1;
    public ModelRenderer horseFaceSaddle;
    public ModelRenderer ear_2;
    public ModelRenderer head;
    public ModelRenderer ear_1;
    public ModelRenderer horseFaceSaddle_4;
    public ModelRenderer horseFaceSaddle_2;
    public ModelRenderer horseFaceSaddle_3;
    public ModelRenderer horseFaceMetal;
    public ModelRenderer horseFaceSaddle_5;
    public ModelRenderer horseFaceSaddle_6;
    public ModelRenderer horseFaceSaddle_7;
    public ModelRenderer horseFaceSaddle_8;
    public ModelRenderer tailMiddle;
    public ModelRenderer tailTip;
    public ModelRenderer camelLeftChest;
    public ModelRenderer camelRightChest;

    public ModelCamel(float textureOffset) {
        super(0, textureOffset);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.frontLeftHoof = new ModelRenderer(this, 110, 55);
        this.frontLeftHoof.setRotationPoint(-1.0F, 8.0F, -2.5F);
        this.frontLeftHoof.addBox(-0.5F, 0.0F, 0.5F, 3, 2, 4, 0.0F);
        this.horseLeftSaddleRope = new ModelRenderer(this, 77, 0);
        this.horseLeftSaddleRope.setRotationPoint(6.75F, -1.5F, -1.0F);
        this.horseLeftSaddleRope.addBox(-1.5F, 1.0F, -0.5F, 1, 6, 1, 0.0F);
        this.horseFaceSaddle_3 = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle_3.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.horseFaceSaddle_3.addBox(-3.2F, -15.5F, -8.1F, 4, 0, 1, 0.0F);
        this.hump_2 = new ModelRenderer(this, 104, 22);
        this.hump_2.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.hump_2.addBox(-3.0F, -51.0F, 3.0F, 6, 3, 6, 0.0F);
        this.horseFaceSaddle_4 = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle_4.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.horseFaceSaddle_4.addBox(-3.2F, -20.2F, -8.1F, 4, 0, 1, 0.0F);
        this.horseFaceSaddle_5 = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle_5.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.horseFaceSaddle_5.addBox(-0.8F, -15.5F, -8.1F, 4, 0, 1, 0.0F);
        this.frontRightShin = new ModelRenderer(this, 109, 37);
        this.frontRightShin.setRotationPoint(0.8F, 8.0F, 0.3F);
        this.frontRightShin.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.horseSaddleFront = new ModelRenderer(this, 113, 9);
        this.horseSaddleFront.setRotationPoint(0.0F, -1.0F, -1.0F);
        this.horseSaddleFront.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2, 0.0F);
        this.horseFaceSaddle_6 = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle_6.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.horseFaceSaddle_6.addBox(-0.8F, -20.2F, -8.1F, 4, 0, 1, 0.0F);
        this.camelLeftChest = new ModelRenderer(this, 48, 37);
        this.camelLeftChest.setRotationPoint(9.0F, 0.0F, -1.0F);
        this.camelLeftChest.addBox(-3.0F, 1.0F, 3.0F, 3, 8, 8, 0.0F);
        this.tailBase = new ModelRenderer(this, 114, 21);
        this.tailBase.setRotationPoint(0.0F, 0.0F, 10.0F);
        this.tailBase.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 1, 0.0F);
        this.setRotateAngle(tailBase, 0.3490658503988659F, 0.0F, 0.0F);
        this.Leg2 = new ModelRenderer(this, 109, 37);
        this.Leg2.setRotationPoint(-5.6F, 6.0F, 9.0F);
        this.Leg2.addBox(-0.7F, -1.0F, -1.7F, 3, 9, 4, 0.0F);
        this.carpet_3 = new ModelRenderer(this, 77, 40);
        this.carpet_3.mirror = true;
        this.carpet_3.setRotationPoint(-0.5F, -24.1F, 0.0F);
        this.carpet_3.addBox(0.0F, 0.0F, -8.0F, 1, 1, 12, 0.0F);
        this.tailTip = new ModelRenderer(this, 114, 57);
        this.tailTip.setRotationPoint(-1.0F, 5.0F, -0.5F);
        this.tailTip.addBox(-0.5F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Leg4 = new ModelRenderer(this, 109, 37);
        this.Leg4.setRotationPoint(4.0F, 6.0F, 9.0F);
        this.Leg4.addBox(-0.7F, -1.0F, -1.7F, 3, 9, 4, 0.0F);
        this.hump_1 = new ModelRenderer(this, 104, 22);
        this.hump_1.setRotationPoint(0.0F, 24.0F, 1.0F);
        this.hump_1.addBox(-3.0F, -51.0F, -12.0F, 6, 3, 6, 0.0F);
        this.ear_2 = new ModelRenderer(this, 0, 0);
        this.ear_2.setRotationPoint(-1.0F, -17.0F, -5.0F);
        this.ear_2.addBox(-1.0F, -0.4F, -5.0F, 1, 1, 2, 0.0F);
        this.horseFaceSaddle_7 = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle_7.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.horseFaceSaddle_7.addBox(-3.2F, -20.2F, -8.1F, 0, 3, 1, 0.0F);
        this.carpet_1 = new ModelRenderer(this, 73, 40);
        this.carpet_1.mirror = true;
        this.carpet_1.setRotationPoint(-5.2F, -24.1F, -7.0F);
        this.carpet_1.addBox(-1.0F, 0.0F, 0.0F, 6, 9, 11, 0.0F);
        this.tailMiddle = new ModelRenderer(this, 114, 21);
        this.tailMiddle.setRotationPoint(0.5F, 3.7F, 0.0F);
        this.tailMiddle.addBox(-1.0F, 0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(tailMiddle, -0.3141592653589793F, 0.0F, 0.0F);
        this.camelRightChest = new ModelRenderer(this, 48, 37);
        this.camelRightChest.setRotationPoint(9.0F, 0.0F, -1.0F);
        this.camelRightChest.addBox(-18.0F, 1.0F, 3.0F, 3, 8, 8, 0.0F);
        this.backRightHoof = new ModelRenderer(this, 110, 55);
        this.backRightHoof.setRotationPoint(-1.0F, 8.0F, -2.5F);
        this.backRightHoof.addBox(-0.5F, 0.0F, 0.5F, 3, 2, 4, 0.0F);
        this.horseFaceSaddle = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle.setRotationPoint(0.2F, -0.2F, 0.0F);
        this.horseFaceSaddle.addBox(3.2F, -14.0F, -14.1F, 0, 2, 1, 0.0F);
        this.setRotateAngle(horseFaceSaddle, 0.3490658503988659F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 26, 0);
        this.body.setRotationPoint(0.0F, 24.0F, 1.0F);
        this.body.addBox(-6.0F, -24.0F, -11.0F, 12, 10, 21, 0.0F);
        this.frontLeftShin = new ModelRenderer(this, 109, 37);
        this.frontLeftShin.setRotationPoint(0.8F, 8.0F, 0.3F);
        this.frontLeftShin.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.frontRightHoof = new ModelRenderer(this, 110, 55);
        this.frontRightHoof.setRotationPoint(-1.0F, 8.0F, -2.5F);
        this.frontRightHoof.addBox(-0.5F, 0.0F, 0.5F, 3, 2, 4, 0.0F);
        this.carpet_2 = new ModelRenderer(this, 73, 40);
        this.carpet_2.setRotationPoint(0.2F, -24.1F, -7.0F);
        this.carpet_2.addBox(0.0F, 0.0F, 0.0F, 6, 9, 11, 0.0F);
        this.horseFaceMetal = new ModelRenderer(this, 86, 18);
        this.horseFaceMetal.setRotationPoint(0.0F, -13.8F, -14.4F);
        this.horseFaceMetal.addBox(-3.5F, -0.5F, 0.2F, 7, 1, 1, 0.0F);
        this.horseFaceSaddle_8 = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle_8.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.horseFaceSaddle_8.addBox(-3.2F, -17.5F, -8.1F, 0, 2, 1, 0.0F);
        this.backRightShin = new ModelRenderer(this, 109, 37);
        this.backRightShin.setRotationPoint(0.8F, 8.0F, 0.3F);
        this.backRightShin.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.horseRightSaddleMetal = new ModelRenderer(this, 90, 13);
        this.horseRightSaddleMetal.setRotationPoint(-6.0F, -0.5F, -1.0F);
        this.horseRightSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2, 0.0F);
        this.horseFaceSaddle_2 = new ModelRenderer(this, 83, 1);
        this.horseFaceSaddle_2.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.horseFaceSaddle_2.addBox(3.2F, -20.2F, -8.1F, 0, 3, 1, 0.0F);
        this.backLeftHoof = new ModelRenderer(this, 110, 55);
        this.backLeftHoof.setRotationPoint(-1.0F, 8.0F, -2.5F);
        this.backLeftHoof.addBox(-0.5F, 0.0F, 0.5F, 3, 2, 4, 0.0F);
        this.Leg3 = new ModelRenderer(this, 109, 37);
        this.Leg3.setRotationPoint(4.0F, 6.0F, -8.5F);
        this.Leg3.addBox(-0.7F, -1.0F, -1.7F, 3, 9, 4, 0.0F);
        this.horseLeftRein = new ModelRenderer(this, 75, 20);
        this.horseLeftRein.setRotationPoint(0.0F, 3.5F, -5.0F);
        this.horseLeftRein.addBox(3.3F, -9.5F, -18.5F, 0, 2, 16, 0.0F);
        this.horseSaddleBottom = new ModelRenderer(this, 87, 0);
        this.horseSaddleBottom.setRotationPoint(-0.5F, -1.0F, -1.0F);
        this.horseSaddleBottom.addBox(-5.0F, 0.0F, -3.0F, 11, 1, 8, 0.0F);
        this.ear_1 = new ModelRenderer(this, 0, 0);
        this.ear_1.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.ear_1.addBox(1.0F, -17.3F, -11.0F, 1, 1, 2, 0.0F);
        this.horseLeftSaddleMetal = new ModelRenderer(this, 90, 13);
        this.horseLeftSaddleMetal.setRotationPoint(6.0F, -0.5F, -1.0F);
        this.horseLeftSaddleMetal.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2, 0.0F);
        this.neck_1 = new ModelRenderer(this, 0, 18);
        this.neck_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.neck_1.addBox(-3.0F, -12.5F, -12.5F, 6, 12, 5, 0.0F);
        this.setRotateAngle(neck_1, 0.3490658503988659F, -0.008901179185171082F, 0.0F);
        this.Leg1 = new ModelRenderer(this, 109, 37);
        this.Leg1.setRotationPoint(-5.6F, 6.0F, -8.5F);
        this.Leg1.addBox(-0.7F, -1.0F, -1.7F, 3, 9, 4, 0.0F);
        this.horseRightSaddleRope = new ModelRenderer(this, 87, 0);
        this.horseRightSaddleRope.setRotationPoint(-5.75F, -0.5F, -1.0F);
        this.horseRightSaddleRope.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.neck = new ModelRenderer(this, 0, 37);
        this.neck.setRotationPoint(0.0F, 3.5F, -5.0F);
        this.neck.addBox(-4.0F, -3.0F, -12.0F, 8, 7, 10, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-3.0F, -16.4F, -15.5F, 6, 4, 8, 0.0F);
        this.backLeftShin = new ModelRenderer(this, 109, 37);
        this.backLeftShin.setRotationPoint(0.8F, 8.0F, 0.3F);
        this.backLeftShin.addBox(-1.0F, 0.0F, -1.5F, 2, 8, 3, 0.0F);
        this.horseSaddleBack = new ModelRenderer(this, 87, 9);
        this.horseSaddleBack.setRotationPoint(0.0F, -1.0F, -1.0F);
        this.horseSaddleBack.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2, 0.0F);
        this.horseRightRein = new ModelRenderer(this, 75, 20);
        this.horseRightRein.setRotationPoint(0.0F, 3.5F, -5.0F);
        this.horseRightRein.addBox(-3.0F, -9.5F, -18.5F, 0, 2, 16, 0.0F);
        this.frontLeftShin.addChild(this.frontLeftHoof);
        this.horseFaceSaddle.addChild(this.horseFaceSaddle_3);
        this.body.addChild(this.hump_2);
        this.horseFaceSaddle.addChild(this.horseFaceSaddle_4);
        this.horseFaceSaddle.addChild(this.horseFaceSaddle_5);
        this.Leg1.addChild(this.frontRightShin);
        this.horseFaceSaddle.addChild(this.horseFaceSaddle_6);
        this.body.addChild(this.carpet_3);
        this.tailMiddle.addChild(this.tailTip);
        this.body.addChild(this.hump_1);
        this.neck_1.addChild(this.ear_2);
        this.horseFaceSaddle.addChild(this.horseFaceSaddle_7);
        this.body.addChild(this.carpet_1);
        this.tailBase.addChild(this.tailMiddle);
        this.backRightShin.addChild(this.backRightHoof);
        this.neck.addChild(this.horseFaceSaddle);
        this.Leg3.addChild(this.frontLeftShin);
        this.frontRightShin.addChild(this.frontRightHoof);
        this.body.addChild(this.carpet_2);
        this.horseFaceSaddle.addChild(this.horseFaceMetal);
        this.horseFaceSaddle.addChild(this.horseFaceSaddle_8);
        this.Leg2.addChild(this.backRightShin);
        this.horseFaceSaddle.addChild(this.horseFaceSaddle_2);
        this.backLeftShin.addChild(this.backLeftHoof);
        this.neck_1.addChild(this.ear_1);
        this.neck.addChild(this.neck_1);
        this.neck_1.addChild(this.head);
        this.Leg4.addChild(this.backLeftShin);
    }
    @Override
    public void render(@Nonnull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        EntityCamel entityCamel = (EntityCamel) entity;
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity) entityCamel);
        if (entityCamel.isChild()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, this.childYOffset * scale, this.childZOffset * scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.45454544F, 0.41322312F, 0.45454544F);
            GlStateManager.translate(0.0F, 33.0F * scale, 0.0F);
            this.body.render(scale);
            this.neck.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();

            GlStateManager.scale(0.45454544F, 0.41322312F, 0.45454544F);
            GlStateManager.translate(0.0F, 33.0F * scale, 0.0F);
            this.Leg1.render(scale);
            this.Leg2.render(scale);
            this.Leg3.render(scale);
            this.Leg4.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();

            GlStateManager.scale(0.45454544F, 0.41322312F, 0.45454544F);
            GlStateManager.translate(0.0F, 37.0F * scale, 0.0F);
            this.tailBase.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.neck.render(scale);
            this.body.render(scale);
            this.tailBase.render(scale);
            this.Leg1.render(scale);
            this.Leg2.render(scale);
            this.Leg3.render(scale);
            this.Leg4.render(scale);


            if (entityCamel.isHorseSaddled() && !entityCamel.isChild()) {
                this.horseLeftSaddleMetal.render(scale);
                this.horseLeftSaddleRope.render(scale);
                this.horseRightSaddleMetal.render(scale);
                this.horseRightSaddleRope.render(scale);
                this.horseSaddleBack.render(scale);
                this.horseSaddleBottom.render(scale);
                this.horseSaddleFront.render(scale);

                if (entityCamel.isBeingRidden()) {
                    this.horseLeftRein.render(scale);
                    this.horseRightRein.render(scale);
                }
            }

            if (entityCamel.hasChest() && !entityCamel.isChild()) {
                this.camelLeftChest.render(scale);
                this.camelRightChest.render(scale);
            }
        }
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        limbSwingAmount *= 2F;
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        EntityCamel entityCamel = (EntityCamel) entity;

        this.neck.rotateAngleX = headPitch * 0.017453292F;
        this.neck.rotateAngleY = netHeadYaw * 0.017453292F;

        this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
        this.Leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.5F * limbSwingAmount;
        this.Leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.5F * limbSwingAmount;
        this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;

        if (entityCamel.isBeingRidden()) {
            this.neck.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.025F * limbSwingAmount;
            this.horseLeftRein.rotateAngleX=MathHelper.cos(limbSwing * 0.6662F) * 0.025F * limbSwingAmount;
            this.horseRightRein.rotateAngleX=MathHelper.cos(limbSwing * 0.6662F) * 0.025F * limbSwingAmount;
            this.tailBase.rotateAngleX = 6.8F + MathHelper.sqrt(Math.pow(entityCamel.motionX, 2.0D) + Math.pow(entityCamel.motionZ, 2.0D));
        } else {
            this.tailBase.rotateAngleX = 6.8F;
            this.neck.rotateAngleX = 0F;
        }
        this.tailBase.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1F * limbSwingAmount;
    }



    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
        private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }
    }

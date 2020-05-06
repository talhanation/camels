package com.talhanation.camels.client.layer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.camels.Main;
import com.talhanation.camels.client.model.ModelEntityCamel;
import com.talhanation.camels.entities.EntityCamel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.client.renderer.texture.OverlayTexture;

import net.minecraft.item.SaddleItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;



@OnlyIn(Dist.CLIENT)
public class CamelSaddleLayer extends LayerRenderer<EntityCamel, ModelEntityCamel<EntityCamel>> {
    private static final ResourceLocation SADDLE = new ResourceLocation(Main.MOD_ID,"textures/entity/camel_saddle_layer.png");
    private final ModelEntityCamel<EntityCamel> model = new ModelEntityCamel();

    public CamelSaddleLayer(IEntityRenderer<EntityCamel, ModelEntityCamel<EntityCamel>> p_i50933_1_) {
        super(p_i50933_1_);
    }

    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, EntityCamel entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        ResourceLocation texture;
        if (entity.isHorseSaddled()){
            texture = SADDLE;
        } else {
            return;
        }


        ((ModelEntityCamel)this.getEntityModel()).copyModelAttributesTo(this.model);
        this.model.setRotationAngles(entity, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
        IVertexBuilder lvt_13_1_ = p_225628_2_.getBuffer(RenderType.getEntityCutoutNoCull(texture));
        this.model.render(p_225628_1_, lvt_13_1_, p_225628_3_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}

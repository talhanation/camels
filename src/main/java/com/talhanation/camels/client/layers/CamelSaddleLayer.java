package com.talhanation.camels.client.layers;

import com.talhanation.camels.Reference;
import com.talhanation.camels.client.ModelCamel;
import com.talhanation.camels.client.RenderCamel;
import com.talhanation.camels.entity.EntityCamel;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CamelSaddleLayer implements LayerRenderer<EntityCamel>{

        private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/camel_saddle_layer.png");
        private final RenderCamel camelRenderer;
        private final ModelCamel camelModel = new ModelCamel(0);

        public CamelSaddleLayer(RenderCamel pigRendererIn)
        {
            this.camelRenderer = pigRendererIn;
        }

        public void doRenderLayer(EntityCamel entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            if (entitylivingbaseIn.isHorseSaddled())
            {
                this.camelRenderer.bindTexture(TEXTURE);
                this.camelModel.setModelAttributes(this.camelRenderer.getMainModel());
                this.camelModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }

        public boolean shouldCombineTextures()
        {
            return false;
        }
}
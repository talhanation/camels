package com.talhanation.camels.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.camels.Main;
import com.talhanation.camels.client.layer.CamelDecorLayer;
import com.talhanation.camels.client.layer.CamelSaddleLayer;
import com.talhanation.camels.client.model.ModelEntityCamel;
import com.talhanation.camels.entities.EntityCamel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderEntityCamel extends MobRenderer<EntityCamel, ModelEntityCamel<EntityCamel>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(Main.MOD_ID,"textures/entity/camel_entity.png");

    public RenderEntityCamel(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn, new ModelEntityCamel<EntityCamel>(), 0.85F);
        this.addLayer(new CamelDecorLayer(this));
        this.addLayer(new CamelSaddleLayer(this));
    }
    protected void preRenderCallback(EntityCamel entity, MatrixStack matrix, float a) {
        matrix.scale(1.1F, 1.1F, 1.1F);
        super.preRenderCallback(entity, matrix, a);
    }


    public ResourceLocation getEntityTexture(EntityCamel entity) {
        return TEXTURE;
    }
}

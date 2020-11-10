package com.talhanation.camels.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.camels.Main;
import com.talhanation.camels.client.layer.CamelDecorLayer;
import com.talhanation.camels.client.layer.CamelSaddleLayer;
import com.talhanation.camels.client.model.ModelEntityCamel;
import com.talhanation.camels.entities.CamelEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderEntityCamel extends MobRenderer<CamelEntity, ModelEntityCamel<CamelEntity>> {
    protected static final ResourceLocation[] TEXTURE = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID,"textures/entity/camel_entity_0.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/camel_entity_1.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/camel_entity_2.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/camel_entity_3.png")};

    public RenderEntityCamel(EntityRendererManager rendererManagerIn){
        super(rendererManagerIn, new ModelEntityCamel<CamelEntity>(), 0.85F);
        this.addLayer(new CamelDecorLayer(this));
        this.addLayer(new CamelSaddleLayer(this));
    }
    protected void preRenderCallback(CamelEntity entity, MatrixStack matrix, float a) {
        matrix.scale(1.1F, 1.1F, 1.1F);
        super.preRenderCallback(entity, matrix, a);
    }

    public ResourceLocation getEntityTexture(CamelEntity entity) {
        return TEXTURE [entity.getVariant()];
    }
}

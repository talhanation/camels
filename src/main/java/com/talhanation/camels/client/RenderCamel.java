package com.talhanation.camels.client;

import com.talhanation.camels.Reference;
import com.talhanation.camels.client.layers.CamelDecorLayer;
import com.talhanation.camels.client.layers.CamelSaddleLayer;
import com.talhanation.camels.entity.EntityCamel;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCamel extends RenderLiving<EntityCamel>
{
    public static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new  ResourceLocation (Reference.MOD_ID ,"textures/entity/camel/camel_entity_0.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/camel_entity_0.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/camel_entity_1.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/camel_entity_2.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/camel_entity_3.png")};

    public RenderCamel(RenderManager manager)
    {
        super(manager, new ModelCamel(0), 0.85f);
        this.addLayer(new CamelDecorLayer(this));
        this.addLayer(new CamelSaddleLayer(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCamel entity)
    {
        return TEXTURES[entity.getVariant()];
    }
}
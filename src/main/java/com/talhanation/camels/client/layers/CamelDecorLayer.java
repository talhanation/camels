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
public class CamelDecorLayer implements LayerRenderer<EntityCamel>
{
    private static final ResourceLocation[] CAMEL_DECOR_TEXTURES = new ResourceLocation[] {
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/white.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/orange.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/magenta.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/light_blue.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/yellow.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/lime.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/pink.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/gray.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/light_gray.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/cyan.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/purple.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/blue.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/brown.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/green.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/red.png"),
            new ResourceLocation(Reference.MOD_ID,"textures/entity/camel/decor/black.png")};

    private final RenderCamel renderer;
    private final ModelCamel model = new ModelCamel(0);

    public CamelDecorLayer(RenderCamel renderCamel)
    {
        this.renderer = renderCamel;
    }

    public void doRenderLayer(EntityCamel entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entitylivingbaseIn.hasColor())
        {
            this.renderer.bindTexture(CAMEL_DECOR_TEXTURES[entitylivingbaseIn.getColor().getMetadata()]);
            this.model.setModelAttributes(this.renderer.getMainModel());
            this.model.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}
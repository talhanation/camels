package com.talhanation.camels;


import com.talhanation.camels.client.RenderCamel;
import com.talhanation.camels.entity.EntityCamel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModRenderHandler
{

    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCamel.class, new IRenderFactory<EntityCamel>()
        {
            public Render<? super EntityCamel> createRenderFor(RenderManager manager)
            {
                return new RenderCamel(manager);
            }
        });
    }
}
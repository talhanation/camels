package com.talhanation.camels.init;


import com.talhanation.camels.Main;
import com.talhanation.camels.Reference;
import com.talhanation.camels.entity.EntityCamel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {


    public static void registerEntities()
    {
        registerEntity("camel", EntityCamel.class, Reference.ENTITY_CAMEL, 128, 14460200, 13995816);

    }

    private static void registerEntity(String name, Class<EntityCamel> entity, int id, int range, int color1, int color2)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID + ":" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
    }
}

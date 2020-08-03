package com.talhanation.camels;

import com.talhanation.camels.entity.EntityCamel;
import com.talhanation.camels.init.EntityInit;
import com.talhanation.camels.init.SoundInit;
import com.talhanation.camels.proxy.CommonProxy;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import static net.minecraft.init.Biomes.*;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void PreInit(FMLPreInitializationEvent event){

        EntityInit.registerEntities();
}

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        SoundInit.registerSounds();
        EntityRegistry.addSpawn(EntityCamel.class, 2, 2, 5, EnumCreatureType.CREATURE, //
                DESERT, SAVANNA, SAVANNA_PLATEAU);
    }

    @Mod.EventHandler
    public static void PostInit(FMLPostInitializationEvent event){

    }

}

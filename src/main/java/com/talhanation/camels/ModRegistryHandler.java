package com.talhanation.camels;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModRegistryHandler {


    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event){
        ModRenderHandler.registerEntityRenders();
    }

}

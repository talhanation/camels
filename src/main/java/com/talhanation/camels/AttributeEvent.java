package com.talhanation.camels;

import com.talhanation.camels.entities.CamelEntity;
import com.talhanation.camels.entities.DromedaryEntity;
import com.talhanation.camels.init.ModEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeEvent {

    @SubscribeEvent
    public static void entityAttributeEvent(final EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.CAMEL.get(), CamelEntity.createAttributes().build());
        event.put(ModEntityTypes.DROMEDARY.get(), DromedaryEntity.createAttributes().build());
    }
}

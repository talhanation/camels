package com.talhanation.camels.init;

import com.talhanation.camels.Main;
import com.talhanation.camels.entities.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);


    public static final RegistryObject<EntityType<CamelEntity>> CAMEL = ENTITY_TYPES.register("camel",
            () -> EntityType.Builder.of(CamelEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .canSpawnFarFromPlayer()
                    .setTrackingRange(32)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "camel").toString()));

    public static final RegistryObject<EntityType<DromedaryEntity>> DROMEDARY = ENTITY_TYPES.register("dromedary",
            () -> EntityType.Builder.of(DromedaryEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .canSpawnFarFromPlayer()
                    .setTrackingRange(32)
                    .setShouldReceiveVelocityUpdates(true)
                    .build(new ResourceLocation(Main.MOD_ID, "dromedary").toString()));
}

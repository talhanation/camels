package com.talhanation.camels.init;

import com.talhanation.camels.Main;
import com.talhanation.camels.entities.CamelEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MOD_ID);



    public static final RegistryObject<EntityType<CamelEntity>> CAMEL_ENTITY = ENTITY_TYPES.register("camel", () -> EntityType.Builder.<CamelEntity>create(CamelEntity::new, EntityClassification.CREATURE)
            .size(0.9f, 1.2f)
            .build(new ResourceLocation(Main.MOD_ID,"camel").toString()));


    public static void addEntitySpawns() {
        ForgeRegistries.BIOMES.getValues().stream().forEach(ModEntityTypes::processSpawning);

    }

    private static void processSpawning(Biome biome) {
        if(biome.getCategory() == Biome.Category.DESERT) {
          //  biome.(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ModEntityTypes.CAMEL_ENTITY.get(), 1, 2, 4));//x= y=min z=max
        }
    }
}

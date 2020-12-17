package com.talhanation.camels.world.gen;

import com.talhanation.camels.Main;
import com.talhanation.camels.init.ModEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEntitySpawns {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent e) {
        if (e.getCategory() == Biome.Category.DESERT) {
            e.getSpawns().withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(ModEntityTypes.CAMEL_ENTITY.get(), 2, 2, 3));
        }
    }
}




























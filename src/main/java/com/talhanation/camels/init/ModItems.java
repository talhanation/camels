package com.talhanation.camels.init;

import com.google.common.collect.Lists;
import com.talhanation.camels.Main;
import com.talhanation.camels.util.RegistryUtils;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final List<RegistryObject<Item>> SPAWN_EGGS = Lists.newArrayList();

    public static final RegistryObject<Item> CAMEL_SPAWN_EGG = RegistryUtils.createSpawnEggItem("camel", ModEntityTypes.CAMEL::get, 16755200, 16777045);
    public static final RegistryObject<Item> DROMEDARY_SPAWN_EGG = RegistryUtils.createSpawnEggItem("dromedary", ModEntityTypes.DROMEDARY::get, 16755200, 16777045);

}
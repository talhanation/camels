package com.talhanation.camels.items;

import com.google.common.collect.Lists;
import com.talhanation.camels.Main;
import com.talhanation.camels.init.ModEntityTypes;
import com.talhanation.camels.util.RegistryUtils;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@SuppressWarnings("deprecation")
public class ModItems {
        public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Main.MOD_ID);
        public static final List<RegistryObject<Item>> SPAWN_EGGS = Lists.newArrayList();

    public static final RegistryObject<Item> CAMEL_SPAWN_EGG = RegistryUtils.createSpawnEggItem("camel", () -> ModEntityTypes.CAMEL_ENTITY.get(), 16755200, 16777045);
}


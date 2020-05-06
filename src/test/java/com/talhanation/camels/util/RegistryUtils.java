package com.talhanation.camels.util;

import com.talhanation.camels.items.CamelSpawnEggItem;
import com.talhanation.camels.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class RegistryUtils {

    public static RegistryObject<Item> createSpawnEggItem(String entityName, Supplier<EntityType<?>> supplier, int primaryColor, int secondaryColor) {
        RegistryObject<Item> spawnEgg = ModItems.ITEMS.register(entityName + "_spawn_egg", () -> new CamelSpawnEggItem(supplier, primaryColor, secondaryColor, new Item.Properties().group(ItemGroup.MISC)));
        ModItems.SPAWN_EGGS.add(spawnEgg);
        return spawnEgg;
    }
}

package com.talhanation.camels.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;


public class CamelsSpawnEgg extends SpawnEggItem {

    private Supplier<EntityType<?>> entityType;

    public CamelsSpawnEgg(Supplier<EntityType<?>> entityType, int primaryColor, int secondaryColor, Properties properties){
        super(null, primaryColor, secondaryColor, properties);
        this.entityType = entityType;
    }

    @Override
    public EntityType<?> getType(CompoundTag compound){
        if(compound != null && compound.contains("EntityTag", 10)) {
            CompoundTag entityTag = compound.getCompound("EntityTag");

            if(entityTag.contains("id", 8)) {
                return EntityType.byString(entityTag.getString("id")).orElse(this.entityType.get());
            }
        }
        return this.entityType.get();
    }
}
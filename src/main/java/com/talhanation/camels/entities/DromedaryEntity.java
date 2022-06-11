package com.talhanation.camels.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class DromedaryEntity extends AbstractCamelEntity{


    public DromedaryEntity(EntityType<? extends AbstractChestedHorse> type, Level level) {
        super(type, level);
    }

    protected void randomizeAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBaseChestedHorseAttributes()
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.MAX_HEALTH, 20.0D);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_30774_, DifficultyInstance p_30775_, MobSpawnType p_30776_, @Nullable SpawnGroupData p_30777_, @Nullable CompoundTag p_30778_) {
        this.initSpawn();
        return super.finalizeSpawn(p_30774_, p_30775_, p_30776_, p_30777_, p_30778_);
    }

    public void initSpawn(){
        this.setVariant(random.nextInt(3));
        this.randomizeAttributes();
    }
}

package com.talhanation.camels.entities;

import com.talhanation.camels.entities.ai.CamelFollowCaravanGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WoolCarpetBlock;

import javax.annotation.Nullable;

public class AbstractCamelEntity extends AbstractChestedHorse {
    @Nullable
    private AbstractCamelEntity caravanHead;
    @Nullable
    private AbstractCamelEntity caravanTail;
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(AbstractCamelEntity.class, EntityDataSerializers.INT);

    protected AbstractCamelEntity(EntityType<? extends AbstractChestedHorse> type, Level level) {
        super(type, level);
        this.maxUpStep = 1.2F;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int x) {
        this.entityData.set(VARIANT, x);
    }

    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getVariant());
        if (!this.inventory.getItem(1).isEmpty()) {
            nbt.put("DecorItem", this.inventory.getItem(1).save(new CompoundTag()));
        }
        if (!this.inventory.getItem(2).isEmpty()) {
            nbt.put("ArmorItem", this.inventory.getItem(2).save(new CompoundTag()));
        }

    }


    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setVariant(nbt.getInt("Variant"));
        if (nbt.contains("DecorItem", 10)) {
            this.inventory.setItem(1, ItemStack.of(nbt.getCompound("DecorItem")));
        }
        if (nbt.contains("ArmorItem", 10)) {
            this.inventory.setItem(2, ItemStack.of(nbt.getCompound("ArmorItem")));
        }

        this.updateContainerEquipment();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new CamelFollowCaravanGoal(this, (double)2.1F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2F, false));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.25D, Ingredient.of(Items.HAY_BLOCK), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    protected int getInventorySize() {
        return this.hasChest() ? 30 : 3;
        //armor = 1     id = 2
        //carpet = 1    id = 1
        //chest = 27    id = 3-30
        //saddle = 1    id = 0
    }

    public double getPassengersRidingOffset() {
        return (double)this.getBbHeight() * 0.6D;
    }

    public boolean canWearArmor() {
        return true;
    }

    public boolean isWearingArmor() {
        return !this.inventory.getItem(2).isEmpty();
    }

    public boolean isWearingCarpet() {
        return !this.inventory.getItem(1).isEmpty();
    }

    public boolean isSaddleable() {
        return true;
    }

    public boolean isCarpet(ItemStack stack) {
        return stack.is(ItemTags.CARPETS);
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource p_30609_) {

        return null;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {

        return null;
    }

    public int getMaxTemper() {
        return 60;
    }

    @Nullable
    protected SoundEvent getAngrySound() {
        return null;
    }

    public int getMaxSpawnClusterSize() {
        return 3;
    }

    public void openInventory(Player p_30621_) {
        if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(p_30621_)) && this.isTamed()) {

        }

    }

    public void leaveCaravan() {
        if (this.caravanHead != null) {
            this.caravanHead.caravanTail = null;
        }

        this.caravanHead = null;
    }

    public void joinCaravan(AbstractCamelEntity p_30767_) {
        this.caravanHead = p_30767_;
        this.caravanHead.caravanTail = this;
    }

    public boolean hasCaravanTail() {
        return this.caravanTail != null;
    }

    public boolean inCaravan() {
        return this.caravanHead != null;
    }

    @Nullable
    public AbstractCamelEntity getCaravanHead() {
        return this.caravanHead;
    }

    protected double followLeashSpeed() {
        return 2.0D;
    }

    protected void followMommy() {
        if (!this.inCaravan() && this.isBaby()) {
            super.followMommy();
        }

    }

    @Nullable
    private static DyeColor getDyeColor(ItemStack p_30836_) {
        Block block = Block.byItem(p_30836_.getItem());
        return block instanceof WoolCarpetBlock ? ((WoolCarpetBlock)block).getColor() : null;
    }


    protected double generateRandomJumpStrength() {
        return (double)0.15F + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
    }

    protected double generateRandomSpeed() {
        return ((double)0.15F + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }
}

package com.talhanation.camels.entities;

import java.util.Iterator;
import javax.annotation.Nullable;

import com.talhanation.camels.init.ModEntityTypes;
import com.talhanation.camels.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EntityCamel extends AbstractChestedHorseEntity {
    private static final DataParameter<Integer> DATA_STRENGTH_ID;
    private static final DataParameter<Integer> DATA_COLOR_ID;
    private static final DataParameter<Integer> DATA_VARIANT_ID;
    public int tailCounter;
    public int eatingCounter;

    @Nullable
    private EntityCamel caravanHead;
    @Nullable
    private EntityCamel caravanTail;

    public EntityCamel(EntityType<? extends EntityCamel> entity, World world) {
        super(entity, world);
    }


    private void setStrength(int x) {
        this.dataManager.set(DATA_STRENGTH_ID, Math.max(1, Math.min(5, x)));
    }

    private void setRandomStrength() {
        int x = this.rand.nextFloat() < 0.04F ? 5 : 3;
        this.setStrength(3 + this.rand.nextInt(x));
    }

    private int getStrength() {
        return this.dataManager.get(DATA_STRENGTH_ID);
    }

    public void writeAdditional(CompoundNBT comnbt) {
        super.writeAdditional(comnbt);
        comnbt.putInt("Variant", this.getVariant());
        comnbt.putInt("Strength", this.getStrength());
        if (!this.horseChest.getStackInSlot(1).isEmpty()) {
            comnbt.put("DecorItem", this.horseChest.getStackInSlot(1).write(new CompoundNBT()));
        }

    }

    public void readAdditional(CompoundNBT comnbt) {
        this.setStrength(comnbt.getInt("Strength"));
        super.readAdditional(comnbt);
        this.setVariant(comnbt.getInt("Variant"));
        if (comnbt.contains("DecorItem", 10)) {
            this.horseChest.setInventorySlotContents(1, ItemStack.read(comnbt.getCompound("DecorItem")));
        }

        this.updateHorseSlots();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
       // this.goalSelector.addGoal(2, new LlamaFollowCaravanGoal(this, 2.0999999046325684D));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.1F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_STRENGTH_ID, 0);
        this.dataManager.register(DATA_COLOR_ID, -1);
        this.dataManager.register(DATA_VARIANT_ID, 0);
    }

    private int getVariant() {
        return MathHelper.clamp(this.dataManager.get(DATA_VARIANT_ID), 0, 3);
    }

    private void setVariant(int variant) {
        this.dataManager.set(DATA_VARIANT_ID, variant);
    }

    protected int getInventorySize() {
        return this.hasChest() ? 2 + 3 * this.getInventoryColumns() : super.getInventorySize();
    }
    @Nullable
    public void updatePassenger(Entity entity) {
        if (this.isPassenger(entity)) {
            float x = MathHelper.cos(this.renderYawOffset * 0.017453292F);
            float y = MathHelper.sin(this.renderYawOffset * 0.017453292F);
            float z = 0.3F;
            entity.setPosition(this.getPosX() + (double)(0.3F * y), this.getPosY() + this.getMountedYOffset() + entity.getYOffset(), this.getPosZ() - (double)(0.3F * x));
        }
    }

    public double getMountedYOffset() {
        return (double)this.getHeight() * 0.87D;
    }

    public boolean canBeSteered() {
        return true;
    }

    @Nullable
    protected boolean handleEating(PlayerEntity player, ItemStack stack) {
        boolean flag = false;
        float f = 0.0F;
        int i = 0;
        int j = 0;
        Item item = stack.getItem();
        if (item == Items.WHEAT) {
            f = 2.0F;
            i = 20;
            j = 3;
        } else if (item == Items.SUGAR) {
            f = 1.0F;
            i = 30;
            j = 3;
        } else if (item == Blocks.HAY_BLOCK.asItem()) {
            f = 20.0F;
            i = 180;
        } else if (item == Items.APPLE) {
            f = 3.0F;
            i = 60;
            j = 3;
        } else if (item == Items.GOLDEN_CARROT) {
            f = 4.0F;
            i = 60;
            j = 5;
            if (this.isTame() && this.getGrowingAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(player);
            }
        } else if (item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE) {
            f = 10.0F;
            i = 240;
            j = 10;
            if (this.isTame() && this.getGrowingAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(player);
            }
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            flag = true;
        }

        if (this.isChild() && i > 0) {
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), 0.0D, 0.0D, 0.0D);
            if (!this.world.isRemote) {
                this.addGrowth(i);
            }

            flag = true;
        }

        if (j > 0 && (flag || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            flag = true;
            if (!this.world.isRemote) {
                this.increaseTemper(j);
            }
        }
        if (flag && !this.isSilent()) {
            this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LLAMA_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        }
        return flag;

    }

    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || this.isEatingHaystack();
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance diff, SpawnReason spawn, @Nullable ILivingEntityData data, @Nullable CompoundNBT comnbt) {
        this.setRandomStrength();
        int x;
        if (data instanceof EntityCamel.CamelData) {
            x = ((EntityCamel.CamelData)data).variant;
        } else {
            x = this.rand.nextInt(4);
            data = new EntityCamel.CamelData(x);
        }

        this.setVariant(x);
        return super.onInitialSpawn(world, diff, spawn, (ILivingEntityData)data, comnbt);
    }

    protected SoundEvent getAngrySound() {
        return this.isChild() ? SoundInit.ENTITY_CAMEL_ANGRY_BABY.get() : SoundInit.ENTITY_CAMEL_ANGRY.get();
    }
    protected SoundEvent getAmbientSound() {
        return this.isChild() ? SoundInit.ENTITY_CAMEL_AMBIENT_BABY.get() : SoundInit.ENTITY_CAMEL_AMBIENT.get();
    }
    protected SoundEvent getHurtSound(DamageSource damage) {
        return this.isChild() ? SoundInit.ENTITY_CAMEL_HURT_BABY.get() : SoundInit.ENTITY_CAMEL_HURT.get();
    }
    protected SoundEvent getDeathSound() {
        return this.isChild() ? SoundInit.ENTITY_CAMEL_DEATH_BABY.get() : SoundInit.ENTITY_CAMEL_DEATH.get();
    }


    protected void playStepSound(BlockPos blockpos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }
    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_MULE_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

    public int getInventoryColumns() {
        return this.getStrength();
    }

    public boolean wearsArmor() {
        return true;
    }

    public boolean isArmor(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return ItemTags.CARPETS.contains(item);
    }

    public boolean canBeSaddled() {
        return true;
    }

    public void onInventoryChanged(IInventory inventory) {
        DyeColor dye1 = this.getColor();
        super.onInventoryChanged(inventory);
        DyeColor dye2 = this.getColor();
        if (this.ticksExisted > 20 && dye2 != null && dye2 != dye1) {
            this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
        }

    }

    protected void updateHorseSlots() {
        if (!this.world.isRemote) {
            super.updateHorseSlots();
            this.setColor(getCarpetColor(this.horseChest.getStackInSlot(1)));
        }
    }

    private void setColor(@Nullable DyeColor dye) {
        this.dataManager.set(DATA_COLOR_ID, dye == null ? -1 : dye.getId());
    }

    @Nullable
    private static DyeColor getCarpetColor(ItemStack itemStack) {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        return block instanceof CarpetBlock ? ((CarpetBlock)block).getColor() : null;
    }

    @Nullable
    public DyeColor getColor() {
        int x = (Integer)this.dataManager.get(DATA_COLOR_ID);
        return x == -1 ? null : DyeColor.byId(x);
    }

    public int getMaxTemper() {
        return 60;
    }

    public boolean canMateWith(AnimalEntity animal) {
        return animal != this && animal instanceof EntityCamel && this.canMate() && ((EntityCamel)animal).canMate();
    }
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        EntityCamel entity = new EntityCamel(ModEntityTypes.CAMEL_ENTITY.get(), this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)),
                SpawnReason.BREEDING, (ILivingEntityData) null, (CompoundNBT) null);
        return entity;
    }



    public boolean onLivingFall(float x, float y) {
        int z = this.func_225508_e_(x, y);
        if (z <= 0) {
            return false;
        } else {
            if (x >= 6.0F) {
                this.attackEntityFrom(DamageSource.FALL, (float)z);
                if (this.isBeingRidden()) {
                    Iterator var4 = this.getRecursivePassengers().iterator();

                    while(var4.hasNext()) {
                        Entity entity = (Entity)var4.next();
                        entity.attackEntityFrom(DamageSource.FALL, (float)z);
                    }
                }
            }

            this.playFallSound();
            return true;
        }
    }

    public void leaveCaravan() {
        if (this.caravanHead != null) {
            this.caravanHead.caravanTail = null;
        }

        this.caravanHead = null;
    }

    public void joinCaravan(EntityCamel entity) {
        this.caravanHead = entity;
        this.caravanHead.caravanTail = this;
    }

    public boolean hasCaravanTrail() {
        return this.caravanTail != null;
    }

    public boolean inCaravan() {
        return this.caravanHead != null;
    }

    @Nullable
    public EntityCamel getCaravanHead() {
        return this.caravanHead;
    }

    protected double followLeashSpeed() {
        return 1.0D;
    }

    protected void followMother() {
        if (!this.inCaravan() && this.isChild()) {
            super.followMother();
        }

    }

    public boolean canEatGrass() {
        return true;
    }


    static {
        DATA_STRENGTH_ID = EntityDataManager.createKey(EntityCamel.class, DataSerializers.VARINT);
        DATA_COLOR_ID = EntityDataManager.createKey(EntityCamel.class, DataSerializers.VARINT);
        DATA_VARIANT_ID = EntityDataManager.createKey(EntityCamel.class, DataSerializers.VARINT);
    }

    static class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal(EntityCamel entity) {
            super(entity, new Class[0]);
        }

        public boolean shouldContinueExecuting() {
            if (this.goalOwner instanceof EntityCamel) {
                EntityCamel entity = (EntityCamel)this.goalOwner;
            }
            return super.shouldContinueExecuting();
        }
    }

    static class CamelData extends AgeableData {
        public final int variant;

        private CamelData(int x) {
            this.variant = x;
        }
    }
    public void livingTick() {
        if (this.rand.nextInt(200) == 0) {
            this.moveTail();
        }

        super.livingTick();
        if (!this.world.isRemote && this.isAlive()) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

            if (this.canEatGrass()) {
                if (!this.isEatingHaystack() && !this.isBeingRidden() && this.rand.nextInt(300) == 0 && this.world.getBlockState((new BlockPos(this)).down()).getBlock() == Blocks.GRASS_BLOCK) {
                    this.setEatingHaystack(true);
                }

                if (this.isEatingHaystack() && ++this.eatingCounter > 50) {
                    this.eatingCounter = 0;
                    this.setEatingHaystack(false);
                }
            }

            this.followMother();
        }
    }
    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 1.75F;
    }
    //hitbox
    @Override
    public float getRenderScale() {
        return this.isChild() ? 0.95F : 1.9F;
    }

}

package com.talhanation.camels.entities;

import java.util.Iterator;
import javax.annotation.Nullable;

import com.talhanation.camels.entities.ai.goal.CamelFollowCaravanGoal;
import com.talhanation.camels.init.ModEntityTypes;
import com.talhanation.camels.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
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
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CamelEntity extends LlamaEntity {
    private static final Ingredient BreedItems;
    private static final DataParameter<Integer> DATA_STRENGTH_ID = EntityDataManager.createKey(CamelEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.createKey(CamelEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.createKey(CamelEntity.class, DataSerializers.VARINT);
    public int tailCounter;

    @Nullable
    private CamelEntity caravanHead;
    @Nullable
    private CamelEntity caravanTail;

    @Override
    protected void func_230273_eI_() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.getModifiedMaxHealth());

    }
    @Override
    protected float getModifiedMaxHealth() {
        return 25.0F + (float)this.rand.nextInt(8) + (float)this.rand.nextInt(9);
    }

    public CamelEntity(EntityType<? extends CamelEntity> type, World world) {

        super(type,world);
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return BreedItems.test(itemStack);
    }

    private void setStrength(int x) {
        this.dataManager.set(DATA_STRENGTH_ID, Math.max(2, Math.min(10, x)));
    }

    private void setRandomStrength() {
        int x = this.rand.nextFloat() < 0.04F ? 5 : 3;
        this.setStrength(5 + this.rand.nextInt(x));
    }

    public int getStrength() {
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

       // this.updateHorseSlots();
        this.func_230275_fc_();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new CamelFollowCaravanGoal(this, 2.5D));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.17F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 40.00F)
                .createMutableAttribute(Attributes.HORSE_JUMP_STRENGTH, 0.33F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_STRENGTH_ID, 0);
        this.dataManager.register(DATA_COLOR_ID, -1);
        this.dataManager.register(DATA_VARIANT_ID, 0);
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataManager.get(DATA_VARIANT_ID), 0, 4);
    }

    public void setVariant(int variant) {
        this.dataManager.set(DATA_VARIANT_ID, variant);
    }

    protected int getInventorySize() {
        return this.hasChest() ? 2 + 5 * this.getInventoryColumns() : super.getInventorySize();
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
        return (double)this.getHeight() * 0.78D;
    }

    public boolean canBeSteered() {
        return true;
    }

    public ActionResultType func_241395_b_(PlayerEntity p_241395_1_, ItemStack p_241395_2_) {
        boolean flag = this.handleEating(p_241395_1_, p_241395_2_);
        if (!p_241395_1_.abilities.isCreativeMode) {
            p_241395_2_.shrink(1);
        }

        if (this.world.isRemote) {
            return ActionResultType.CONSUME;
        } else {
            return flag ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
    }
    @Override
    protected boolean handleEating(PlayerEntity playerEntity, ItemStack itemStack) {
        int i = 0;
        int j = 0;
        float f = 0.0F;
        boolean b = false;
        Item item = itemStack.getItem();
        if (item == Items.WHEAT) {
            i = 10;
            j = 3;
            f = 2.0F;
        } else if (item == Items.SUGAR) {
            f = 1.0F;
            i = 30;
            j = 3;
        } else if (item == Items.APPLE) {
            f = 3.0F;
            i = 60;
            j = 3;
        }else if (item == Blocks.HAY_BLOCK.asItem()) {
            i = 90;
            j = 6;
            f = 10.0F;
            if (this.isTame() && this.getGrowingAge() == 0 && this.canFallInLove()) {
                b = true;
                this.setInLove(playerEntity);
            }
        }
        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            b = true;
        }

        if (this.isChild() && i > 0) {
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), 0.0D, 0.0D, 0.0D);
            if (!this.world.isRemote) {
                this.addGrowth(i);
            }

            b = true;
        }

        if (j > 0 && (b || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            b = true;
            if (!this.world.isRemote) {
                this.increaseTemper(j);
            }
        }

        if (b && !this.isSilent()) {
            SoundEvent lvt_8_1_ = this.func_230274_fe_();
            if (lvt_8_1_ != null) {
                this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), this.func_230274_fe_(), this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
            }
        }

        return b;
    }

    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || this.isEatingHaystack();
    }

    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance diff, SpawnReason spawn, @Nullable ILivingEntityData data, @Nullable CompoundNBT comnbt) {
        this.setRandomStrength();
        int x;
        if (data instanceof CamelData) {
            x = ((CamelData)data).variant;
        } else {
            x = this.rand.nextInt(4);
            data = new CamelData(x);
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

   // public boolean wearsArmor() {return true;}
    public boolean func_230276_fq_() {
        return true;
    }


    public boolean isArmor(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return ItemTags.CARPETS.contains(item);
    }

    //public boolean canBeSaddled() {return true;}
    public boolean func_230264_L__() {
        return true;
    }


    public void onInventoryChanged(IInventory inventory) {
        DyeColor dye1 = this.getColor();
        super.onInventoryChanged(inventory);
        DyeColor dye2 = this.getColor();
        if (this.ticksExisted > 20 && dye2 != null && dye2 != dye1) {
           // this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
        }

    }

    /*protected void updateHorseSlots() {
        if (!this.world.isRemote) {
            super.updateHorseSlots();
            this.setColor(getCarpetColor(this.horseChest.getStackInSlot(1)));
        }
    }*/
    protected void func_230275_fc_() {
        if (!this.world.isRemote) {
            super.func_230275_fc_();
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
        return animal != this && animal instanceof CamelEntity && this.canMate() && ((CamelEntity)animal).canMate();
    }

    @Override
    public CamelEntity func_241840_a(ServerWorld serverWorld, AgeableEntity parent ) {
        CamelEntity camelEntity = this.createChild();
        this.setOffspringAttributes(parent, camelEntity);
        CamelEntity camelEntity1 = (CamelEntity)parent;
        int i = this.rand.nextInt(Math.max(this.getStrength(), camelEntity1.getStrength())) + 1;
        if (this.rand.nextFloat() < 0.03F) {
            ++i;
        }
        camelEntity.setStrength(i);
        camelEntity.setVariant(this.rand.nextBoolean() ? this.getVariant() : camelEntity1.getVariant());
        return camelEntity;
    }

    protected CamelEntity createChild() {
        return ModEntityTypes.CAMEL_ENTITY.get().create(this.world);
    }

        public boolean onLivingFall(float x, float y) {
            int z = this.calculateFallDamage(x, y);
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

    public void joinCaravan(CamelEntity entity) {
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
    public CamelEntity getCaravanHead() {
        return this.caravanHead;
    }

    protected double followLeashSpeed() {
        return 1.5D;
    }

    protected void followMother() {
        if (!this.inCaravan() && this.isChild()) {
            super.followMother();
        }

    }

    public boolean canEatGrass() {
        return false;
    }


    static class CamelData extends AgeableData {
        public final int variant;

        private CamelData(int x) {
            super(true);
            this.variant = x;
        }
    }
    public void livingTick() {
        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (this.rand.nextInt(200) == 0) {
            this.moveTail();
        }

        super.livingTick();
        if (!this.world.isRemote && this.isAlive()) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

            this.followMother();
        }
    }
    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 1.3F;
    }
    //hitbox
    @Override
    public float getRenderScale() {
        return this.isChild() ? 0.75F : 1.85F;
    }



    static {
        BreedItems = Ingredient.fromItems(new IItemProvider[]{Items.WHEAT, Blocks.HAY_BLOCK.asItem()});
    }

}

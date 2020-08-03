package com.talhanation.camels.entity;


import com.talhanation.camels.LootTableHandler;
import com.talhanation.camels.entity.ai.AICamelCaravan;
import com.talhanation.camels.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Iterator;

public class EntityCamel  extends EntityLlama {
    private static final DataParameter<Integer> DATA_STRENGTH_ID;
    private static final DataParameter<Integer> DATA_COLOR_ID;
    private static final DataParameter<Integer> DATA_VARIANT_ID;

    public int tailCounter;
    private int eatingCounter;
    @Nullable
    private EntityCamel caravanHead;
    @Nullable
    private EntityCamel caravanTail;


    public EntityCamel(World world) {
        super(world);
    }

    private void setStrength(int x) {
        this.dataManager.set(DATA_STRENGTH_ID, Math.max(1, Math.min(5, x)));
    }

    private void setRandomStrength() {
        int x = this.rand.nextFloat() < 0.04F ? 5 : 3;
        this.setStrength(4 + this.rand.nextInt(x));
    }

    public int getStrength() {
        return (Integer)this.dataManager.get(DATA_STRENGTH_ID);
    }

    public void writeEntityToNBT(NBTTagCompound comnbt) {
        super.writeEntityToNBT(comnbt);
        comnbt.setInteger("Variant", this.getVariant());
        comnbt.setInteger("Strength", this.getStrength());
        if (!this.horseChest.getStackInSlot(1).isEmpty()) {
            comnbt.setTag("DecorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
    }

    public void readEntityFromNBT(NBTTagCompound comnbt) {
        this.setStrength(comnbt.getInteger("Strength"));
        super.readEntityFromNBT(comnbt);
        this.setVariant(comnbt.getInteger("Variant"));
        if (comnbt.hasKey("DecorItem", 10)) {
            this.horseChest.setInventorySlotContents(1, new ItemStack(comnbt.getCompoundTag("DecorItem")));
        }

        this.updateHorseSlots();
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
        this.tasks.addTask(2, new AICamelCaravan(this, 2.0999999046325684D));
        this.tasks.addTask(3, new EntityAIPanic(this, 1.2D));
        this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.25F);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(DATA_STRENGTH_ID, 0);
        this.dataManager.register(DATA_COLOR_ID, -1);
        this.dataManager.register(DATA_VARIANT_ID, 0);
    }

    public int getVariant() {
        return MathHelper.clamp((Integer)this.dataManager.get(DATA_VARIANT_ID), 0, 3);
    }

    public void setVariant(int variant) {
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
            entity.setPosition(this.posX + (double)(0.3F * y), this.posY + this.getMountedYOffset() + entity.getYOffset(), this.posZ - (double)(0.3F * x));
        }
    }

    public double getMountedYOffset() {
        return (double)this.height * 0.78D;
    }

    public boolean canBeSteered() {
        return true;
    }

    @Nullable
    protected boolean handleEating(EntityPlayer player, ItemStack stack)
    {
        boolean flag = false;
        float f = 0.0F;
        int i = 0;
        int j = 0;
        Item item = stack.getItem();

        if (item == Items.WHEAT)
        {
            f = 2.0F;
            i = 20;
            j = 3;
        }
        else if (item == Items.SUGAR)
        {
            f = 1.0F;
            i = 30;
            j = 3;
        }
        else if (item == Item.getItemFromBlock(Blocks.HAY_BLOCK))
        {
            f = 20.0F;
            i = 180;
        }
        else if (item == Items.APPLE)
        {
            f = 3.0F;
            i = 60;
            j = 3;
        }
        else if (item == Items.GOLDEN_CARROT)
        {
            f = 4.0F;
            i = 60;
            j = 5;

            if (this.isTame() && this.getGrowingAge() == 0 && !this.isInLove())
            {
                flag = true;
                this.setInLove(player);
            }
        }
        else if (item == Items.GOLDEN_APPLE)
        {
            f = 10.0F;
            i = 240;
            j = 10;

            if (this.isTame() && this.getGrowingAge() == 0 && !this.isInLove())
            {
                flag = true;
                this.setInLove(player);
            }
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F)
        {
            this.heal(f);
            flag = true;
        }

        if (this.isChild() && i > 0)
        {
            this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D);

            if (!this.world.isRemote)
            {
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
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        }
        return flag;

    }

    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || this.isEatingHaystack();
    }

    public IEntityLivingData onInitialSpawn(DifficultyInstance diff, @Nullable IEntityLivingData entityLivingData) {
        IEntityLivingData data = super.onInitialSpawn(diff, entityLivingData);
        this.setRandomStrength();
        int lvt_3_2_;
        if (data instanceof EntityCamel.GroupData) {
            lvt_3_2_ = ((EntityCamel.GroupData)data).variant;
        } else {
            lvt_3_2_ = this.rand.nextInt(4);
            data = new EntityCamel.GroupData(lvt_3_2_);
        }

        this.setVariant(lvt_3_2_);
        return (IEntityLivingData)data;
    }

    protected SoundEvent getAngrySound() {
        return SoundInit.ENTITY_CAMEL_ANGRY;
    }
    protected SoundEvent getAmbientSound() {
        return SoundInit.ENTITY_CAMEL_AMBIENT;
    }
    protected SoundEvent getHurtSound(DamageSource damage) {
        return SoundInit.ENTITY_CAMEL_HURT;
    }
    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_CAMEL_DEATH;
    }

    protected void playStepSound(BlockPos blockpos, Block blockState) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }
    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_MULE_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }
    @Override
    public void makeMad()
    {
        SoundEvent soundevent = this.getAngrySound();

        if (soundevent != null)
        {
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.CAMEL;
    }
    public int getInventoryColumns() {
        return this.getStrength();
    }

    public boolean wearsArmor() {
        return true;
    }

    public boolean isArmor(ItemStack itemStack) {
        return itemStack.getItem() == Item.getItemFromBlock(Blocks.CARPET);
    }

    public boolean canBeSaddled() {
        return true;
    }

    public void onInventoryChanged(IInventory inventory) {
        EnumDyeColor dye1 = this.getColor();
        super.onInventoryChanged(inventory);
        EnumDyeColor dye2 = this.getColor();
        if (this.ticksExisted > 20 && dye2 != null && dye2 != dye1) {
            this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
        }
    }

    protected void updateHorseSlots() {
        if (!this.world.isRemote) {
            super.updateHorseSlots();
            this.setColorByItem(this.horseChest.getStackInSlot(1));
        }
    }

    private void setColor(@Nullable EnumDyeColor dye) {
        this.dataManager.set(DATA_COLOR_ID, dye == null ? -1 : dye.getMetadata());
    }

    private void setColorByItem(ItemStack itemStack) {
        if (this.isArmor(itemStack)) {
            this.setColor(EnumDyeColor.byMetadata(itemStack.getMetadata()));
        } else {
            this.setColor((EnumDyeColor)null);
        }

    }

    @Nullable
    public EnumDyeColor getColor() {
        int x = (Integer)this.dataManager.get(DATA_COLOR_ID);
        return x == -1 ? null : EnumDyeColor.byMetadata(x);
    }

    public int getMaxTemper() {
        return 60;
    }

    public boolean canMateWith(EntityAnimal animal) {
        return animal != this && animal instanceof EntityCamel && this.canMate() && ((EntityCamel)animal).canMate();
    }

    @Override
    public EntityCamel createChild(EntityAgeable ageable) {
        EntityCamel entity = new EntityCamel(this.world);
        this.setOffspringAttributes(ageable, entity);
        EntityCamel entity1 = (EntityCamel)ageable;
        int i = this.rand.nextInt(Math.max(this.getStrength(), entity1.getStrength())) + 1;
        if (this.rand.nextFloat() < 0.03F) {
            ++i;
        }

        entity.setStrength(i);
        entity.setVariant(this.rand.nextBoolean() ? this.getVariant() : entity1.getVariant());
        return entity;
    }

    public void fall(float x, float y) {
        int z = MathHelper.ceil((x * 0.5F - 3.0F) * y);
        if (z > 0) {
            if (x >= 6.0F) {
                this.attackEntityFrom(DamageSource.FALL, (float)z);
                if (this.isBeingRidden()) {
                    Iterator var4 = this.getRecursivePassengers().iterator();

                    while(var4.hasNext()) {
                        Entity lvt_5_1_ = (Entity)var4.next();
                        lvt_5_1_.attackEntityFrom(DamageSource.FALL, (float)z);
                    }
                }
            }

            IBlockState blockState = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double)this.prevRotationYaw, this.posZ));
            Block lvt_5_2_ = blockState.getBlock();
            if (blockState.getMaterial() != Material.AIR && !this.isSilent()) {
                SoundType lvt_6_1_ = lvt_5_2_.getSoundType();
                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, lvt_6_1_.getStepSound(), this.getSoundCategory(), lvt_6_1_.getVolume() * 0.5F, lvt_6_1_.getPitch() * 0.75F);
            }

        }
    }


    public void setAIMoveSpeed(float speed) {
        if (isBeingRidden()) {
            super.setAIMoveSpeed(speed * 1F);
        } else {
            super.setAIMoveSpeed(speed);
        }
    }

    public void leaveCaravan() {
        if (this.caravanHead != null)
            this.caravanHead.caravanTail = null;
        this.caravanHead = null;
    }

    public void joinCaravan(EntityCamel camel) {
        this.caravanHead = camel;
        this.caravanHead.caravanTail = this;
    }

    public boolean hasCaravanTrail() {
        return (this.caravanTail != null);
    }

    public boolean inCaravan() {
        return (this.caravanHead != null);
    }

    @Nullable
    public EntityCamel getCaravanHead() {
        return this.caravanHead;
    }

    protected double followLeashSpeed() {
        return 2.0D;
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
        DATA_STRENGTH_ID = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
        DATA_COLOR_ID = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
        DATA_VARIANT_ID = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
    }

    static class GroupData implements IEntityLivingData {
        public int variant;

        private GroupData(int x) {
            this.variant = x;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasColor()
    {
        return this.getColor() != null;
    }
}
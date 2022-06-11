package com.talhanation.camels.inventory;

import com.talhanation.camels.Main;
import com.talhanation.camels.entities.AbstractCamelEntity;
import de.maxhenkel.corelib.inventory.ContainerBase;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CamelsInventoryContainer extends ContainerBase {

    private final Container camelInventory;
    private final AbstractCamelEntity camelEntity;


    public CamelsInventoryContainer(int id, AbstractCamelEntity camel, Inventory playerInventory) {
        super(Main.CAMELS_CONTAINER_TYPE, id, playerInventory, camel.getInventory());
        this.camelEntity = camel;
        this.camelInventory = camel.getInventory();

        addCamelInventorySlots();
        addCamelArmorSlot();
        addCamelCarpetSlot();
        addCamelSaddleSlot();
        addPlayerInventorySlots();
    }

    public AbstractCamelEntity getCamelEntity() {
        return camelEntity;
    }

    @Override
    public int getInvOffset() {
        return 56;
    }

    public void addCamelSaddleSlot(){
        this.addSlot(new Slot(camelInventory, 0, 8, 18) {
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.SADDLE) && !this.hasItem();
            }
        });
    }

    public void addCamelArmorSlot(){
        this.addSlot(new Slot(camelInventory, 2, 8, 36) {
            public boolean mayPlace(ItemStack stack) {
                return camelEntity.isArmor(stack);
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    public void addCamelCarpetSlot(){
        this.addSlot(new Slot(camelInventory, 1, 8, 54) {
            public boolean mayPlace(ItemStack stack) {
                return camelEntity.isCarpet(stack);
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
    }

    public void addCamelInventorySlots() {
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                this.addSlot(new Slot(camelInventory, 0 + l + k * camelEntity.getInventoryColumns(), 2 * 18 + 82 + l * 18,  18 + k * 18));
            }
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return this.camelInventory.stillValid(playerIn) && this.camelEntity.isAlive() && this.camelEntity.distanceTo(playerIn) < 8.0F;
    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
    }

}
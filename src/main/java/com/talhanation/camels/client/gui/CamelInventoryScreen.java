package com.talhanation.camels.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.talhanation.camels.entities.AbstractCamelEntity;
import com.talhanation.camels.inventory.CamelsInventoryContainer;
import com.talhanation.camels.Main;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CamelInventoryScreen extends ScreenBase<CamelsInventoryContainer> {
    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(Main.MOD_ID,"textures/gui/camel_gui.png" );

    private static final int fontColor = 4210752;

    private final AbstractCamelEntity camelEntity;
    private final Inventory playerInventory;

    private int group;
    private int follow;
    private int aggro;

    public CamelInventoryScreen(CamelsInventoryContainer recruitContainer, Inventory playerInventory, Component title) {
        super(RESOURCE_LOCATION, recruitContainer, playerInventory, title);
        this.camelEntity = recruitContainer.getCamelEntity();
        this.playerInventory = playerInventory;
        imageWidth = 176;
        imageHeight = 218;
    }


    @Override
    protected void init() {
        super.init();

    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        //Titles
        font.draw(matrixStack, camelEntity.getDisplayName().getVisualOrderText(), 8, 5, fontColor);
        font.draw(matrixStack, playerInventory.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, fontColor);
    }

    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);

        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        InventoryScreen.renderEntityInInventory(i + 51, j + 60, 17, (float)(i + 51) - mouseX, (float)(j + 75 - 50) - mouseY, this.camelEntity);
    }
}

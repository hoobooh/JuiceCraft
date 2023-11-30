package com.usagin.juicecraft.client.renderer;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.FriendMenu;
import com.usagin.juicecraft.JuiceCraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class FriendMenuScreen extends AbstractContainerScreen<FriendMenu>{
    public static final ResourceLocation TEXTURE = new ResourceLocation(JuiceCraft.MODID, "textures/gui/friendmenu.png");
    private static final Logger LOGGER = LogUtils.getLogger();
    public FriendMenuScreen(FriendMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth=176;
        this.imageHeight=176;
        LOGGER.info("ttest");
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTransparentBackground(pGuiGraphics);
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick){
        super.render(pGuiGraphics,  pMouseX,  pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}

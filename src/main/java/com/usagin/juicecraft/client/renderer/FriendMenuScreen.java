package com.usagin.juicecraft.client.renderer;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.FriendMenu;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.usagin.juicecraft.FriendMenuTextureLocations.*;

public class FriendMenuScreen extends AbstractContainerScreen<FriendMenu> {
    public ResourceLocation FRIEND_THEME;
    public ResourceLocation FRIEND_SOURCE;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Friend friend;
    private ImageButton skillButton;
    private ImageButton bagButton;
    private ImageButton statButton;
    WidgetSprites buttonSprite = new WidgetSprites(BUTTON_BEFORE, BUTTON_AFTER);
    public FriendMenuScreen(FriendMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 384;
        this.imageHeight = 262;
        this.friend=pMenu.getFriend();
        this.inventoryLabelX=111;
        this.inventoryLabelY=170;
        FRIEND_THEME= new ResourceLocation(JuiceCraft.MODID, "textures/gui/"+friend.getFriendName().toLowerCase() +"/"+friend.getFriendName().toLowerCase()+".png");
        FRIEND_SOURCE = new ResourceLocation(JuiceCraft.MODID, "textures/gui/"+friend.getFriendName().toLowerCase() +"/"+friend.getFriendName().toLowerCase()+"_theme.png");
    }
    private void handleSkillButton(Button btn){
        //logic
    }
    private void handleGearButton(Button btn){
        //logic
    }
    private void handleStatsButton(Button btn){
        //logic
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTransparentBackground(pGuiGraphics);
        pGuiGraphics.blit(TEXTURE, this.leftPos-1, this.topPos-1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        //render friend
        InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, this.leftPos +13, this.topPos + 18, this.leftPos+88, this.topPos + 170, 55, 0.20F, pMouseX, pMouseY, this.friend);
        //this part renders the locked slots on the inventory
        for(int n=0;n<7-friend.getInventoryRows();n++){
            for(int i=0;i<5;i++){
                pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos+182+18*i, this.topPos+150-18*n, 0, 0, 18, 18, 18, 18);
            }
        }
        if(!friend.isModular()){
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos+67, this.topPos+204,0,0,18,18,18,18);
        }
        if(!friend.isArmorable()){
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos+13,this.topPos+229,0,0,18,18,18,18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos+31,this.topPos+229,0,0,18,18,18,18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos+49,this.topPos+229,0,0,18,18,18,18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos+67,this.topPos+229,0,0,18,18,18,18);
        }
    }
    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    @Override
    protected void init(){
        super.init();
        //button renderer
        this.skillButton=addRenderableWidget(new ImageButton(this.leftPos+120,this.topPos+7,36,18,
                buttonSprite,
                this::handleSkillButton,Component.translatable("juicecraft.skills")));
        this.bagButton=addRenderableWidget(new ImageButton(this.leftPos+174,this.topPos+7,36,18,
                buttonSprite,
                this::handleGearButton,Component.translatable("juicecraft.gear")));
        this.statButton=addRenderableWidget(new ImageButton(this.leftPos+228,this.topPos+7,36,18,
                buttonSprite,
                this::handleStatsButton,Component.translatable("juicecraft.stats")));
    }
}

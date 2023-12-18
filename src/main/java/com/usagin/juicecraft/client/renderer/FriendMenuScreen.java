package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.menu.FriendMenu;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.menu.FriendSlot;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static com.usagin.juicecraft.client.menu.FriendMenuTextureLocations.*;

public class FriendMenuScreen extends AbstractContainerScreen<FriendMenu> {
    public ResourceLocation FRIEND_PORTRAIT;
    public ResourceLocation FRIEND_THEME;
    public ResourceLocation FRIEND_SOURCE;
    private static final Logger LOGGER = LogUtils.getLogger();
    final Friend friend;
    private final FriendMenu menu;
    private FriendButton skillButton;
    private FriendButton bagButton;
    private FriendButton statButton;
    private FriendButton talkButton;
    boolean skillActive = false;
    boolean statsActive = false;
    boolean hidePartial = false;
    boolean hideFull=false;
    WidgetSprites buttonSprite = new WidgetSprites(BUTTON_BEFORE, BUTTON_AFTER);

    public FriendMenuScreen(FriendMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 384;
        this.imageHeight = 262;
        this.friend = pMenu.getFriend();
        this.menu = pMenu;
        this.inventoryLabelX = 111;
        this.inventoryLabelY = 170;
        FRIEND_THEME = new ResourceLocation(JuiceCraft.MODID, "textures/gui/" + friend.getFriendName().toLowerCase() + "/" + friend.getFriendName().toLowerCase() + ".png");
        FRIEND_SOURCE = new ResourceLocation(JuiceCraft.MODID, "textures/gui/" + friend.getFriendName().toLowerCase() + "/" + friend.getFriendName().toLowerCase() + "_theme.png");
        FRIEND_PORTRAIT = new ResourceLocation(JuiceCraft.MODID, "textures/gui/" + friend.getFriendName().toLowerCase() + "/" + friend.getFriendName().toLowerCase() + "_portrait.png");
    }

    void hideMiddleScreen() {
        this.showFullScreen();
        this.hidePartial=true;
        for(Slot slot: this.menu.slots){
            if((slot.getSlotIndex()>6||slot.getSlotIndex()==0)&& !(slot.container instanceof Inventory &&slot.getSlotIndex()<9)){
                ((FriendSlot)slot).highlight=false;
            }
        }
    }

    void hideFullScreen() {
        this.hideFull=true;
        for(Slot slot: this.menu.slots){
            ((FriendSlot)slot).highlight=false;
        }
    }
    void showFullScreen(){
        for(Slot slot: this.menu.slots){
            ((FriendSlot)slot).highlight=true;
        }
        this.hidePartial=false;
        this.hideFull=false;
    }

    private boolean isValidSpot(double pMouseX, double pMouseY) {
        for(int i = 0; i < this.menu.slots.size(); ++i) {
            Slot slot = this.menu.slots.get(i);
            if (this.isSlotHovering(slot, pMouseX, pMouseY) && slot.isActive()) {
                if(this.hideFull){
                    return false;
                }
                else if(this.hidePartial){
                    if((i>6 || i==0) && !(slot.container instanceof Inventory && slot.getSlotIndex()<9)){
                        return false;
                    }
                }
            }
        }

        return true;
    }
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if(isValidSpot(pMouseX,pMouseY)){//findValidSlot determines that the click spot is not on a disabled slot
            return super.mouseClicked(pMouseX,pMouseY,pButton);
        }
        else{ //it is on a disabled slot, ignore it
            return true;
        }
    }
    private boolean isSlotHovering(Slot pSlot, double pMouseX, double pMouseY) {
        int i = pSlot.getSlotIndex();
        return this.isHovering(pSlot.x, pSlot.y, 16, 16, pMouseX, pMouseY);
    }
    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pMouseX -= (double)i;
        pMouseY -= (double)j;
        return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
    }

    private void handleSkillButton(Button btn) {
        this.hideMiddleScreen();
        this.skillActive=true;
        this.statsActive=false;
        this.bagButton.setFocused(false);
        //logic
    }

    private void handleGearButton(Button btn) {
        this.showFullScreen();
        this.statsActive=false;
        this.skillActive=false;
        //logic
    }

    private void handleStatsButton(Button btn) {
        this.hideMiddleScreen();
        this.statsActive=true;
        this.skillActive=false;
        this.bagButton.setFocused(false);
        //logic
    }

    private void handleTalkButton(Button btn) {
        this.hideFullScreen();
        this.bagButton.setFocused(false);
        //logic
    }
    void renderSkillMenu(GuiGraphics pGuiGraphics){
        pGuiGraphics.blit(SKILLMENU, this.leftPos - 1, this.topPos - 1, 1000,0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
    }
    void renderStatsMenu(GuiGraphics pGuiGraphics){
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_BLEND);
        pGuiGraphics.blit(STATMENU, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(FRIEND_PORTRAIT, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        GL11.glDisable(GL11.GL_BLEND);
        //all the text
        pGuiGraphics.drawString(this.getMinecraft().font,Component.literal(Component.translatable("juicecraft.menu.name").getString() + this.friend.getFriendName()),this.leftPos - 1+187,this.topPos - 1+50, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.getMinecraft().font,this.getResource("origin"),this.leftPos - 1+187,this.topPos - 1+70, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.getMinecraft().font,this.getResource("disposition"),this.leftPos - 1+187,this.topPos - 1+90, ChatFormatting.BLACK.getColor(), false);

        Component comp = Component.translatable("juicecraft.menu.specialties");

        pGuiGraphics.drawString(this.getMinecraft().font, comp,this.leftPos - 1+124,this.topPos - 1+110, ChatFormatting.BLACK.getColor(),false);
        renderScrollingString(pGuiGraphics,this.getMinecraft().font, this.getFriendResource("specialties"),this.leftPos - 1+124+this.getMinecraft().font.width(comp),this.topPos - 1+110, this.leftPos -1 +260,this.topPos-1+120,ChatFormatting.BLACK.getColor());

        comp = Component.translatable("juicecraft.menu.weaknesses");

        pGuiGraphics.drawString(this.getMinecraft().font, comp,this.leftPos - 1+124,this.topPos - 1+120, ChatFormatting.BLACK.getColor(),false);
        renderScrollingString(pGuiGraphics,this.getMinecraft().font, this.getFriendResource("weaknesses"),this.leftPos - 1+124+this.getMinecraft().font.width(comp),this.topPos - 1+120, this.leftPos -1 +260,this.topPos-1+130,ChatFormatting.BLACK.getColor());


        pGuiGraphics.drawString(this.getMinecraft().font,this.getIntResource("level",-1),this.leftPos - 1+124,this.topPos - 1+135, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.getMinecraft().font,this.getFloatResource("health",this.friend.getHealth()),this.leftPos - 1+124,this.topPos - 1+150, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.getMinecraft().font,this.getFloatResource("hunger",this.friend.getHungerMeter()),this.leftPos - 1+195,this.topPos - 1+150, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.getMinecraft().font,this.getIntResource("age",-1)+" Day(s)",this.leftPos - 1+124,this.topPos - 1+165, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.getMinecraft().font,this.getIntResource("hostilekilled",-1),this.leftPos - 1+124,this.topPos - 1+180, ChatFormatting.BLACK.getColor(), false);
        //
        RenderSystem.enableDepthTest();
    }
    String getResource(String s){
        return Component.translatable("juicecraft.menu."+s).getString()+Component.translatable("juicecraft.menu."+this.friend.getFriendName().toLowerCase()+"."+s).getString();
    }
    Component getFriendResource(String s){
        return Component.translatable("juicecraft.menu."+this.friend.getFriendName().toLowerCase()+"."+s);
    }
    String getFloatResource(String s, float val){
        return Component.translatable("juicecraft.menu."+s).getString()+val;
    }
    String getIntResource(String s, int val){
        return Component.translatable("juicecraft.menu."+s).getString()+val;
    }
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTransparentBackground(pGuiGraphics);
        pGuiGraphics.blit(MAINFLUIDTEXTURE, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(HUNGERBAR, this.leftPos - 1, this.topPos - 1, 0, 0, 7 + (int) (84 * this.friend.getHungerMeter() / 100), this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(HEALTHBAR, this.leftPos - 1, this.topPos - 1, 0, 0, 7 + (int) (84 * this.friend.getHealth() / this.friend.getMaxHealth()), this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(MAINTEXTURE, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        //render friend
        InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, this.leftPos + 13, this.topPos + 18, this.leftPos + 88, this.topPos + 170, 55, 0.20F, pMouseX, pMouseY, this.friend);

        //hide gear icons if there is gear there
        for (int i = 0; i < 4; i++) {
            if (!friend.inventory.getItem(i + 3).isEmpty()) { //armor slots
                pGuiGraphics.blit(CLEARSLOT, this.leftPos + 13 + 18 * i, this.topPos + 229, 0, 0, 18, 18, 18, 18);
            }
        }
        if (!friend.inventory.getItem(2).isEmpty()) { //module slot
            pGuiGraphics.blit(CLEARSLOT, this.leftPos + 67, this.topPos + 204, 0, 0, 18, 18, 18, 18);
        }
        if (!friend.inventory.getItem(1).isEmpty()) { //weapon slot
            pGuiGraphics.blit(CLEARSLOT, this.leftPos + 13, this.topPos + 204, 0, 0, 18, 18, 18, 18);
        }
        if (!friend.inventory.getItem(0).isEmpty()) { //hyper slot
            pGuiGraphics.blit(CLEARSLOT, this.leftPos + 135, this.topPos + 132, 0, 0, 18, 18, 18, 18);
        }

        //this part renders the locked slots on the inventory
        for (int n = 0; n < 7 - friend.getInventoryRows(); n++) {
            for (int i = 0; i < 5; i++) {
                pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 182 + 18 * i, this.topPos + 150 - 18 * n, 0, 0, 18, 18, 18, 18);
            }
        }
        if (!friend.isModular()) {
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 67, this.topPos + 204, 0, 0, 18, 18, 18, 18);
        }
        if (!friend.isArmorable()) {
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 13, this.topPos + 229, 0, 0, 18, 18, 18, 18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 31, this.topPos + 229, 0, 0, 18, 18, 18, 18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 49, this.topPos + 229, 0, 0, 18, 18, 18, 18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 67, this.topPos + 229, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if(isValidSpot(pMouseX,pMouseY)){
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);}

        //additional menus
        if(this.skillActive){
            this.renderSkillMenu(pGuiGraphics);
        }
        else if(this.statsActive){
            this.renderStatsMenu(pGuiGraphics);
        }
    }

    @Override
    protected void init() {
        super.init();

        //button renderer
        this.skillButton = addRenderableWidget(new FriendButton(this.leftPos + 120, this.topPos + 7, 36, 18, buttonSprite, this::handleSkillButton, Component.translatable("juicecraft.skills")));
        this.bagButton = addRenderableWidget(new FriendButton(this.leftPos + 174, this.topPos + 7, 36, 18, buttonSprite, this::handleGearButton, Component.translatable("juicecraft.gear")));
        this.statButton = addRenderableWidget(new FriendButton(this.leftPos + 228, this.topPos + 7, 36, 18, buttonSprite, this::handleStatsButton, Component.translatable("juicecraft.stats")));
        this.talkButton = addRenderableWidget(new FriendButton(this.leftPos + 335, this.topPos + 179, 36, 18, buttonSprite, this::handleTalkButton, Component.translatable("juicecraft.talk")));
        this.bagButton.setFocused(true);
    }
    protected static void renderScrollingString(GuiGraphics pGuiGraphics, Font pFont, Component pText, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
        renderScrollingString(pGuiGraphics, pFont, pText, (pMinX + pMaxX) / 2, pMinX, pMinY, pMaxX, pMaxY, pColor);
    }
    protected static void renderScrollingString(GuiGraphics pGuiGraphics, Font pFont, Component pText, int p_300294_, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
        int i = pFont.width(pText);
        int j = (pMinY + pMaxY - 9) / 2 + 1;
        int k = pMaxX - pMinX;
        if (i > k) {
            int l = i - k;
            double d0 = (double) Util.getMillis() / 500.0D;
            double d1 = Math.max((double)l * 0.5D, 3.0D);
            double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / 2.0D + 0.5D;
            double d3 = Mth.lerp(d2, 0.0D, (double)l);
            pGuiGraphics.enableScissor(pMinX, pMinY, pMaxX, pMaxY);
            pGuiGraphics.drawString(pFont, pText, pMinX - (int)d3, j, pColor,false);
            pGuiGraphics.disableScissor();
        } else {
            pGuiGraphics.drawString(pFont, pText, pMinX, pMinY, pColor,false);
        }

    }
}



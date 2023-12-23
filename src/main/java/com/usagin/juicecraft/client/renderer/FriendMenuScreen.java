package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.menu.FriendMenu;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.client.menu.FriendSlot;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.network.PacketHandler;
import com.usagin.juicecraft.network.ToServerPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.extensions.IForgeGuiGraphics;
import net.minecraftforge.common.data.SpriteSourceProvider;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import java.awt.event.ContainerEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.usagin.juicecraft.client.menu.FriendMenuTextureLocations.*;
import static net.minecraft.core.Direction.NORTH;

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
    private FriendButton dialogueOne;
    private FriendButton dialogueTwo;
    private FriendButton dialogueThree;
    private FriendButton dialogueFour;
    private FriendButton exitDialogue;
    ArrayList<FriendButton> bt = new ArrayList<>();
    ArrayList<FriendButton> talkBt = new ArrayList<>();
    boolean skillActive = false;
    boolean statsActive = false;
    boolean hidePartial = false;
    boolean talkActive=false;
    boolean hideFull = false;
    WidgetSprites buttonSprite = new WidgetSprites(BUTTON_BEFORE, BUTTON_AFTER);
    WidgetSprites upgradeSprite = new WidgetSprites(UPGRADE_BEFORE, UPGRADE_AFTER);
    WidgetSprites enableSprite = new WidgetSprites(ENABLE_BEFORE, ENABLE_AFTER);
    WidgetSprites disableSprite = new WidgetSprites(DISABLE_BEFORE, DISABLE_AFTER);

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
        this.hidePartial = true;
        for (Slot slot : this.menu.slots) {
            if (((slot.getSlotIndex() > 6 || slot.getSlotIndex() == 0) && !(slot.container instanceof Inventory)) || (slot.container instanceof Inventory && slot.getSlotIndex() > 8)) {
                ((FriendSlot) slot).highlight = false;
            }
        }
    }

    void hideFullScreen() {
        this.showFullScreen();
        this.hideFull = true;
        for (Slot slot : this.menu.slots) {
            ((FriendSlot) slot).highlight = false;
        }
    }

    void showFullScreen() {
        for (Slot slot : this.menu.slots) {
            ((FriendSlot) slot).highlight = true;
        }
        this.hidePartial = false;
        this.hideFull = false;
    }

    private boolean isValidSpot(double pMouseX, double pMouseY) {
        return true;
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (isValidSpot(pMouseX, pMouseY)) {//findValidSlot determines that the click spot is not on a disabled slot
            return super.mouseClicked(pMouseX, pMouseY, pButton);
        } else { //it is on a disabled slot, ignore it
            return true;
        }
    }

    protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        FriendSlot sl = (FriendSlot) pSlot;
        if(sl==null){
            this.minecraft.gameMode.handleInventoryMouseClick(this.menu.containerId, pSlotId, pMouseButton, pType, this.minecraft.player);
        }
        else if (sl.highlight) {
            if (pSlot != null) {
                pSlotId = pSlot.index;
            }

            this.minecraft.gameMode.handleInventoryMouseClick(this.menu.containerId, pSlotId, pMouseButton, pType, this.minecraft.player);
        }
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pMouseX -= (double) i;
        pMouseY -= (double) j;
        return pMouseX >= (double) (pX - 1) && pMouseX < (double) (pX + pWidth + 1) && pMouseY >= (double) (pY - 1) && pMouseY < (double) (pY + pHeight + 1);
    }

    private void handleSkillButton(Button btn) {
        this.hideMiddleScreen();
        this.skillActive = true;
        this.statsActive = false;
        this.bagButton.setFocus(false);
        this.statButton.setFocus(false);
        //logic
    }

    private void handleGearButton(Button btn) {
        this.showFullScreen();
        this.skillButton.setFocus(false);
        this.statButton.setFocus(false);
        this.statsActive = false;
        this.skillActive = false;
        //logic
    }

    private void handleStatsButton(Button btn) {
        this.hideMiddleScreen();
        this.statsActive = true;
        this.skillActive = false;
        this.bagButton.setFocus(false);
        this.skillButton.setFocus(false);
        //logic
    }

    private void handleTalkButton(Button btn) {
        //this.friend.combatSettings.aggression=0;
        //PacketHandler.sendToServer(new ToServerPacket(this.friend.combatSettings.makeHash(),this.friend.getId()));
        this.hideFullScreen();
        this.statsActive=false;
        this.skillActive=false;
        this.talkActive=true;
        //logic
    }
    private void handleDialogueOne(Button btn){

    }
    private void handleDialogueTwo(Button btn){

    }
    private void handleDialogueThree(Button btn){

    }
    private void handleDialogueFour(Button btn){

    }
    private void exitTalkButton(Button btn){
        this.showFullScreen();
        if(this.statsActive || this.skillActive){
            this.hideMiddleScreen();
        }
        this.talkActive=false;
    }
    private void doSkillOneUpgrade(Button btn) {

    }

    private void doSkillOneEnable(Button btn) {

    }

    private void doSkillOneDisable(Button btn) {

    }

    private void doSkillTwoUpgrade(Button btn) {

    }

    private void doSkillTwoEnable(Button btn) {

    }

    private void doSkillTwoDisable(Button btn) {

    }

    private void doSkillThreeUpgrade(Button btn) {

    }

    private void doSkillThreeEnable(Button btn) {

    }

    private void doSkillThreeDisable(Button btn) {

    }

    private void doSkillFourUpgrade(Button btn) {

    }

    private void doSkillFourEnable(Button btn) {

    }

    private void doSkillFourDisable(Button btn) {

    }

    private void doSkillFiveUpgrade(Button btn) {

    }

    private void doSkillFiveEnable(Button btn) {

    }

    private void doSkillFiveDisable(Button btn) {

    }

    private void doSkillSixUpgrade(Button btn) {

    }

    private void doSkillSixEnable(Button btn) {

    }

    private void doSkillSixDisable(Button btn) {

    }
    void renderTalkMenu(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick){
        for (Slot slot : this.menu.slots) {
            ((FriendSlot) slot).tempBypass = true;
            List<BakedQuad> bakedmodel = this.getMinecraft().getItemRenderer().getModel(slot.getItem(), this.friend.level(), this.friend, 0).getQuads(null, null, this.friend.getRandom());
            if (!bakedmodel.isEmpty()) {
                TextureAtlasSprite sprite = bakedmodel.get(0).getSprite();
                for (BakedQuad quad : bakedmodel) {
                    int i = -1;
                    float f,f1,f2;
                    if (bakedmodel.get(0).isTinted()) {
                        sprite = quad.getSprite();
                        i = this.getMinecraft().getItemColors().getColor(slot.getItem(), quad.getTintIndex());
                    }
                    f = (float) (i >> 16 & 255) / 255.0F;
                    f1 = (float) (i >> 8 & 255) / 255.0F;
                    f2 = (float) (i & 255) / 255.0F;
                    pGuiGraphics.blit(this.leftPos + slot.x, this.topPos + slot.y, -900, sprite.contents().width(), sprite.contents().height(), sprite, f, f1, f2, 1);
                }
                if (slot.getItem().getCount() != 1) {
                    String s = Integer.toString(slot.getItem().getCount());
                    pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                    pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                }
                if(slot.getItem().isDamaged()){
                    pGuiGraphics.fill(this.leftPos + slot.x+2, this.topPos + slot.y+13,this.leftPos + slot.x + 15, this.topPos + slot.y + 15, 0xFF000000);
                    int color = slot.getItem().getBarColor();
                    int offset=0;
                    for(int n=0;n<8-Integer.toHexString(color).length();n++){
                        offset=offset/16;
                        offset+=0xF0000000;
                    }
                    color+=offset;
                    pGuiGraphics.fill(this.leftPos + slot.x+2, this.topPos + slot.y+13,this.leftPos + slot.x + 2 + slot.getItem().getBarWidth(), this.topPos + slot.y + 14, color);
                }
            } else {
                pGuiGraphics.renderItem(slot.getItem(), this.leftPos + slot.x, this.topPos + slot.y, 0, -1000);
                if (slot.getItem().getCount() > 1) {
                    String s = Integer.toString(slot.getItem().getCount());
                    pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                    pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                }
            }
             ((FriendSlot) slot).tempBypass = false;
        } pGuiGraphics.flush();

        //ACTUAL STUFF


    }

    void renderSkillMenu(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

        for (Slot slot : this.menu.slots) {
            ((FriendSlot) slot).tempBypass = true;
            if (((slot.getSlotIndex() > 6 || slot.getSlotIndex() == 0) && !(slot.container instanceof Inventory)) || (slot.getSlotIndex() > 8 && slot.container instanceof Inventory)) {
                List<BakedQuad> bakedmodel = this.getMinecraft().getItemRenderer().getModel(slot.getItem(), this.friend.level(), this.friend, 0).getQuads(null, null, this.friend.getRandom());
                if (!bakedmodel.isEmpty()) {
                    TextureAtlasSprite sprite = bakedmodel.get(0).getSprite();
                    for (BakedQuad quad : bakedmodel) {
                        int i = -1;
                        float f,f1,f2;
                        if (bakedmodel.get(0).isTinted()) {
                            sprite = quad.getSprite();
                            i = this.getMinecraft().getItemColors().getColor(slot.getItem(), quad.getTintIndex());
                        }
                        f = (float) (i >> 16 & 255) / 255.0F;
                        f1 = (float) (i >> 8 & 255) / 255.0F;
                        f2 = (float) (i & 255) / 255.0F;
                        pGuiGraphics.blit(this.leftPos + slot.x, this.topPos + slot.y, -900, sprite.contents().width(), sprite.contents().height(), sprite, f, f1, f2, 1);
                    }
                    if (slot.getItem().getCount() != 1) {
                        String s = Integer.toString(slot.getItem().getCount());
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                    }
                    if(slot.getItem().isDamaged()){
                        pGuiGraphics.fill(this.leftPos + slot.x+2, this.topPos + slot.y+13,this.leftPos + slot.x + 15, this.topPos + slot.y + 15, 0xFF000000);
                        int color = slot.getItem().getBarColor();
                        int offset=0;
                        for(int n=0;n<8-Integer.toHexString(color).length();n++){
                            offset=offset/16;
                            offset+=0xF0000000;
                        }
                        color+=offset;
                        pGuiGraphics.fill(this.leftPos + slot.x+2, this.topPos + slot.y+13,this.leftPos + slot.x + 2 + slot.getItem().getBarWidth(), this.topPos + slot.y + 14, color);
                    }
                } else {
                    pGuiGraphics.renderItem(slot.getItem(), this.leftPos + slot.x, this.topPos + slot.y, 0, -1000);
                    if (slot.getItem().getCount() > 1) {
                        String s = Integer.toString(slot.getItem().getCount());
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                    }
                }
            } ((FriendSlot) slot).tempBypass = false;
        } pGuiGraphics.flush();
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_BLEND);
        pGuiGraphics.pose().pushPose();

        pGuiGraphics.blit(EXPBAREMPTY, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(EXPBAR, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, 154 + (int) (0.97 * (this.friend.getFriendExperience() % 100)), this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(SKILLMENU, this.leftPos - 1, this.topPos - 1, 1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        //skill button setup
        //0~5 is first row
        //6~11 is second row
        //12~17 is third row
        RenderSystem.enableDepthTest();
        for (int i = 0; i < bt.size(); i++) {
            boolean[] enabled = this.friend.getSkillEnabled();
            int[] levels = this.friend.getSkillLevels();
            if (i == 0 || i == 1) {
                if (this.friend.getSkillPoints() > 0) {
                    bt.get(i).visible = true;
                }
                bt.get(i + 2).setFocus(enabled[i] || levels[i]==0);
                bt.get(i + 4).setFocus(!enabled[i]);
            } else if (i == 6 || i == 7) {
                if (this.friend.getSkillPoints() > 0) {
                    bt.get(i).visible = true;
                }
                bt.get(i + 2).setFocus(enabled[i - 4] || levels[i-4]==0);
                bt.get(i + 4).setFocus(!enabled[i - 4]);
            } else if (i == 12) {
                if (this.friend.getSkillPoints() > 2) {
                    bt.get(i).visible = true;
                }
                bt.get(i + 2).setFocus(enabled[i - 8] || levels[i-8]==0);
                bt.get(i + 4).setFocus(!enabled[i - 8]);
            } else if (i==13){
                if (this.friend.getSkillPoints() > 2) {
                    if(!this.friend.inventory.getItem(0).isEmpty()){
                    bt.get(i).visible = true;}
                }
                bt.get(i + 2).setFocus(enabled[i - 8] || levels[i-8]==0);
                bt.get(i + 4).setFocus(!enabled[i - 8]);
            }else {
                bt.get(i).visible = true;
            }
            bt.get(i).render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }


        //render level
        pGuiGraphics.drawCenteredString(this.font, Integer.toString((int) this.friend.getFriendExperience() / 100), this.leftPos + 144, this.topPos + 46, ChatFormatting.WHITE.getColor());
        pGuiGraphics.drawString(this.font, Component.translatable("juicecraft.menu.skillpoints").getString() + this.friend.getSkillPoints(),this.leftPos+157,this.topPos+52,ChatFormatting.WHITE.getColor());
        RenderSystem.disableDepthTest();


        pGuiGraphics.pose().popPose();
        GL11.glDisable(GL11.GL_BLEND);
        RenderSystem.enableDepthTest();
    }

    void renderStatsMenu(GuiGraphics pGuiGraphics) {
        pGuiGraphics.pose().pushPose();
        for (Slot slot : this.menu.slots) {
            ((FriendSlot) slot).tempBypass = true;
            if (((slot.getSlotIndex() > 6 || slot.getSlotIndex() == 0) && !(slot.container instanceof Inventory)) || (slot.getSlotIndex() > 8 && slot.container instanceof Inventory)) {
                List<BakedQuad> bakedmodel = this.getMinecraft().getItemRenderer().getModel(slot.getItem(), this.friend.level(), this.friend, 0).getQuads(null, null, this.friend.getRandom());
                if (!bakedmodel.isEmpty()) {
                    TextureAtlasSprite sprite = bakedmodel.get(0).getSprite();
                    for (BakedQuad quad : bakedmodel) {
                        int i = -1;
                        float f,f1,f2;
                        if (bakedmodel.get(0).isTinted()) {
                            sprite = quad.getSprite();
                            i = this.getMinecraft().getItemColors().getColor(slot.getItem(), quad.getTintIndex());
                        }
                        f = (float) (i >> 16 & 255) / 255.0F;
                        f1 = (float) (i >> 8 & 255) / 255.0F;
                        f2 = (float) (i & 255) / 255.0F;
                        pGuiGraphics.blit(this.leftPos + slot.x, this.topPos + slot.y, -900, sprite.contents().width(), sprite.contents().height(), sprite, f, f1, f2, 1);
                    }
                    if (slot.getItem().getCount() != 1) {
                        String s = Integer.toString(slot.getItem().getCount());
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                    }
                    if(slot.getItem().isDamaged()){
                        pGuiGraphics.fill(this.leftPos + slot.x+2, this.topPos + slot.y+13,this.leftPos + slot.x + 15, this.topPos + slot.y + 15, 0xFF000000);
                        int color = slot.getItem().getBarColor();
                        int offset=0;
                        for(int n=0;n<8-Integer.toHexString(color).length();n++){
                            offset=offset/16;
                            offset+=0xF0000000;
                        }
                        color+=offset;
                        pGuiGraphics.fill(this.leftPos + slot.x+2, this.topPos + slot.y+13,this.leftPos + slot.x + 2 + slot.getItem().getBarWidth(), this.topPos + slot.y + 14, color);
                    }
                } else {
                    pGuiGraphics.renderItem(slot.getItem(), this.leftPos + slot.x, this.topPos + slot.y, 0, -1000);
                    if (slot.getItem().getCount() > 1) {
                        String s = Integer.toString(slot.getItem().getCount());
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                    }
                }
            } ((FriendSlot) slot).tempBypass = false;
        }
        pGuiGraphics.pose().translate(0,0,500);
        pGuiGraphics.pose().popPose();
        pGuiGraphics.flush();
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_BLEND);
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.blit(STATMENU, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(FRIEND_PORTRAIT, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);


        pGuiGraphics.drawString(this.font, Component.literal(Component.translatable("juicecraft.menu.name").getString() + this.friend.getFriendName()), this.leftPos - 1 + 187, this.topPos - 1 + 50, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getResource("origin"), this.leftPos - 1 + 187, this.topPos - 1 + 67, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getResource("disposition"), this.leftPos - 1 + 187, this.topPos - 1 + 87, ChatFormatting.BLACK.getColor(), false);

        Component comp = Component.translatable("juicecraft.menu.specialties");
        pGuiGraphics.drawString(this.font, comp, this.leftPos - 1 + 124, this.topPos - 1 + 110, ChatFormatting.BLACK.getColor(), false);
        renderScrollingString(pGuiGraphics, this.font, this.getFriendResource("specialties"), this.leftPos - 1 + 124 + this.getMinecraft().font.width(comp), this.topPos - 1 + 110, this.leftPos - 1 + 260, this.topPos - 1 + 120, ChatFormatting.BLACK.getColor());

        comp = Component.translatable("juicecraft.menu.weaknesses");

        pGuiGraphics.drawString(this.font, comp, this.leftPos - 1 + 124, this.topPos - 1 + 120, ChatFormatting.BLACK.getColor(), false);
        renderScrollingString(pGuiGraphics, this.font, this.getFriendResource("weaknesses"), this.leftPos - 1 + 124 + this.getMinecraft().font.width(comp), this.topPos - 1 + 120, this.leftPos - 1 + 260, this.topPos - 1 + 130, ChatFormatting.BLACK.getColor());


        pGuiGraphics.drawString(this.font, this.getIntResource("level", (int) this.friend.getFriendExperience()/100), this.leftPos - 1 + 124, this.topPos - 1 + 135, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getFloatResource("health", this.friend.getHealth()), this.leftPos - 1 + 124, this.topPos - 1 + 150, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getFloatResource("hunger", this.friend.getHungerMeter()), this.leftPos - 1 + 195, this.topPos - 1 + 150, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getIntResource("itemscollected", this.friend.getFriendItemsCollected()), this.leftPos - 1 + 124, this.topPos - 1 + 165, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getIntResource("hostilekilled", this.friend.getFriendEnemiesKilled()), this.leftPos - 1 + 124, this.topPos - 1 + 180, ChatFormatting.BLACK.getColor(), false);

        pGuiGraphics.pose().popPose();
        GL11.glDisable(GL11.GL_BLEND);
        //RenderSystem.enableDepthTest();
    }

    String getResource(String s) {
        return Component.translatable("juicecraft.menu." + s).getString() + Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + "." + s).getString();
    }

    Component getFriendResource(String s) {
        return Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + "." + s);
    }

    String getFloatResource(String s, float val) {
        return Component.translatable("juicecraft.menu." + s).getString() + String.format("%.1f", val);
    }

    String getIntResource(String s, int val) {
        return Component.translatable("juicecraft.menu." + s).getString() + val;
    }

    @Override
    public void renderTransparentBackground(GuiGraphics pGuiGraphics) {
        pGuiGraphics.fillGradient(0, 0, this.width, this.height, -1000, -1072689136, -804253680);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTransparentBackground(pGuiGraphics);
        //fluids
        pGuiGraphics.blit(MAINFLUIDTEXTURE, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(HUNGERBAR, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, 7 + (int) (84 * this.friend.getHungerMeter() / 100), this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(HEALTHBAR, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, 7 + (int) (84 * this.friend.getHealth() / this.friend.getMaxHealth()), this.imageHeight, this.imageWidth, this.imageHeight);

        //norma level
        int x = this.friend.getFriendNorma();
        if (x == 0) {
            pGuiGraphics.blit(NORMA1, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        } else if (x == 1) {
            pGuiGraphics.blit(NORMA1, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        } else if (x == 2) {
            pGuiGraphics.blit(NORMA2, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        } else if (x == 3) {
            pGuiGraphics.blit(NORMA3, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        } else if (x == 4) {
            pGuiGraphics.blit(NORMA4, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        } else {
            pGuiGraphics.blit(NORMA5, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        }


        pGuiGraphics.blit(MAINTEXTURE, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        //render friend
        InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, this.leftPos + 13, this.topPos + 18, this.leftPos + 88, this.topPos + 170, 55, 0.20F, pMouseX, pMouseY, this.friend);

        //hide gear icons if there is gear there
        for (int i = 0; i < 4; i++) {
            if (!friend.inventory.getItem(i + 3).isEmpty()) { //armor slots
                pGuiGraphics.blit(CLEARSLOT, this.leftPos + 13 + 18 * i, this.topPos + 229, -1000, 0, 0, 18, 18, 18, 18);
            }
        }
        if (!friend.inventory.getItem(2).isEmpty()) { //module slot
            pGuiGraphics.blit(CLEARSLOT, this.leftPos + 67, this.topPos + 204, -1000, 0, 0, 18, 18, 18, 18);
        }
        if (!friend.inventory.getItem(1).isEmpty()) { //weapon slot
            pGuiGraphics.blit(CLEARSLOT, this.leftPos + 13, this.topPos + 204, -1000, 0, 0, 18, 18, 18, 18);
        }
        if (!friend.inventory.getItem(0).isEmpty()) { //hyper slot
            pGuiGraphics.blit(CLEARSLOT, this.leftPos + 135, this.topPos + 132, -1000, 0, 0, 18, 18, 18, 18);
        }

        //this part renders the locked slots on the inventory
        for (int n = 0; n < 7 - friend.getInventoryRows(); n++) {
            for (int i = 0; i < 5; i++) {
                pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 182 + 18 * i, this.topPos + 150 - 18 * n, -1000, 0, 0, 18, 18, 18, 18);
            }
        }
        if (!friend.isModular()) {
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 67, this.topPos + 204, -1000, 0, 0, 18, 18, 18, 18);
        }
        if (!friend.isArmorable()) {
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 13, this.topPos + 229, -1000, 0, 0, 18, 18, 18, 18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 31, this.topPos + 229, -1000, 0, 0, 18, 18, 18, 18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 49, this.topPos + 229, -1000, 0, 0, 18, 18, 18, 18);
            pGuiGraphics.blit(LOCKED_TEXTURE, this.leftPos + 67, this.topPos + 229, -1000, 0, 0, 18, 18, 18, 18);
        }
    }

    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        if (this.getMinecraft().level != null) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.BackgroundRendered(this, pGuiGraphics));
        } else {
            this.renderDirtBackground(pGuiGraphics);
        }
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if (isValidSpot(pMouseX, pMouseY)) {
            renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        }

        //additional menus
        if (this.skillActive) {
            this.renderSkillMenu(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        } else if (this.statsActive) {
            this.renderStatsMenu(pGuiGraphics);
        } else if(this.talkActive){
            this.renderTalkMenu(pGuiGraphics,pMouseX,pMouseY,pPartialTick);
        }
        if (!this.skillActive) {
            for (Button i : bt) {
                i.visible = false;
            }
        }
        if(!this.talkActive){
            for(Button i: talkBt){
                i.visible = false;
            }
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
        this.bagButton.setFocus(true);

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 173, this.topPos + 63, 11, 10, upgradeSprite, this::doSkillOneUpgrade, true)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 238, this.topPos + 63, 11, 10, upgradeSprite, this::doSkillTwoUpgrade, true)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 74, 27, 12, enableSprite, this::doSkillOneEnable)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 74, 27, 12, enableSprite, this::doSkillTwoEnable)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 87, 27, 12, disableSprite, this::doSkillOneDisable)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 87, 27, 12, disableSprite, this::doSkillTwoDisable)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 173, this.topPos + 111, 11, 10, upgradeSprite, this::doSkillThreeUpgrade, true)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 238, this.topPos + 111, 11, 10, upgradeSprite, this::doSkillFourUpgrade, true)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 122, 27, 12, enableSprite, this::doSkillThreeEnable)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 122, 27, 12, enableSprite, this::doSkillFourEnable)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 135, 27, 12, disableSprite, this::doSkillThreeDisable)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 135, 27, 12, disableSprite, this::doSkillFourDisable)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 173, this.topPos + 159, 11, 10, upgradeSprite, this::doSkillFiveUpgrade, true)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 238, this.topPos + 159, 11, 10, upgradeSprite, this::doSkillSixUpgrade, true)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 170, 27, 12, enableSprite, this::doSkillFiveEnable)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 170, 27, 12, enableSprite, this::doSkillSixEnable)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 183, 27, 12, disableSprite, this::doSkillFiveDisable)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 183, 27, 12, disableSprite, this::doSkillSixDisable)));

        this.dialogueOne=addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 183, 27, 12, disableSprite, this::handleDialogueOne));
        this.dialogueTwo=addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 183, 27, 12, disableSprite, this::handleDialogueTwo));
        this.dialogueThree=addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 183, 27, 12, disableSprite, this::handleDialogueThree));
        this.dialogueFour=addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 183, 27, 12, disableSprite, this::handleDialogueFour));
        this.exitDialogue=addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 183, 27, 12, disableSprite, this::exitTalkButton));
        this.talkBt.add(dialogueOne);
        this.talkBt.add(dialogueTwo);
        this.talkBt.add(dialogueThree);
        this.talkBt.add(dialogueFour);
        this.talkBt.add(exitDialogue);

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
            double d1 = Math.max((double) l * 0.5D, 3.0D);
            double d2 = Math.sin((Math.PI / 2D) * Math.cos((Math.PI * 2D) * d0 / d1)) / 2.0D + 0.5D;
            double d3 = Mth.lerp(d2, 0.0D, (double) l);
            pGuiGraphics.enableScissor(pMinX, pMinY, pMaxX, pMaxY);
            pGuiGraphics.drawString(pFont, pText, pMinX - (int) d3, j, pColor, false);
            pGuiGraphics.disableScissor();
        } else {
            pGuiGraphics.drawString(pFont, pText, pMinX, pMinY, pColor, false);
        }

    }
}



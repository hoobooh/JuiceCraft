package com.usagin.juicecraft.client.renderer;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.ai.awareness.CombatSettings;
import com.usagin.juicecraft.client.menu.FriendMenu;
import com.usagin.juicecraft.client.menu.FriendSlot;
import com.usagin.juicecraft.data.dialogue.AbstractDialogueManager;
import com.usagin.juicecraft.data.dialogue.DialogueEnums;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.network.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static com.usagin.juicecraft.client.menu.FriendMenuTextureLocations.*;

public class FriendMenuScreen extends AbstractContainerScreen<FriendMenu> {
    static final Style style = Style.EMPTY.withItalic(true);
    private static final Logger LOGGER = LogUtils.getLogger();
    public final ResourceLocation FRIEND_PORTRAIT;
    public final ResourceLocation FRIEND_THEME;
    public final ResourceLocation FRIEND_SOURCE;
    final Friend friend;
    final ResourceLocation SKILLS;
    final Tooltip SKILL1;
    final Tooltip SKILL2;
    final Tooltip SKILL3;
    final Tooltip SKILL4;
    final Tooltip SKILL5;
    final Tooltip SKILL6;
    private final FriendMenu menu;
    public boolean playedSound = false;
    public int answerstate;
    public int[] answerstatus = new int[]{0, 0, 0, 0}; //0 is neutral, -1 is bad, 1 is good
    public FriendButton skillButton;
    public FriendButton bagButton;
    public FriendButton statButton;
    public FriendButton talkButton;
    public FriendButton dialogueOne;
    public FriendButton dialogueTwo;
    public FriendButton dialogueThree;
    public FriendButton dialogueFour;
    public FriendButton exitDialogue;
    public int dialogueProgress = 0;
    long timer = 0;
    DialogueEnums currenttopic;
    ArrayList<FriendButton> bt = new ArrayList<>();
    ArrayList<FriendButton> talkBt = new ArrayList<>();
    boolean skillActive = false;
    boolean statsActive = false;
    boolean hidePartial = false;
    boolean talkActive = false;
    boolean hideFull = false;
    WidgetSprites buttonSprite = new WidgetSprites(BUTTON_BEFORE, BUTTON_AFTER);
    WidgetSprites upgradeSprite = new WidgetSprites(UPGRADE_BEFORE, UPGRADE_AFTER);
    WidgetSprites enableSprite = new WidgetSprites(ENABLE_BEFORE, ENABLE_AFTER);
    WidgetSprites disableSprite = new WidgetSprites(DISABLE_BEFORE, DISABLE_AFTER);
    WidgetSprites speechSprite = new WidgetSprites(SPEECH_BEFORE, SPEECH_AFTER);
    WidgetSprites exitSprite = new WidgetSprites(EXIT_BEFORE, EXIT_AFTER);
    WidgetSprites speechConnectorSprite = new WidgetSprites(SPEECH_CONNECTOR_BEFORE, SPEECH_CONNECTOR_AFTER);
    FriendScrollWidget scrollWidget;
    FriendButton itempickupButton;
    ResourceLocation friendtheme;

    public FriendMenuScreen(FriendMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 384;
        this.imageHeight = 262;
        this.friend = pMenu.getFriend();
        this.menu = pMenu;
        this.inventoryLabelX = 111;
        this.inventoryLabelY = 170;
        this.SKILLS = new ResourceLocation(JuiceCraft.MODID, "textures/gui/" + friend.getFriendName().toLowerCase() + "/skills.png");
        FRIEND_THEME = new ResourceLocation(JuiceCraft.MODID, "textures/gui/" + friend.getFriendName().toLowerCase() + "/" + friend.getFriendName().toLowerCase() + ".png");
        FRIEND_SOURCE = new ResourceLocation(JuiceCraft.MODID, "textures/gui/" + friend.getFriendName().toLowerCase() + "/" + friend.getFriendName().toLowerCase() + "_theme.png");
        FRIEND_PORTRAIT = new ResourceLocation(JuiceCraft.MODID, "textures/gui/" + friend.getFriendName().toLowerCase() + "/" + friend.getFriendName().toLowerCase() + "_portrait.png");
        this.SKILL1 = Tooltip.create(Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + ".skill1"));
        this.SKILL2 = Tooltip.create(Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + ".skill2"));
        this.SKILL3 = Tooltip.create(Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + ".skill3"));
        this.SKILL4 = Tooltip.create(Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + ".skill4"));
        this.SKILL5 = Tooltip.create(Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + ".skill5"));
        this.SKILL6 = Tooltip.create(Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + ".skill6"));
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
        this.playedSound = false;
        this.dialogueProgress = 0;
        this.talkActive = true;
        this.bagButton.active = false;
        this.skillButton.active = false;
        this.statButton.active = false;
        this.talkButton.active = false;
        this.currenttopic = AbstractDialogueManager.getRandomPrompt(this.friend);
        //logic
    }

    private void handleDialogueOne(Button btn) {
        this.playedSound = false;
        DialogueResultPacketHandler.sendToServer(new ToServerDialogueResultPacket(this.answerstatus[0] / 100, this.friend.getId()));
        this.answerstate = this.answerstatus[0];
        if (this.dialogueProgress == 1) {
            this.dialogueProgress++;
        } else if (this.dialogueProgress == 3) { //general topics
            this.currenttopic = DialogueEnums.GETCOMBATSETTINGS;
            this.dialogueProgress = 50;
        } else if (this.dialogueProgress == 61) { //change combat setting
            this.dialogueProgress = 63;
        } else if (this.dialogueProgress == 63) {//hyper
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.aggression * 1000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 64) { //aggression

            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 65) { //flee
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 66) { //defense
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + settings.willFlee * 100;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 51) {
            this.dialogueProgress = 3;
            this.currenttopic = DialogueEnums.GENERAL;
        }
        this.answerstatus = new int[]{0, 0, 0, 0};
    }

    private void handleDialogueTwo(Button btn) {
        this.playedSound = false;
        DialogueResultPacketHandler.sendToServer(new ToServerDialogueResultPacket(this.answerstatus[1] / 100, this.friend.getId()));
        this.answerstate = this.answerstatus[1];
        if (this.dialogueProgress == 1) {
            this.dialogueProgress++;
        } else if (this.dialogueProgress == 3) {
            this.currenttopic = DialogueEnums.COMBATSETTINGS;
            this.dialogueProgress = 60;
        } else if (this.dialogueProgress == 61) { //change combat setting
            this.dialogueProgress = 64;
        } else if (this.dialogueProgress == 63) {//hyper
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = 10000 + settings.aggression * 1000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 64) { //aggression
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + 1000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 65) { //flee
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 66) { //defense
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + settings.willFlee * 100 + 1;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        }
        this.answerstatus = new int[]{0, 0, 0, 0};
    }

    private void handleDialogueThree(Button btn) {
        this.playedSound = false;
        DialogueResultPacketHandler.sendToServer(new ToServerDialogueResultPacket(this.answerstatus[2] / 100, this.friend.getId()));
        this.answerstate = this.answerstatus[2];
        if (this.dialogueProgress == 3) {
            this.currenttopic = DialogueEnums.WANDERING;
            SetWanderingPacketHandler.sendToServer(new ToServerSetWanderingPacket(!this.friend.getIsWandering(), this.friend.getId()));
            this.dialogueProgress = 70;
        } else if (this.dialogueProgress == 63) {//hyper
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = 20000 + settings.aggression * 1000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 64) { //aggression
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + 2000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 65) { //flee
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + 200 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 66) { //defense
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + settings.willFlee * 100 + 2;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 61) { //change combat setting
            this.dialogueProgress = 65;
        }
        this.answerstatus = new int[]{0, 0, 0, 0};
    }

    private void handleDialogueFour(Button btn) {
        this.playedSound = false;
        DialogueResultPacketHandler.sendToServer(new ToServerDialogueResultPacket(this.answerstatus[3] / 100, this.friend.getId()));
        this.answerstate = this.answerstatus[3];
        if (this.dialogueProgress == 3) {
            this.currenttopic = DialogueEnums.FARMING;
            SetFarmingPacketHandler.sendToServer(new ToServerSetFarmingPacket(!this.friend.getIsFarming(), this.friend.getId()));
            this.dialogueProgress = 80;
        } else if (this.dialogueProgress == 63) {//hyper
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = 30000 + settings.aggression * 1000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 64) { //aggression
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + 3000 + settings.willFlee * 100 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 65) { //flee
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + 300 + settings.defense;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 66) { //defense
            CombatSettings settings = this.friend.getCombatSettings();
            int temp = settings.hyperCondition * 10000 + settings.aggression * 1000 + settings.willFlee * 100 + 3;
            CombatSettingsPacketHandler.sendToServer(new ToServerCombatSettingsPacket(temp, this.friend.getId()));
            this.dialogueProgress = 62;
        } else if (this.dialogueProgress == 61) { //change combat setting
            this.dialogueProgress = 66;
        }
        this.answerstatus = new int[]{0, 0, 0, 0};
    }

    private void exitTalkButton(Button btn) {
        this.playedSound = false;
        this.showFullScreen();
        if (this.statsActive || this.skillActive) {
            this.hideMiddleScreen();
        }
        this.exitDialogue.setFocus(false);
        this.talkActive = false;
        this.bagButton.active = true;
        this.skillButton.active = true;
        this.statButton.active = true;
        this.talkButton.active = true;
        this.talkButton.setFocus(false);
        this.currenttopic = null;
    }

    private void doSkillOneUpgrade(Button btn) {
        int[] levels = this.friend.getSkillLevels();
        levels[0] += 1;

        //LOGGER.info(Arrays.toString(levels) +" and " + Arrays.toString(this.friend.getSkillEnabled()));
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(levels, 1, this.friend.getSkillEnabled(), this.friend.getId()));
    }

    private void doSkillOneEnable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[0] = true;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillOneDisable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[0] = false;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillTwoUpgrade(Button btn) {
        int[] levels = this.friend.getSkillLevels();
        levels[1] += 1;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(levels, 1, this.friend.getSkillEnabled(), this.friend.getId()));
    }

    private void doSkillTwoEnable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[1] = true;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillTwoDisable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[1] = false;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillThreeUpgrade(Button btn) {
        int[] levels = this.friend.getSkillLevels();
        levels[2] += 1;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(levels, 2, this.friend.getSkillEnabled(), this.friend.getId()));
    }

    private void doSkillThreeEnable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[2] = true;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillThreeDisable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[2] = false;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillFourUpgrade(Button btn) {
        int[] levels = this.friend.getSkillLevels();
        levels[3] += 1;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(levels, 2, this.friend.getSkillEnabled(), this.friend.getId()));
    }

    private void doSkillFourEnable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[3] = true;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillFourDisable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[3] = false;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillFiveUpgrade(Button btn) {
        int[] levels = this.friend.getSkillLevels();
        levels[4] += 1;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(levels, 3, this.friend.getSkillEnabled(), this.friend.getId()));
    }

    private void doSkillFiveEnable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[4] = true;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillFiveDisable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[4] = false;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillSixUpgrade(Button btn) {
        int[] levels = this.friend.getSkillLevels();
        levels[5] += 1;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(levels, 4, this.friend.getSkillEnabled(), this.friend.getId()));
    }

    private void doSkillSixEnable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[5] = true;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    private void doSkillSixDisable(Button btn) {
        boolean[] enabled = this.friend.getSkillEnabled();
        enabled[5] = false;
        UpdateSkillPacketHandler.sendToServer(new ToServerUpdateSkillPacket(this.friend.getSkillLevels(), 0, enabled, this.friend.getId()));
    }

    String getResource(String s) {
        return Component.translatable("juicecraft.menu." + s).getString() + Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + "." + s).getString();
    }

    @Override
    protected void init() {
        super.init();

        //button renderer
        this.skillButton = addRenderableWidget(new FriendButton(this.leftPos + 119, this.topPos + 7, 36, 18, buttonSprite, this::handleSkillButton, Component.translatable("juicecraft.skills")));
        this.bagButton = addRenderableWidget(new FriendButton(this.leftPos + 173, this.topPos + 7, 36, 18, buttonSprite, this::handleGearButton, Component.translatable("juicecraft.gear")));
        this.statButton = addRenderableWidget(new FriendButton(this.leftPos + 227, this.topPos + 7, 36, 18, buttonSprite, this::handleStatsButton, Component.translatable("juicecraft.stats")));
        this.talkButton = addRenderableWidget(new FriendButton(this.leftPos + 335, this.topPos + 179, 36, 18, buttonSprite, this::handleTalkButton, Component.translatable("juicecraft.talk")));
        this.bagButton.setFocus(true);

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 173, this.topPos + 63, 11, 10, upgradeSprite, this::doSkillOneUpgrade, true, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 238, this.topPos + 63, 11, 10, upgradeSprite, this::doSkillTwoUpgrade, true, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 74, 27, 12, enableSprite, this::doSkillOneEnable, false, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 74, 27, 12, enableSprite, this::doSkillTwoEnable, false, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 87, 27, 12, disableSprite, this::doSkillOneDisable, false, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 87, 27, 12, disableSprite, this::doSkillTwoDisable, false, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 173, this.topPos + 111, 11, 10, upgradeSprite, this::doSkillThreeUpgrade, true, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 238, this.topPos + 111, 11, 10, upgradeSprite, this::doSkillFourUpgrade, true, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 122, 27, 12, enableSprite, this::doSkillThreeEnable, false, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 122, 27, 12, enableSprite, this::doSkillFourEnable, false, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 135, 27, 12, disableSprite, this::doSkillThreeDisable, false, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 135, 27, 12, disableSprite, this::doSkillFourDisable, false, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 173, this.topPos + 159, 11, 10, upgradeSprite, this::doSkillFiveUpgrade, true, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 238, this.topPos + 159, 11, 10, upgradeSprite, this::doSkillSixUpgrade, true, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 170, 27, 12, enableSprite, this::doSkillFiveEnable, false, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 170, 27, 12, enableSprite, this::doSkillSixEnable, false, false)));

        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 165, this.topPos + 183, 27, 12, disableSprite, this::doSkillFiveDisable, false, false)));
        bt.add(addRenderableWidget(new FriendButton(this.leftPos + 230, this.topPos + 183, 27, 12, disableSprite, this::doSkillSixDisable, false, false)));
        int xoffset = this.leftPos + this.imageWidth / 2 - 110;

        this.dialogueOne = addWidget(new FriendButton(xoffset, this.topPos + 133, 220, 15, speechSprite, speechConnectorSprite, this::handleDialogueOne, true, false, ChatFormatting.BLACK.getColor(), false));
        this.dialogueTwo = addWidget(new FriendButton(xoffset, this.topPos + 98, 220, 15, speechSprite, speechConnectorSprite, this::handleDialogueTwo, true, false, ChatFormatting.BLACK.getColor(), false));
        this.dialogueThree = addWidget(new FriendButton(xoffset, this.topPos + 63, 220, 15, speechSprite, speechConnectorSprite, this::handleDialogueThree, true, false, ChatFormatting.BLACK.getColor(), false));
        this.dialogueFour = addWidget(new FriendButton(xoffset, this.topPos + 28, 220, 15, speechSprite, speechConnectorSprite, this::handleDialogueFour, true, false, ChatFormatting.BLACK.getColor(), false));
        this.exitDialogue = addRenderableWidget(new FriendButton(this.leftPos + 375, this.topPos + 13, 17, 17, exitSprite, this::exitTalkButton, true, false));

        this.talkBt.add(dialogueOne);
        this.talkBt.add(dialogueTwo);
        this.talkBt.add(dialogueThree);
        this.talkBt.add(dialogueFour);
        this.talkBt.add(exitDialogue);
        this.scrollWidget = addRenderableWidget(new FriendScrollWidget(this.leftPos + 292, this.topPos + 30, 83, 143, Component.literal(this.friend.getEventLog()), this.font, this));

        this.itempickupButton = addRenderableWidget(new FriendButton(this.leftPos + 40, this.topPos + 204, 18, 18, new WidgetSprites(PICKUP_BEFORE, PICKUP_AFTER), this::doPickup, true, true));
        this.itempickupButton.setTooltip(Tooltip.create(Component.translatable("juicecraft.menu.itempickup" + this.friend.getFriendItemPickup())));
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if (isValidSpot(pMouseX, pMouseY)) {
            renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        }

        //additional menus

        if (this.talkActive) {
            for (Slot slot : this.menu.slots) {
                ((FriendSlot) slot).tempBypass = true;
                pGuiGraphics.renderItem(slot.getItem(), this.leftPos + slot.x, this.topPos + slot.y, 0, -1000);
                if (slot.getItem().getCount() > 1) {
                    String s = Integer.toString(slot.getItem().getCount());
                    pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                    pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                }
                ((FriendSlot) slot).tempBypass = false;
            }
        }
        if (this.skillActive || this.statsActive) {
            for (Slot slot : this.menu.slots) {
                ((FriendSlot) slot).tempBypass = true;
                if (((slot.getSlotIndex() > 6 || slot.getSlotIndex() == 0) && !(slot.container instanceof Inventory)) || (slot.getSlotIndex() > 8 && slot.container instanceof Inventory)) {

                    pGuiGraphics.renderItem(slot.getItem(), this.leftPos + slot.x, this.topPos + slot.y, 0, -1000);
                    if (slot.getItem().getCount() > 1) {
                        String s = Integer.toString(slot.getItem().getCount());
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 1 - this.font.width(s), this.topPos + slot.y + 6 + 4, ChatFormatting.BLACK.getColor(), false);
                        pGuiGraphics.drawString(this.font, s, this.leftPos + slot.x + 19 - 2 - this.font.width(s), this.topPos + slot.y + 6 + 3, ChatFormatting.WHITE.getColor(), false);
                    }
                }
                ((FriendSlot) slot).tempBypass = false;
            }
        }
        if (this.skillActive) {
            this.renderSkillMenu(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        } else if (this.statsActive) {
            this.renderStatsMenu(pGuiGraphics);
        }
        if (this.talkActive) {
            this.renderTalkMenu(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            for (FriendButton i : talkBt) {
                if (!i.visible) {
                    i.setFocus(false);
                }
            }
        }
        if (!this.skillActive) {
            for (Button i : bt) {
                i.visible = false;
            }
        }
        if (!this.talkActive) {
            for (Button i : talkBt) {
                i.visible = false;
            }
        }
    }

    void renderSkillMenu(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.flush();
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_BLEND);
        pGuiGraphics.pose().pushPose();

        pGuiGraphics.pose().translate(0, 0, 800);

        pGuiGraphics.blit(EXPBAREMPTY, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(EXPBAR, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, 154 + (int) (0.97 * (this.friend.getFriendExperience() % 100)), this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(SKILLMENU, this.leftPos - 1, this.topPos - 1, 1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(SKILLS, this.leftPos - 1, this.topPos - 1, 1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);


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
                    bt.get(i).visible = this.friend.getFriendNorma() >= this.friend.skillinfo[i];
                } else {
                    bt.get(i).visible = false;
                }
                bt.get(i + 2).setFocus(enabled[i] || levels[i] == 0);
                bt.get(i + 4).setFocus(!enabled[i]);
            } else if (i == 6 || i == 7) {
                if (this.friend.getSkillPoints() > 1) {
                    bt.get(i).visible = this.friend.getFriendNorma() >= this.friend.skillinfo[i - 4];
                } else {
                    bt.get(i).visible = false;
                }
                bt.get(i + 2).setFocus(enabled[i - 4] || levels[i - 4] == 0);
                bt.get(i + 4).setFocus(!enabled[i - 4]);
            } else if (i == 12) {
                if (this.friend.getSkillPoints() > 2) {
                    bt.get(i).visible = this.friend.getFriendNorma() >= this.friend.skillinfo[i - 8];
                } else {
                    bt.get(i).visible = false;
                }
                bt.get(i + 2).setFocus(enabled[i - 8] || levels[i - 8] == 0);
                bt.get(i + 4).setFocus(!enabled[i - 8]);
            } else if (i == 13) {
                if (this.friend.getSkillPoints() > 3) {
                    bt.get(i).visible = this.friend.getFriendNorma() >= this.friend.skillinfo[i - 8];
                } else {
                    bt.get(i).visible = false;
                }
                bt.get(i + 2).setFocus(enabled[i - 8] || levels[i - 8] == 0);
                bt.get(i + 4).setFocus(!enabled[i - 8]);
            } else {
                bt.get(i).visible = true;
            }
            bt.get(i).render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }


        //render level
        pGuiGraphics.drawCenteredString(this.font, Integer.toString((int) this.friend.getFriendExperience() / 100), this.leftPos + 144, this.topPos + 46, ChatFormatting.WHITE.getColor());
        pGuiGraphics.drawString(this.font, Component.translatable("juicecraft.menu.skillpoints").getString() + this.friend.getSkillPoints(), this.leftPos + 157, this.topPos + 52, ChatFormatting.WHITE.getColor());

        int[] levels = this.friend.getSkillLevels();


        //SKILL 1
        if (this.hoveringImage(pMouseX, pMouseY, this.leftPos + 146, this.topPos + 81)) {
            this.setTooltipForNextRenderPass(this.SKILL1.toCharSequence(this.getMinecraft()));

        }

        //SKILL 2
        if (this.hoveringImage(pMouseX, pMouseY, this.leftPos + 211, this.topPos + 81)) {
            this.setTooltipForNextRenderPass(this.SKILL2.toCharSequence(this.getMinecraft()));
        }

        //SKILL 3
        if (this.hoveringImage(pMouseX, pMouseY, this.leftPos + 146, this.topPos + 129)) {
            this.setTooltipForNextRenderPass(this.SKILL3.toCharSequence(this.getMinecraft()));
        }

        //SKILL 4
        if (this.hoveringImage(pMouseX, pMouseY, this.leftPos + 211, this.topPos + 129)) {
            this.setTooltipForNextRenderPass(this.SKILL4.toCharSequence(this.getMinecraft()));
        }
        //SKILL 5
        if (this.hoveringImage(pMouseX, pMouseY, this.leftPos + 146, this.topPos + 177)) {
            this.setTooltipForNextRenderPass(this.SKILL5.toCharSequence(this.getMinecraft()));
        }

        //SKILL 6
        if (this.hoveringImage(pMouseX, pMouseY, this.leftPos + 211, this.topPos + 177)) {
            this.setTooltipForNextRenderPass(this.SKILL6.toCharSequence(this.getMinecraft()));
        }

        pGuiGraphics.pose().translate(0, 0, -400);

        drawCenteredString(pGuiGraphics, this.font, Component.literal(Integer.toString(levels[0])), this.leftPos + 147, this.topPos + 93, ChatFormatting.BLACK.getColor());
        drawCenteredString(pGuiGraphics, this.font, Component.literal(Integer.toString(levels[1])), this.leftPos + 212, this.topPos + 93, ChatFormatting.BLACK.getColor());
        drawCenteredString(pGuiGraphics, this.font, Component.literal(Integer.toString(levels[2])), this.leftPos + 147, this.topPos + 141, ChatFormatting.BLACK.getColor());
        drawCenteredString(pGuiGraphics, this.font, Component.literal(Integer.toString(levels[3])), this.leftPos + 212, this.topPos + 141, ChatFormatting.BLACK.getColor());
        drawCenteredString(pGuiGraphics, this.font, Component.literal(Integer.toString(levels[4])), this.leftPos + 147, this.topPos + 189, ChatFormatting.BLACK.getColor());
        drawCenteredString(pGuiGraphics, this.font, Component.literal(Integer.toString(levels[5])), this.leftPos + 212, this.topPos + 189, ChatFormatting.BLACK.getColor());

        RenderSystem.disableDepthTest();


        pGuiGraphics.pose().popPose();
        GL11.glDisable(GL11.GL_BLEND);
        RenderSystem.enableDepthTest();
    }

    void renderStatsMenu(GuiGraphics pGuiGraphics) {
        pGuiGraphics.flush();
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_BLEND);
        pGuiGraphics.pose().pushPose();

        pGuiGraphics.pose().translate(0, 0, 800);
        pGuiGraphics.blit(STATMENU, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(FRIEND_PORTRAIT, this.leftPos - 1, this.topPos - 1, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        Component comp = Component.translatable("juicecraft.menu.name");

        pGuiGraphics.drawString(this.font, comp, this.leftPos - 1 + 187, this.topPos - 1 + 50, ChatFormatting.BLACK.getColor(), false);
        renderScrollingString(pGuiGraphics, this.font, Component.literal(this.friend.getFriendName()), this.leftPos - 1 + 187 + this.font.width(comp.getString()), this.topPos - 1 + 50, this.leftPos - 1 + 260, this.topPos - 1 + 60, ChatFormatting.BLACK.getColor());

        comp = Component.translatable("juicecraft.menu.origin");

        pGuiGraphics.drawString(this.font, comp, this.leftPos - 1 + 187, this.topPos - 1 + 67, ChatFormatting.BLACK.getColor(), false);
        renderScrollingString(pGuiGraphics, this.font, this.getFriendResource("origin"), this.leftPos - 1 + 187 + this.font.width(comp.getString()), this.topPos - 1 + 67, this.leftPos - 1 + 260, this.topPos - 1 + 87, ChatFormatting.BLACK.getColor());

        comp = Component.translatable("juicecraft.menu.disposition");

        pGuiGraphics.drawString(this.font, comp, this.leftPos - 1 + 187, this.topPos - 1 + 87, ChatFormatting.BLACK.getColor(), false);
        renderScrollingString(pGuiGraphics, this.font, this.getFriendResource("disposition"), this.leftPos - 1 + 187 + this.font.width(comp.getString()), this.topPos - 1 + 87, this.leftPos - 1 + 260, this.topPos - 1 + 97, ChatFormatting.BLACK.getColor());

        comp = Component.translatable("juicecraft.menu.specialties");
        pGuiGraphics.drawString(this.font, comp, this.leftPos - 1 + 124, this.topPos - 1 + 110, ChatFormatting.BLACK.getColor(), false);
        renderScrollingString(pGuiGraphics, this.font, this.getFriendResource("specialties"), this.leftPos - 1 + 124 + this.getMinecraft().font.width(comp), this.topPos - 1 + 110, this.leftPos - 1 + 260, this.topPos - 1 + 120, ChatFormatting.BLACK.getColor());

        comp = Component.translatable("juicecraft.menu.weaknesses");

        pGuiGraphics.drawString(this.font, comp, this.leftPos - 1 + 124, this.topPos - 1 + 120, ChatFormatting.BLACK.getColor(), false);
        renderScrollingString(pGuiGraphics, this.font, this.getFriendResource("weaknesses"), this.leftPos - 1 + 124 + this.getMinecraft().font.width(comp), this.topPos - 1 + 120, this.leftPos - 1 + 260, this.topPos - 1 + 130, ChatFormatting.BLACK.getColor());


        pGuiGraphics.drawString(this.font, this.getIntResource("level", (int) this.friend.getFriendExperience() / 100), this.leftPos - 1 + 124, this.topPos - 1 + 135, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getFloatResource("health", this.friend.getHealth()), this.leftPos - 1 + 124, this.topPos - 1 + 150, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getFloatResource("hunger", this.friend.getHungerMeter()), this.leftPos - 1 + 195, this.topPos - 1 + 150, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getIntResource("itemscollected", this.friend.getFriendItemsCollected()), this.leftPos - 1 + 124, this.topPos - 1 + 165, ChatFormatting.BLACK.getColor(), false);
        pGuiGraphics.drawString(this.font, this.getIntResource("hostilekilled", this.friend.getFriendEnemiesKilled()), this.leftPos - 1 + 124, this.topPos - 1 + 180, ChatFormatting.BLACK.getColor(), false);

        pGuiGraphics.pose().popPose();
        GL11.glDisable(GL11.GL_BLEND);
        RenderSystem.enableDepthTest();
    }

    void renderTalkMenu(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.flush();
        RenderSystem.disableDepthTest();

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0, 0, 2000);
        //GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
        GL11.glEnable(GL11.GL_BLEND);
        //GL11.glDisable(GL11.GL_ALPHA_TEST);

        //dialogue codes:
        //0: prompt
        //1: player response
        //2: friend reaction
        //3: setting changes
        //50: get combat settings friend reaction
        //51: player confirm get combat settings -> j to 3
        //60: change combat settings friend reaction
        //61: select setting to change
        //63: hyper menu
        //64: aggression menu
        //65: flee menu
        //66: defense menu
        //62: friend confirms any changes -> j to 3
        //70: friend react to wander order -> j to 3
        //72: friend reacts to actual order  -> j to 3
        //80: friend react to farming order -> j to 3

        //ACTUAL STUFF
        pGuiGraphics.fillGradient(0, 0, this.width, this.height, -500, -1072689136, -804253680); //render background dim
        pGuiGraphics.pose().pushPose();
        float scale2 = 1.3F;
        String message = this.friend.getDialogueManager().sendToManage(this, pGuiGraphics, this.dialogueProgress, this.currenttopic);
        pGuiGraphics.pose().scale(scale2, scale2, 1);
        pGuiGraphics.drawWordWrap(this.font, FormattedText.of(message, style), (int) ((this.leftPos + 20) / scale2), (int) ((this.topPos + 200) / scale2), (int) (350 / scale2), ChatFormatting.DARK_GRAY.getColor());
        pGuiGraphics.pose().popPose();
        pGuiGraphics.blit(DIALOGUEBOX, this.leftPos - 1, this.topPos - 1, -500, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        //render buttons
        this.exitDialogue.visible = true;
        if (this.dialogueProgress == 1) {
            this.dialogueOne.visible = true;
            this.dialogueTwo.visible = true;
            this.dialogueThree.visible = false;
            this.dialogueFour.visible = false;
        } else if (this.dialogueProgress == 3) {
            this.dialogueOne.visible = true;
            this.dialogueTwo.visible = true;
            this.dialogueThree.visible = true;
            this.dialogueFour.visible = true;
        } else if (this.dialogueProgress == 51) {
            this.dialogueOne.visible = true;
            this.dialogueTwo.visible = false;
            this.dialogueThree.visible = false;
            this.dialogueFour.visible = false;
        } else if (this.dialogueProgress == 61 || this.dialogueProgress == 63 || this.dialogueProgress == 64 || this.dialogueProgress == 65 || this.dialogueProgress == 66) {
            this.dialogueOne.visible = true;
            this.dialogueTwo.visible = true;
            this.dialogueThree.visible = true;
            this.dialogueFour.visible = true;
        } else {
            this.dialogueOne.visible = false;
            this.dialogueTwo.visible = false;
            this.dialogueThree.visible = false;
            this.dialogueFour.visible = false;
        }

        if (!this.dialogueOne.visible) {
            int time = (int) (this.timer % 30);
            if (time < 9 || time > 21) {
                pGuiGraphics.blit(DIALOGUECLICK1, this.leftPos - 1, this.topPos - 10, -500, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
            } else if (time < 12 || time > 18) {
                pGuiGraphics.blit(DIALOGUECLICK2, this.leftPos - 1, this.topPos - 10, -500, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
            } else if (time < 14 || time > 16) {
                pGuiGraphics.blit(DIALOGUECLICK3, this.leftPos - 1, this.topPos - 10, -500, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
            } else {
                pGuiGraphics.blit(DIALOGUECLICK4, this.leftPos - 1, this.topPos - 10, -500, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
            }
        }

        for (Button b : talkBt) {
            if (b.visible) {
                b.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            }
        }

        float scale = 1.8F;
        pGuiGraphics.pose().scale(scale, scale, 1);
        drawCenteredString(pGuiGraphics, this.font, Component.translatable("entity.juicecraft." + this.friend.getFriendName().toLowerCase()), (int) ((this.leftPos + 72) / scale) + 1, (int) ((this.topPos + 169) / scale) + 3, 16765067);
        drawCenteredString(pGuiGraphics, this.font, Component.translatable("entity.juicecraft." + this.friend.getFriendName().toLowerCase()), (int) ((this.leftPos + 72) / scale) - 1, (int) ((this.topPos + 169) / scale) + 3, 16765067);
        drawCenteredString(pGuiGraphics, this.font, Component.translatable("entity.juicecraft." + this.friend.getFriendName().toLowerCase()), (int) ((this.leftPos + 72) / scale), (int) ((this.topPos + 169) / scale) + 4, 16765067);
        drawCenteredString(pGuiGraphics, this.font, Component.translatable("entity.juicecraft." + this.friend.getFriendName().toLowerCase()), (int) ((this.leftPos + 72) / scale), (int) ((this.topPos + 169) / scale) + 2, 16765067);
        drawCenteredString(pGuiGraphics, this.font, Component.translatable("entity.juicecraft." + this.friend.getFriendName().toLowerCase()), (int) ((this.leftPos + 72) / scale), (int) ((this.topPos + 169) / scale) + 3, ChatFormatting.WHITE.getColor());


        //epilogue

        pGuiGraphics.pose().popPose();
        GL11.glDisable(GL11.GL_BLEND);
        RenderSystem.enableDepthTest();
    }

    boolean hoveringImage(int x, int y, int centerx, int centery) {
        if (x < centerx + 11 && x > centerx - 11) {
            return y < centery + 8 && y > centery - 8;
        }
        return false;
    }

    public static void drawCenteredString(GuiGraphics pGuiGraphics, Font pFont, Component pText, int pX, int pY, int pColor) {
        FormattedCharSequence formattedcharsequence = pText.getVisualOrderText();
        pGuiGraphics.drawString(pFont, formattedcharsequence, pX - pFont.width(formattedcharsequence) / 2, pY, pColor, false);
    }

    protected static void renderScrollingString(GuiGraphics pGuiGraphics, Font pFont, Component pText, int pMinX, int pMinY, int pMaxX, int pMaxY, int pColor) {
        renderScrollingString(pGuiGraphics, pFont, pText, (pMinX + pMaxX) / 2, pMinX, pMinY, pMaxX, pMaxY, pColor);
    }

    Component getFriendResource(String s) {
        return Component.translatable("juicecraft.menu." + this.friend.getFriendName().toLowerCase() + "." + s);
    }

    String getIntResource(String s, int val) {
        return Component.translatable("juicecraft.menu." + s).getString() + val;
    }

    String getFloatResource(String s, float val) {
        return Component.translatable("juicecraft.menu." + s).getString() + String.format("%.1f", val);
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
            double d3 = Mth.lerp(d2, 0.0D, l);
            pGuiGraphics.enableScissor(pMinX, pMinY, pMaxX, pMaxY);
            pGuiGraphics.drawString(pFont, pText, pMinX - (int) d3, j, pColor, false);
            pGuiGraphics.disableScissor();
        } else {
            pGuiGraphics.drawString(pFont, pText, pMinX, pMinY, pColor, false);
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
    public void renderTransparentBackground(GuiGraphics pGuiGraphics) {
        pGuiGraphics.fillGradient(0, 0, this.width, this.height, -1000, -1072689136, -804253680);
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics pGuiGraphics, int pX1, int pY1, int pX2, int pY2, int pScale, float pYOffset, float pMouseX, float pMouseY, LivingEntity pEntity) {
        float f = (float) (pX1 + pX2) / 2.0F;
        float f1 = (float) (pY1 + pY2) / 2.0F;
        pGuiGraphics.enableScissor(pX1, pY1, pX2, pY2);
        float f2 = (float) Math.atan((f - pMouseX) / 40.0F);
        float f3 = (float) Math.atan((f1 - pMouseY) / 40.0F);
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float) Math.PI);
        Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f3 * 20.0F * ((float) Math.PI / 180F));
        quaternionf.mul(quaternionf1);
        float f4 = pEntity.yBodyRot;
        float f5 = pEntity.getYRot();
        float f6 = pEntity.getXRot();
        float f7 = pEntity.yHeadRotO;
        float f8 = pEntity.yHeadRot;
        pEntity.yBodyRot = 180.0F + f2 * 20.0F;
        pEntity.setYRot(180.0F + f2 * 40.0F);
        pEntity.setXRot(-f3 * 20.0F);
        pEntity.yHeadRot = pEntity.getYRot();
        pEntity.yHeadRotO = pEntity.getYRot();
        Vector3f vector3f = new Vector3f(0.0F, pEntity.getBbHeight() / 2.0F + pYOffset, 0.0F);
        renderEntityInInventory(pGuiGraphics, f, f1, pScale, vector3f, quaternionf, quaternionf1, pEntity);
        pEntity.yBodyRot = f4;
        pEntity.setYRot(f5);
        pEntity.setXRot(f6);
        pEntity.yHeadRotO = f7;
        pEntity.yHeadRot = f8;
        pGuiGraphics.disableScissor();
    }

    public static void renderEntityInInventory(GuiGraphics pGuiGraphics, float pX, float pY, int pScale, Vector3f pTranslate, Quaternionf pPose, @Nullable Quaternionf pCameraOrientation, LivingEntity pEntity) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(pX, pY, -600.0D);
        pGuiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling((float) pScale, (float) pScale, (float) (-pScale)));
        pGuiGraphics.pose().translate(pTranslate.x, pTranslate.y, pTranslate.z);
        pGuiGraphics.pose().mulPose(pPose);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (pCameraOrientation != null) {
            pCameraOrientation.conjugate();
            entityrenderdispatcher.overrideCameraOrientation(pCameraOrientation);
        }

        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(pEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, pGuiGraphics.pose(), pGuiGraphics.bufferSource(), 15728880);
        });
        pGuiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        pGuiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderTransparentBackground(pGuiGraphics);
        //fluids
        pGuiGraphics.blit(MAINFLUIDTEXTURE, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(HUNGERBAR, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, 7 + (84 * this.friend.getHungerMeter() / 100), this.imageHeight, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(HEALTHBAR, this.leftPos - 1, this.topPos - 1, -1000, 0, 0, 7 + (int) (84 * this.friend.getHealth() / this.friend.getMaxHealth()), this.imageHeight, this.imageWidth, this.imageHeight);

        //norma level
        int x = (int) this.friend.getFriendNorma();
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
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0, 0, 0);
        renderEntityInInventoryFollowsMouse(pGuiGraphics, this.leftPos + 7, this.topPos + 30, this.leftPos + 90, this.topPos + 173, 55, 0.20F, pMouseX, pMouseY, this.friend);
        pGuiGraphics.pose().popPose();

        pGuiGraphics.blit(FRIEND_THEME, this.leftPos - 19, this.topPos - 20, -1000, 0, 0, 420, 300,420, 300);

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

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.talkActive) {
            if (this.dialogueProgress == 2) {
                this.dialogueProgress = 3;
                this.currenttopic = DialogueEnums.GENERAL;
                this.playDownSound(this.getMinecraft().getSoundManager());
            } else if (this.dialogueProgress == 62) {
                this.dialogueProgress = 3;
                this.currenttopic = DialogueEnums.GENERAL;
                this.playDownSound(this.getMinecraft().getSoundManager());
            } else if (this.dialogueProgress == 70) {
                this.dialogueProgress = 3;
                this.currenttopic = DialogueEnums.GENERAL;
                this.playDownSound(this.getMinecraft().getSoundManager());
            } else if (this.dialogueProgress == 80) {
                this.dialogueProgress = 3;
                this.currenttopic = DialogueEnums.GENERAL;
                this.playDownSound(this.getMinecraft().getSoundManager());
            } else if (!this.dialogueOne.visible) { //prompt player response
                this.dialogueProgress++;
                this.playDownSound(this.getMinecraft().getSoundManager());
            }
        }
        if (isValidSpot(pMouseX, pMouseY)) {//findValidSlot determines that the click spot is not on a disabled slot
            return super.mouseClicked(pMouseX, pMouseY, pButton);
        } else { //it is on a disabled slot, ignore it
            return true;
        }
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pMouseX -= i;
        pMouseY -= j;
        return pMouseX >= (double) (pX - 1) && pMouseX < (double) (pX + pWidth + 1) && pMouseY >= (double) (pY - 1) && pMouseY < (double) (pY + pHeight + 1);
    }

    protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
        FriendSlot sl = (FriendSlot) pSlot;
        if (sl == null) {
            this.minecraft.gameMode.handleInventoryMouseClick(this.menu.containerId, pSlotId, pMouseButton, pType, this.minecraft.player);
        } else if (sl.highlight) {
            if (pSlot != null) {
                pSlotId = pSlot.index;
            }

            this.minecraft.gameMode.handleInventoryMouseClick(this.menu.containerId, pSlotId, pMouseButton, pType, this.minecraft.player);
        }
    }

    protected void containerTick() {
        this.timer++;
    }

    public void playDownSound(SoundManager pHandler) {
        pHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    private boolean isValidSpot(double pMouseX, double pMouseY) {
        return true;
    }

    private void doPickup(Button button) {
        int pickup = this.friend.getFriendItemPickup();
        if (pickup == 0) { //always
            this.itempickupButton.setTooltip(Tooltip.create(Component.translatable("juicecraft.menu.itempickup1")));
            ItemPickupPacketHandler.sendToServer(new ToServerItemPickupPacket(1, this.friend.getId()));
        } else if (pickup == 1) { //sometimes
            this.itempickupButton.setTooltip(Tooltip.create(Component.translatable("juicecraft.menu.itempickup2")));
            ItemPickupPacketHandler.sendToServer(new ToServerItemPickupPacket(2, this.friend.getId()));
        } else { //never
            this.itempickupButton.setTooltip(Tooltip.create(Component.translatable("juicecraft.menu.itempickup0")));
            ItemPickupPacketHandler.sendToServer(new ToServerItemPickupPacket(0, this.friend.getId()));
        }
    }
}



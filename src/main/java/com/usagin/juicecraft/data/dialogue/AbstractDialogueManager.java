package com.usagin.juicecraft.data.dialogue;

import com.usagin.juicecraft.ai.awareness.CombatSettings;
import com.usagin.juicecraft.ai.awareness.EnemyEvaluator;
import com.usagin.juicecraft.client.renderer.FriendMenuScreen;
import com.usagin.juicecraft.friends.Friend;
import com.usagin.juicecraft.network.PlaySoundPacketHandler;
import com.usagin.juicecraft.network.SpecialDialoguePacketHandler;
import com.usagin.juicecraft.network.ToServerPlaySoundPacket;
import com.usagin.juicecraft.network.ToServerSpecialDialogueUpdatePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public abstract class AbstractDialogueManager {
    public static DialogueEnums getRandomPrompt(Friend friend) {
        int[] specials = friend.getSpecialDialogueEnabled();
        ArrayList<DialogueEnums> list = new ArrayList<>();
        if (specials[0] == 1) {
            list.add(DialogueEnums.SPECIAL1);
            list.add(DialogueEnums.SPECIAL1);
            list.add(DialogueEnums.SPECIAL1);
            list.add(DialogueEnums.SPECIAL1);
            list.add(DialogueEnums.SPECIAL1);

        }
        if (specials[1] == 1) {
            list.add(DialogueEnums.SPECIAL2);
            list.add(DialogueEnums.SPECIAL2);
            list.add(DialogueEnums.SPECIAL2);
            list.add(DialogueEnums.SPECIAL2);
            list.add(DialogueEnums.SPECIAL2);
        }
        if (specials[2] == 1) {
            list.add(DialogueEnums.SPECIAL3);
            list.add(DialogueEnums.SPECIAL3);
            list.add(DialogueEnums.SPECIAL3);
            list.add(DialogueEnums.SPECIAL3);
            list.add(DialogueEnums.SPECIAL3);

        }
        list.add(DialogueEnums.NORMAL1);
        list.add(DialogueEnums.NORMAL2);
        list.add(DialogueEnums.NORMAL3);
        list.add(DialogueEnums.COMMENT_HEALTH);
        list.add(DialogueEnums.COMMENT_HEALTH);
        list.add(DialogueEnums.COMMENT_HEALTH);
        list.add(DialogueEnums.COMMENT_HEALTH);
        list.add(DialogueEnums.COMMENT_HEALTH);
        list.add(DialogueEnums.COMMENT_AREA_DANGER);
        list.add(DialogueEnums.COMMENT_AREA_DANGER);
        list.add(DialogueEnums.COMMENT_AREA_DANGER);
        list.add(DialogueEnums.COMMENT_AREA_DANGER);
        list.add(DialogueEnums.COMMENT_AREA_DANGER);
        if (friend.getHungerMeter() < 40) {
            list.add(DialogueEnums.COMMENT_HUNGER);
            list.add(DialogueEnums.COMMENT_HUNGER);
            list.add(DialogueEnums.COMMENT_HUNGER);
            list.add(DialogueEnums.COMMENT_HUNGER);
            list.add(DialogueEnums.COMMENT_HUNGER);
        }
        DialogueEnums choiceenum = list.get(friend.getRandom().nextInt(list.size()));

        //need to synchronize with packets. update tomorrow

        if (choiceenum == DialogueEnums.SPECIAL1) {
            specials[0] += 1;
        } else if (choiceenum == DialogueEnums.SPECIAL2) {
            specials[1] += 1;
        } else if (choiceenum == DialogueEnums.SPECIAL3) {
            specials[2] += 1;
        }
        SpecialDialoguePacketHandler.sendToServer(new ToServerSpecialDialogueUpdatePacket(encodeSpecialHash(specials), friend.getId()));
        return choiceenum;
    }

    public static int encodeSpecialHash(int[] n) {
        int temp = 0;
        temp += n[0];
        temp *= 10;
        temp += n[1];
        temp *= 10;
        temp += n[2];
        return temp;
    }

    public static int[] decodeSpecialHash(int n) {
        String temp = String.valueOf(n);
        int i = 3 - temp.length();
        for (int x = 0; x < i; x++) {
            temp = "0" + temp;
        }
        return new int[]{temp.charAt(2) - '0', temp.charAt(1) - '0', temp.charAt(0) - '0'};
    }

    public String sendToManage(FriendMenuScreen screen, GuiGraphics pGuiGraphics, int progress, DialogueEnums enums) {
        if (enums == DialogueEnums.NORMAL1) {
            return manageNormal1(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.NORMAL2) {
            return manageNormal2(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.NORMAL3) {
            return manageNormal3(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.SPECIAL1) {
            return manageSpecial1(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.SPECIAL2) {
            return manageSpecial2(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.SPECIAL3) {
            return manageSpecial3(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.COMMENT_HEALTH) {
            return manageCommentHealth(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.COMMENT_HUNGER) {
            return manageCommentHunger(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.COMMENT_AREA_DANGER) {
            return manageCommentDanger(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.GENERAL) {
            return manageGeneralPage(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.GETCOMBATSETTINGS) {
            return manageGetCombatSettingsPage(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.COMBATSETTINGS) {
            return manageSetCombatSettingsPage(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else if (enums == DialogueEnums.WANDERING) {
            return manageWandering(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        } else {
            return manageFarming(screen, pGuiGraphics, screen.getMenu().getFriend(), progress);
        }
    }

    String manageNormal1(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.normal1_response1"));
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.normal1_response2"));
        screen.answerstatus[0] = 1;
        screen.answerstatus[1] = -1;
        if (progress == 0 || progress == 1) {
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.normal1").getString();
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.approval").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageNormal2(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.normal2_response2"));
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.normal2_response1"));
        screen.answerstatus[0] = -1;
        screen.answerstatus[1] = 1;
        if (progress == 0 || progress == 1) {
            pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.normal2").getString();
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.approval").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageNormal3(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.normal3_response1"));
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.normal3_response2"));
        screen.answerstatus[0] = 1;
        screen.answerstatus[1] = -1;
        if (progress == 0 || progress == 1) {
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.normal3").getString();
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.approval").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageSpecial1(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.special1_response1"));
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.special1_response2"));
        screen.answerstatus[0] = -1;
        screen.answerstatus[1] = 1;
        if (progress == 0 || progress == 1) {
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.special1").getString();
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.approval").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageSpecial2(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.special2_response1"));
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.special2_response2"));
        screen.answerstatus[0] = 1;
        screen.answerstatus[1] = 1;
        if (progress == 0 || progress == 1) {
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.special2").getString();
        } else {
            pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.approval").getString();
        }
    }

    String manageSpecial3(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.special3_response1"));
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".responses.special3_response2"));
        screen.answerstatus[0] = -1;
        screen.answerstatus[1] = 1;
        if (progress == 0 || progress == 1) {
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.special3").getString();
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.approval").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageCommentHealth(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.comment_health_response1"));
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.comment_health_response2"));
        if (progress == 0 || progress == 1) {
            if (friend.getHealth() == friend.getMaxHealth()) {
                screen.answerstatus[0] = 1;
                screen.answerstatus[1] = 0;
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.comment_health_high").getString();
            } else if (friend.getHealth() > friend.getMaxHealth() / 2) {
                screen.answerstatus[0] = 0;
                screen.answerstatus[1] = 0;
                pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.comment_health_medium").getString();
            } else {
                screen.answerstatus[0] = -1;
                screen.answerstatus[1] = 1;
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.comment_health_low").getString();
            }
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.generic_accept").getString();
            } else if (screen.answerstate == 0) {
                pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.neutral").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageCommentHunger(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.comment_hunger_response1"));
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.comment_hunger_response2"));
        if (progress == 0 || progress == 1) {
            screen.answerstatus[0] = 1;
            screen.answerstatus[1] = -1;
            pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.comment_hunger_low").getString();
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.approval").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageCommentDanger(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.comment_area_danger_response1"));
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.comment_area_danger_response2"));
        if (progress == 0 || progress == 1) {
            if (EnemyEvaluator.evaluateAreaDanger(friend) > 500) {
                screen.answerstatus[0] = -1;
                screen.answerstatus[1] = 1;
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.comment_area_danger_high").getString();
            } else if (EnemyEvaluator.evaluateAreaDanger(friend) > 50) {
                screen.answerstatus[0] = 0;
                screen.answerstatus[1] = 0;
                pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.comment_area_danger_medium").getString();
            } else {
                screen.answerstatus[0] = 1;
                screen.answerstatus[1] = 0;
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".prompts.comment_area_danger_low").getString();
            }
        } else {
            if (screen.answerstate == 1) {
                pGuiGraphics.blit(getPortraitHappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.generic_accept").getString();
            } else if (screen.answerstate == 0) {
                pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.neutral").getString();
            } else {
                pGuiGraphics.blit(getPortraitUnhappy(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
                return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.disapproval").getString();
            }
        }
    }

    String manageGeneralPage(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.getcombatsettings"));
        screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.setcombatsettings"));
        if (friend.getIsWandering()) {
            screen.dialogueThree.setMessage(Component.translatable("juicecraft.menu.responses.stopwandering"));
        } else {
            screen.dialogueThree.setMessage(Component.translatable("juicecraft.menu.responses.startwandering"));
        }
        if (friend.getIsFarming()) {
            screen.dialogueFour.setMessage(Component.translatable("juicecraft.menu.responses.stopfarming"));
        } else {
            screen.dialogueFour.setMessage(Component.translatable("juicecraft.menu.responses.startfarming"));
        }
        screen.answerstatus = new int[]{0, 0, 0, 0};
        pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
        return "...";
    }

    String manageGetCombatSettingsPage(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        String temp = "";
        screen.answerstatus = new int[]{0, 0, 0, 0};
        CombatSettings settings = friend.getCombatSettings();
        temp += Component.translatable("juicecraft.menu.getsettings.hyper." + settings.hyperCondition).getString();
        temp += Component.translatable("juicecraft.menu.getsettings.aggression." + settings.aggression).getString();
        temp += Component.translatable("juicecraft.menu.getsettings.flee." + settings.willFlee).getString();
        temp += Component.translatable("juicecraft.menu.getsettings.defense." + settings.defense).getString();
        pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
        screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.generic_response"));
        return temp;
    }


    String manageSetCombatSettingsPage(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.answerstatus = new int[]{0, 0, 0, 0};
        if (progress == 61) { //init settings menu
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.hyper"));
            screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.aggression"));
            screen.dialogueThree.setMessage(Component.translatable("juicecraft.menu.responses.flee"));
            screen.dialogueFour.setMessage(Component.translatable("juicecraft.menu.responses.defense"));
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.combatsettings").getString();
        } else if (progress == 63) { //hyper
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.hyper.0"));
            screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.hyper.1"));
            screen.dialogueThree.setMessage(Component.translatable("juicecraft.menu.responses.hyper.2"));
            screen.dialogueFour.setMessage(Component.translatable("juicecraft.menu.responses.hyper.3"));
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.combatsettings").getString();
        } else if (progress == 64) { //aggression
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.aggression.0"));
            screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.aggression.1"));
            screen.dialogueThree.setMessage(Component.translatable("juicecraft.menu.responses.aggression.2"));
            screen.dialogueFour.setMessage(Component.translatable("juicecraft.menu.responses.aggression.3"));
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.combatsettings").getString();
        } else if (progress == 65) { //flee
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.flee.0"));
            screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.flee.1"));
            screen.dialogueThree.setMessage(Component.translatable("juicecraft.menu.responses.flee.2"));
            screen.dialogueFour.setMessage(Component.translatable("juicecraft.menu.responses.flee.3"));

            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.combatsettings").getString();
        } else if (progress == 66) { //defense
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            screen.dialogueOne.setMessage(Component.translatable("juicecraft.menu.responses.defense.0"));
            screen.dialogueTwo.setMessage(Component.translatable("juicecraft.menu.responses.defense.1"));
            screen.dialogueThree.setMessage(Component.translatable("juicecraft.menu.responses.defense.2"));
            screen.dialogueFour.setMessage(Component.translatable("juicecraft.menu.responses.defense.3"));
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.combatsettings").getString();
        } else { //response
            pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
            return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.generic_accept").getString();
        }
    }


    String manageWandering(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.answerstatus = new int[]{0, 0, 0, 0};
        pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
        return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.generic_accept").getString();
    }


    String manageFarming(FriendMenuScreen screen, GuiGraphics pGuiGraphics, Friend friend, int progress) {
        screen.answerstatus = new int[]{0, 0, 0, 0};
        pGuiGraphics.blit(getPortraitNeutral(friend.getFriendName().toLowerCase(), friend, screen), screen.getGuiLeft() - 1, screen.getGuiTop() - 1, -500, 0, 0, screen.getXSize(), screen.getYSize(), screen.getXSize(), screen.getYSize());
        return Component.translatable("juicecraft.menu." + friend.getFriendName().toLowerCase() + ".reactions.generic_accept").getString();
    }

    public static ResourceLocation getPortraitNeutral(String name, Friend friend, FriendMenuScreen screen) {
        return new ResourceLocation("juicecraft", "textures/gui/" + name + "/portrait_neutral.png");
    }

    public static ResourceLocation getPortraitHappy(String name, Friend friend, FriendMenuScreen screen) {
        if (!screen.playedSound) {
            screen.playedSound = true;
            PlaySoundPacketHandler.sendToServer(new ToServerPlaySoundPacket(true, friend.getId()));
        }
        return new ResourceLocation("juicecraft", "textures/gui/" + name + "/portrait_happy.png");
    }

    public static ResourceLocation getPortraitUnhappy(String name, Friend friend, FriendMenuScreen screen) {
        if (!screen.playedSound) {
            screen.playedSound = true;
            PlaySoundPacketHandler.sendToServer(new ToServerPlaySoundPacket(false, friend.getId()));
        }
        return new ResourceLocation("juicecraft", "textures/gui/" + name + "/portrait_unhappy.png");
    }
}

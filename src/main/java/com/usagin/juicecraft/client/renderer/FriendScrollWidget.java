package com.usagin.juicecraft.client.renderer;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractScrollWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class FriendScrollWidget extends AbstractScrollWidget {
    private final FriendMenuScreen screen;
    public FriendScrollWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage, FriendMenuScreen screen) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.screen=screen;
    }

    @Override
    protected int getInnerHeight() {
        return 300;
    }

    @Override
    protected double scrollRate() {
        return 1;
    }

    @Override
    protected void renderContents(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.5F,0.5F,0.5F);
        pGuiGraphics.drawWordWrap(screen.getMinecraft().font, FormattedText.of(screen.friend.getEventLog()),(screen.getGuiLeft() + 294)*2,(screen.getGuiTop()+35)*2,140, ChatFormatting.BLACK.getColor());
        pGuiGraphics.pose().popPose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {

    }
}

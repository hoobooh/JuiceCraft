package com.usagin.juicecraft.client.renderer;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.FittingMultiLineTextWidget;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.network.chat.Component;

public class FriendScrollWidget extends FittingMultiLineTextWidget {
    private final FriendMenuScreen screen;
    private final MultiLineTextWidget multilineWidget;

    public FriendScrollWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage, Font pFont, FriendMenuScreen screen) {
        super(pX, pY, pWidth, pHeight, pMessage, pFont);
        this.screen = screen;
        this.multilineWidget = (new MultiLineTextWidget(pMessage, pFont)).setMaxWidth((int) ((this.getWidth() - this.totalInnerPadding()) * (1 / 0.7F)));
        this.setScrollAmount(this.getMaxScrollAmount());
    }

    public FittingMultiLineTextWidget setColor(int pColor) {
        this.multilineWidget.setColor(pColor);
        return this;
    }

    public void setWidth(int pWidth) {
        super.setWidth(pWidth);
        this.multilineWidget.setMaxWidth(this.getWidth() - this.totalInnerPadding());
    }

    protected int getInnerHeight() {
        return (int) (this.multilineWidget.getHeight() * 0.7F);
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.setMessage(Component.literal(this.screen.friend.getEventLog()));
        this.multilineWidget.setMessage(Component.literal(this.screen.friend.getEventLog()));

        if (this.visible) {

            //this.renderBackground(pGuiGraphics);
            pGuiGraphics.enableScissor(this.getX() + 1, this.getY() + 1, this.getX() + this.width - 1, this.getY() + this.height - 1);
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate(0.0D, -this.scrollAmount(), 0.0D);
            this.renderContents(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            pGuiGraphics.pose().popPose();
            pGuiGraphics.disableScissor();
            this.renderDecorations(pGuiGraphics);

        }

        //super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    protected void renderDecorations(GuiGraphics pGuiGraphics) {
        //do nothing
    }

    protected void renderContents(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().scale(0.7F, 0.7F, 0.7F);
        pGuiGraphics.pose().translate(((float) (this.getX() + this.innerPadding())) * (1 / 0.7F), ((float) (this.getY() + this.innerPadding())) * (1 / 0.7F), 0.0F);
        this.multilineWidget.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.pose().popPose();

    }


}

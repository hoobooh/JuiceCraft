package com.usagin.juicecraft.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FriendButton extends ImageButton {
    protected final WidgetSprites sprites;

    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress) {
        super(pX,pY,pWidth,pHeight,pSprites,pOnPress);
        this.sprites = pSprites;
    }

    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, Component pMessage) {
        super(pX,pY,pWidth,pHeight,pSprites,pOnPress,pMessage);
        this.sprites = pSprites;
    }

    public FriendButton(int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, Component pMessage) {
        super(pWidth,pHeight,pSprites,pOnPress,pMessage);
        this.sprites = pSprites;
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.isFocused());
        pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), this.width, this.height);
        int i = getFGColor();
        this.renderString(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }
    protected void renderScrollingString(GuiGraphics pGuiGraphics, Font pFont, int pWidth, int pColor) {
        int i = this.getX() + pWidth;
        int j = this.getX() + this.getWidth() - pWidth;
        renderScrollingString(pGuiGraphics, pFont, this.getMessage(), i, this.getY(), j, this.getY() + this.getHeight(), pColor);
    }
}

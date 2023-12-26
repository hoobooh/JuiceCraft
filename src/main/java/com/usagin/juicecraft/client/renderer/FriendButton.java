package com.usagin.juicecraft.client.renderer;

import com.mojang.logging.LogUtils;
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
import org.slf4j.Logger;

public class FriendButton extends ImageButton {
    protected final WidgetSprites sprites;
    public boolean focus = false;
    boolean imperm = false;
    public boolean active=true;

    public void setFocus(boolean b) {
        this.focus = b;
    }

    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pSprites, pOnPress);
        this.sprites = pSprites;
    }

    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, boolean b) {
        super(pX, pY, pWidth, pHeight, pSprites, pOnPress);
        this.sprites = pSprites;
        this.imperm = b;
    }
    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, boolean b, boolean c) {
        super(pX, pY, pWidth, pHeight, pSprites, pOnPress);
        this.sprites = pSprites;
        this.imperm = b;
        this.visible=c;
    }

    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pSprites, pOnPress, pMessage);
        this.sprites = pSprites;
    }

    public FriendButton(int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, Component pMessage) {
        super(pWidth, pHeight, pSprites, pOnPress, pMessage);
        this.sprites = pSprites;
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.focus);
        pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), this.width, this.height);
        int i = getFGColor();
        this.renderString(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    protected void renderScrollingString(GuiGraphics pGuiGraphics, Font pFont, int pWidth, int pColor) {
        int i = this.getX() + pWidth;
        int j = this.getX() + this.getWidth() - pWidth;
        renderScrollingString(pGuiGraphics, pFont, this.getMessage(), i, this.getY(), j, this.getY() + this.getHeight(), pColor);
    }


    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.active && this.visible) {
            if (this.isValidClickButton(pButton)) {
                boolean flag = this.clicked(pMouseX, pMouseY);
                if (flag) {
                    if ((!this.focus || this.imperm) && this.active) {
                        this.focus = true;
                        this.playDownSound(Minecraft.getInstance().getSoundManager());
                        this.onClick(pMouseX, pMouseY);
                        return true;
                    }
                }

            }

            return false;
        } else {
            return false;
        }
    }

    public void onRelease(double pMouseX, double pMouseY) {
        if (imperm) {
            this.focus = false;
        }
    }
}

package com.usagin.juicecraft.client.renderer;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.client.menu.FriendMenuTextureLocations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.slf4j.Logger;

public class FriendButton extends ImageButton {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected final WidgetSprites sprites;
    public boolean focus = false;
    public boolean active = true;
    public int extendamount = 0;
    public int color = -1;
    public boolean rendershadow = true;
    protected WidgetSprites bordersprites = new WidgetSprites(FriendMenuTextureLocations.BUTTON_BEFORE, FriendMenuTextureLocations.BUTTON_AFTER);
    boolean imperm = false;

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
        this.visible = c;
    }

    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, WidgetSprites pBorderSprites, Button.OnPress pOnPress, boolean b, boolean c, int color, boolean shadow) {
        super(pX, pY, pWidth, pHeight, pSprites, pOnPress);
        this.sprites = pSprites;
        this.bordersprites = pBorderSprites;
        this.imperm = b;
        this.visible = c;
        this.color = color;
        this.rendershadow = shadow;
    }

    public FriendButton(int pX, int pY, int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pSprites, pOnPress, pMessage);
        this.sprites = pSprites;
    }

    public FriendButton(int pWidth, int pHeight, WidgetSprites pSprites, Button.OnPress pOnPress, Component pMessage) {
        super(pWidth, pHeight, pSprites, pOnPress, pMessage);
        this.sprites = pSprites;
    }

    public void setFocus(boolean b) {
        this.focus = b;
    }

    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.focus);
        ResourceLocation resourcelocation2 = this.bordersprites.get(this.isActive(), this.focus);
        if (color != -1) {
            this.renderString(pGuiGraphics, minecraft.font, this.color);
        } else {
            int i = getFGColor();
            this.renderString(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
        }
        pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), -500, this.width, this.height);
        for (int n = 1; n < this.extendamount + 1; n++) {
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY() + n * this.height, -500, this.width, this.height);
            if (n != this.extendamount + 1) {
                pGuiGraphics.blitSprite(resourcelocation2, this.getX(), this.getY() + n * this.height - this.height / 4, -500, this.width, this.height / 2);
            }
        }

    }

    public void renderString(GuiGraphics pGuiGraphics, Font pFont, int pColor) {
        FormattedText text = FormattedText.of(this.getMessage().getString());
        if (pFont.width(this.getMessage().getString()) >= this.getWidth() * 0.9) {
            this.extendamount = -1;
            int i = 0;
            for (FormattedCharSequence formattedcharsequence : pFont.split(text, (int) (this.getWidth() * 0.9))) {
                pGuiGraphics.drawString(pFont, formattedcharsequence, this.getX() + this.getWidth() / 2 - pFont.width(formattedcharsequence) / 2, (this.getY() + this.getHeight() / 2 - 3) + i, pColor, this.rendershadow);
                i += this.getHeight();
                this.extendamount += 1;
            }
        } else {
            this.extendamount = 0;
            drawCenteredString(pGuiGraphics, pFont, this.getMessage(), this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2 - 3, pColor);
        }
    }

    public void drawCenteredString(GuiGraphics pGuiGraphics, Font pFont, Component pText, int pX, int pY, int pColor) {
        FormattedCharSequence formattedcharsequence = pText.getVisualOrderText();
        pGuiGraphics.drawString(pFont, formattedcharsequence, pX - pFont.width(formattedcharsequence) / 2, pY, pColor, this.rendershadow);
    }


    protected boolean clicked(double pMouseX, double pMouseY) {
        return this.active && this.visible && pMouseX >= (double) this.getX() && pMouseY >= (double) this.getY() && pMouseX < (double) (this.getX() + this.width) && pMouseY < (double) (this.getY() + this.height + this.height * this.extendamount);
    }

    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return this.active && this.visible && pMouseX >= (double) this.getX() && pMouseY >= (double) this.getY() && pMouseX < (double) (this.getX() + this.width) && pMouseY < (double) (this.getY() + this.height + this.height * this.extendamount);
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

    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (this.isValidClickButton(pButton)) {
            this.onRelease(pMouseX, pMouseY);
            return true;
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

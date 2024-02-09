package com.usagin.juicecraft.client.menu;

import com.mojang.logging.LogUtils;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class FriendSlot extends Slot {
    private static final Logger LOGGER = LogUtils.getLogger();
    public boolean highlight = true;
    public boolean tempBypass = false;

    public FriendSlot(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
    }

    @Override
    public @NotNull ItemStack getItem() {
        if (this.highlight || this.tempBypass) {
            return super.getItem();
        } else return ItemStack.EMPTY;
    }

    public boolean isHighlightable() {
        return highlight;
    }
}

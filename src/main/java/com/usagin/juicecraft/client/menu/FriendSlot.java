package com.usagin.juicecraft.client.menu;

import com.mojang.logging.LogUtils;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import org.slf4j.Logger;

public class FriendSlot extends Slot {
    private static final Logger LOGGER = LogUtils.getLogger();
    public boolean highlight=true;
    public FriendSlot(Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
    }
    @Override
    public boolean allowModification(Player pPlayer) {
        return this.mayPickup(pPlayer) && this.mayPlace(this.getItem());
    }
    public boolean isHighlightable() {
        return highlight;
    }
}

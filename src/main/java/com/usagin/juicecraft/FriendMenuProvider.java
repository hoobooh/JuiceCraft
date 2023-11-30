package com.usagin.juicecraft;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class FriendMenuProvider implements MenuProvider {
    Friend friend;
    private static final Logger LOGGER = LogUtils.getLogger();
    public FriendMenuProvider(Friend f){
        this.friend=f;
    }
    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal(friend.getFriendName());
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FriendMenu(pContainerId, pPlayerInventory, friend.inventory, friend);
    }
}

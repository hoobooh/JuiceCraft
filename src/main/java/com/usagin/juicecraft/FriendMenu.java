package com.usagin.juicecraft;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.Init.ItemInit;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

import static com.usagin.juicecraft.Init.MenuInit.FRIEND_MENU;

public class FriendMenu extends AbstractContainerMenu {
    private final Container friendContainer;
    private final Friend friend;

    public static Friend decodeBuffer(Level level, FriendlyByteBuf buffer){
        int i = buffer.readVarInt();
        return level.getEntity(i) instanceof Friend friend ? friend : null;
    }

    // Client menu constructor
    public FriendMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(containerId, playerInventory, decodeBuffer(playerInventory.player.level(), buffer));
    }
    //Server menu constructor
    private static final Logger LOGGER = LogUtils.getLogger();
    public FriendMenu(int pContainerId, Inventory pPlayerInventory, Friend pFriend) {
        super(FRIEND_MENU.get(), pContainerId);
        LOGGER.info("working");
        this.friendContainer = pFriend.inventory;
        this.friend = pFriend;
        int i = 3;
        this.friendContainer .startOpen(pPlayerInventory.player);
        int j = -18;
        LOGGER.info("this one");
        /*Activator slot*/
        this.addSlot(new Slot(this.friendContainer , 3, 8, 81) {
            public boolean mayPlace(ItemStack p_39677_) {
                return p_39677_.is(ItemInit.ACTIVATOR.get()) && pFriend.isLivingTame();
            }
            public boolean isActive() {
                return pFriend.isLivingTame();
            }
        });
        //Armor slot
        this.addSlot(new Slot(this.friendContainer, 1, 8, 36) {
            public boolean mayPlace(ItemStack p_39690_) {
                return pFriend.isArmor(p_39690_);
            }

            public boolean isActive() {
                return pFriend.isArmorable();
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        //Weapon slot
        this.addSlot(new Slot(this.friendContainer, 0, 8, 18) {
            public boolean mayPlace(ItemStack p_39690_) {
                return pFriend.isArmor(p_39690_);
            }

            public boolean isActive() {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        //Module slot
        this.addSlot(new Slot(this.friendContainer, 2, 8, 54) {
            public boolean mayPlace(ItemStack p_39690_) {
                return pFriend.isModule(p_39690_);
            }

            public boolean isActive() {
                return pFriend.isModular();
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < (pFriend.getInventoryColumns()); ++l) {
                this.addSlot(new Slot(this.friendContainer, 4 + l + k * pFriend.getInventoryColumns(), 80 + l * 18, 18 + k * 18));
            }
        }
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(pPlayerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(pPlayerInventory, j1, 8 + j1 * 18, 142));
        }
        LOGGER.info("123");
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !this.friend.hasInventoryChanged(this.friendContainer) && this.friendContainer.stillValid(pPlayer) && this.friend.isAlive() && this.friend.distanceTo(pPlayer) < 8.0F;
    }

    private boolean hasChest(AbstractHorse pHorse) {
        return pHorse instanceof AbstractChestedHorse && ((AbstractChestedHorse) pHorse).hasChest();
    }
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.friendContainer.getContainerSize();
            if (pIndex < i) {
                if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(2).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(3).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.moveItemStackTo(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (pIndex >= j && pIndex < k) {
                    if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= i && pIndex < j) {
                    if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player pPlayer) {
        LOGGER.info("removed");
        super.removed(pPlayer);
        this.friendContainer.stopOpen(pPlayer);
    }
}

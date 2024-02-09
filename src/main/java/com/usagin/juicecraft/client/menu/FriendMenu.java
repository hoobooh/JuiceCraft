package com.usagin.juicecraft.client.menu;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.Init.ItemInit;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static com.usagin.juicecraft.Init.MenuInit.FRIEND_MENU;
import static com.usagin.juicecraft.Init.sounds.UniversalSoundInit.HYPER_EQUIP;

public class FriendMenu extends AbstractContainerMenu {
    //Server menu constructor
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Container friendContainer;
    private final Friend friend;

    // Client menu constructor
    public FriendMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(containerId, playerInventory, decodeBuffer(playerInventory.player.level(), buffer));
    }

    public FriendMenu(int pContainerId, Inventory pPlayerInventory, Friend pFriend) {
        super(FRIEND_MENU.get(), pContainerId);
        this.friendContainer = pFriend.inventory;
        this.friend = pFriend;
        int i = 3;
        this.friendContainer.startOpen(pPlayerInventory.player);
        int j = -18;
        //Activator slot
        this.addSlot(new FriendSlot(this.friendContainer, 0, 136, 133) {
            public boolean mayPlace(ItemStack p_39677_) {
                return p_39677_.is(ItemInit.ACTIVATOR.get()) && pFriend.isLivingTame();
            }

            public void set(ItemStack pStack) {
                this.container.setItem(0, pStack);
                if (!pPlayerInventory.player.level().isClientSide()) {
                    pFriend.appendEventLog(Component.translatable("juicecraft.menu." + pFriend.getFriendName().toLowerCase() + ".eventlog.activator").getString());
                    pFriend.playVoice(pFriend.getHyperEquip());
                    pFriend.playSound(HYPER_EQUIP.get(), 0.3F, 1);
                }
                this.setChanged();
            }

            public boolean mayPickup(Player player) {
                return !this.hasItem();
            }

            public boolean isActive() {
                return pFriend.isLivingTame();
            }
        });
        //Weapon slot
        this.addSlot(new FriendSlot(this.friendContainer, 1, 14, 205) {
            public boolean mayPlace(ItemStack p_39690_) {
                return true;
            }

            public void set(@NotNull ItemStack pStack) {
                this.container.setItem(1, pStack);
                if (this.hasItem()) {
                    if (!pPlayerInventory.player.level().isClientSide()) {
                        pFriend.playTimedVoice(pFriend.getEquip());
                        pFriend.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.3F, 1);
                    }
                }
                this.setChanged();
            }

            public int getMaxStackSize() {
                return 64;
            }

            public boolean isActive() {
                return true;
            }
        });
        //Module slot
        this.addSlot(new FriendSlot(this.friendContainer, 2, 68, 205) {
            public boolean mayPlace(ItemStack p_39690_) {
                return pFriend.isModule(p_39690_);
            }

            public void set(ItemStack pStack) {
                this.container.setItem(2, pStack);
                if (this.hasItem()) {
                    if (!pPlayerInventory.player.level().isClientSide()) {
                        pFriend.playVoice(pFriend.getModuleEquip());
                        pFriend.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.3F, 1);
                    }
                }
                this.setChanged();
            }

            public int getMaxStackSize() {
                return 1;
            }

            public boolean isActive() {
                return pFriend.isModular();
            }
        });
        //Helmet slot
        this.addSlot(new FriendSlot(this.friendContainer, 3, 14, 230) {
            public boolean mayPlace(ItemStack p_39690_) {
                if (p_39690_.getItem() instanceof ArmorItem pItem) {
                    return pItem.getType() == ArmorItem.Type.HELMET;
                }
                return false;
            }

            public void set(ItemStack pStack) {
                this.container.setItem(3, pStack);
                if (this.hasItem()) {
                    if (!pPlayerInventory.player.level().isClientSide()) {
                        pFriend.playTimedVoice(pFriend.getEquip());
                        pFriend.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.3F, 1);
                    }
                }
                this.setChanged();
            }

            public int getMaxStackSize() {
                return 1;
            }

            public boolean isActive() {
                return pFriend.isArmorable();
            }
        });
        //Chest slot
        this.addSlot(new FriendSlot(this.friendContainer, 4, 32, 230) {
            public boolean mayPlace(ItemStack p_39690_) {
                if (p_39690_.getItem() instanceof ArmorItem pItem) {
                    return pItem.getType() == ArmorItem.Type.CHESTPLATE;
                }
                return false;
            }

            public void set(ItemStack pStack) {
                this.container.setItem(4, pStack);
                if (this.hasItem()) {
                    if (!pPlayerInventory.player.level().isClientSide()) {
                        pFriend.playTimedVoice(pFriend.getEquip());
                        pFriend.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.3F, 1);
                    }
                }
                this.setChanged();
            }

            public int getMaxStackSize() {
                return 1;
            }

            public boolean isActive() {
                return pFriend.isArmorable();
            }
        });
        //Leggings slot
        this.addSlot(new FriendSlot(this.friendContainer, 5, 50, 230) {
            public boolean mayPlace(ItemStack p_39690_) {
                if (p_39690_.getItem() instanceof ArmorItem pItem) {
                    return pItem.getType() == ArmorItem.Type.LEGGINGS;
                }
                return false;
            }

            public void set(ItemStack pStack) {
                this.container.setItem(5, pStack);
                if (this.hasItem()) {
                    if (!pPlayerInventory.player.level().isClientSide()) {
                        pFriend.playTimedVoice(pFriend.getEquip());
                        pFriend.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.3F, 1);
                    }
                }
                this.setChanged();
            }

            public int getMaxStackSize() {
                return 1;
            }

            public boolean isActive() {
                return pFriend.isArmorable();
            }
        });
        //Boots slot
        this.addSlot(new FriendSlot(this.friendContainer, 6, 68, 230) {
            public boolean mayPlace(ItemStack p_39690_) {
                if (p_39690_.getItem() instanceof ArmorItem pItem) {
                    return pItem.getType() == ArmorItem.Type.BOOTS;
                }
                return false;
            }

            public void set(ItemStack pStack) {
                this.container.setItem(6, pStack);
                if (this.hasItem()) {
                    if (!pPlayerInventory.player.level().isClientSide()) {
                        pFriend.playTimedVoice(pFriend.getEquip());
                        pFriend.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.3F, 1);
                    }
                }
                this.setChanged();
            }

            public int getMaxStackSize() {
                return 1;
            }

            public boolean isActive() {
                return pFriend.isArmorable();
            }
        });

        //friend inventory
        for (int k = 0; k < (pFriend.getInventoryRows()); ++k) {
            for (int l = 0; l < 5; ++l) {
                this.addSlot(new FriendSlot(this.friendContainer, 7 + k + l * pFriend.getInventoryRows(), 183 + l * 18, 43 + k * 18));
            }
        }
        //player inventory
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new FriendSlot(pPlayerInventory, k1 + i1 * 9 + 9, 111 + k1 * 18, 199 + i1 * 18 + -18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new FriendSlot(pPlayerInventory, j1, 111 + j1 * 18, 239));
        }
    }

    public static Friend decodeBuffer(Level level, FriendlyByteBuf buffer) {
        int i = buffer.readVarInt();
        return level.getEntity(i) instanceof Friend friend ? friend : null;
    }

    public Friend getFriend() {
        return this.friend;
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
            } else if (this.getSlot(3).mayPlace(itemstack1) && !this.getSlot(3).hasItem() && this.getSlot(3).isActive()) {
                if (!this.moveItemStackTo(itemstack1, 3, 4, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(4).mayPlace(itemstack1) && !this.getSlot(4).hasItem() && this.getSlot(4).isActive()) {
                if (!this.moveItemStackTo(itemstack1, 4, 5, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(5).mayPlace(itemstack1) && !this.getSlot(5).hasItem() && this.getSlot(5).isActive()) {
                if (!this.moveItemStackTo(itemstack1, 5, 6, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(6).mayPlace(itemstack1) && !this.getSlot(6).hasItem() && this.getSlot(6).isActive()) {
                if (!this.moveItemStackTo(itemstack1, 6, 7, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(2).mayPlace(itemstack1) && !this.getSlot(2).hasItem() && this.getSlot(2).isActive()) {
                if (!this.moveItemStackTo(itemstack1, 2, 3, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 7, i, false)) {
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
        super.removed(pPlayer);
        this.friend.updateGear();

        this.friendContainer.stopOpen(pPlayer);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !this.friend.hasInventoryChanged(this.friendContainer) && this.friendContainer.stillValid(pPlayer) && this.friend.isAlive() && this.friend.distanceTo(pPlayer) < 8.0F;
    }
}

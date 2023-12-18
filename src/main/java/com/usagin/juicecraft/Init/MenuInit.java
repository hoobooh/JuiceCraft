package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.client.menu.FriendMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class MenuInit {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final RegistryObject<MenuType<FriendMenu>> FRIEND_MENU = MENUS.register("friend_menu", () -> IForgeMenuType.create(FriendMenu::new));
}

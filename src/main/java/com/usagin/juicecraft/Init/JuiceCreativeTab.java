package com.usagin.juicecraft.Init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.Init.ItemInit.*;
import static com.usagin.juicecraft.JuiceCraft.MODID;

public class JuiceCreativeTab extends CreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> ORANGE_TAB = CREATIVE_MODE_TABS.register("juicetab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.FOOD_AND_DRINKS)
            .title(Component.translatable("itemGroup.juicetab"))
            .icon(() -> ORANGE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ORANGE.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(ACTIVATOR.get());
                output.accept(SUMIKA_MEMORY.get());
                output.accept(GOLDEN_ORANGE.get());
            }).build());

    protected JuiceCreativeTab(Builder builder) {
        super(builder);
    }
}

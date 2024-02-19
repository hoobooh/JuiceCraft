package com.usagin.juicecraft.items;

import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class JuiceCraftItem extends Item {
    public JuiceCraftItem(Properties pProperties) {
        super(pProperties);
    }

    //descid = item.juicecraft.*
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.literal("§o" + Component.translatable("desc."+this.getDescriptionId()).getString()));
    }
}

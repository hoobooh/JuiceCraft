package com.usagin.juicecraft.data.loot;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.usagin.juicecraft.Init.ItemInit;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;


public class ChestLootModifier implements IGlobalLootModifier {
    public static ItemStack getRandomLootItem(RandomSource source) {
        int n = source.nextInt(100);
        if (n < 30) {
            return new ItemStack(ItemInit.ORANGE.get(), source.nextInt(9));
        }
        if (n < 40) {
            return new ItemStack(ItemInit.ALTESCOOKING.get(), source.nextInt(4));
        }
        if (n < 50) {
            return new ItemStack(ItemInit.SAKISCOOKIE.get(), source.nextInt(4));
        }
        if (n < 60) {
            return new ItemStack(ItemInit.PUDDING.get(), source.nextInt(4));
        }
        if (n < 70) {
            return new ItemStack(ItemInit.COOKEDSEAGULL.get(), source.nextInt(9));
        }
        if (n < 80) {
            return new ItemStack(ItemInit.CANDY.get(), source.nextInt(10));
        }
        if (n < 90) {
            return new ItemStack(ItemInit.GOLDEN_ORANGE.get(), source.nextInt(3));
        }
        if (n < 93) {
            return new ItemStack(ItemInit.REDBEANICECREAM.get(), source.nextInt(3));
        }
        if (n < 97) {
            return new ItemStack(ItemInit.SUMIKA_MEMORY.get(), 1);
        } else {
            return new ItemStack(ItemInit.ACTIVATOR.get(), 1);
        }
    }

    @Override
    public @NotNull ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (context.hasParam(LootContextParams.BLOCK_ENTITY)) {
            if (context.getParam(LootContextParams.BLOCK_ENTITY).getBlockState().getBlock() instanceof ChestBlock) {
                RandomSource rand = RandomSource.create();
                int n = rand.nextInt(3);
                for (int i = 0; i < n; i++) {
                    generatedLoot.add(getRandomLootItem(rand));
                }
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<IGlobalLootModifier> codec() {
        return LootModifier.DIRECT_CODEC;
    }
}

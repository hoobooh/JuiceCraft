package com.usagin.juicecraft.data.loot;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.usagin.juicecraft.Init.ItemInit;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public class ChestLootModifier extends LootModifier {
    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */

    public final int count;
    public final int chance;
    public ChestLootModifier(LootItemCondition[] conditionsIn, Item item, int count,int chance) {
        super(conditionsIn);
        this.item=item;
        this.count=count;
        this.chance=chance;
    }

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
    public static final Supplier<Codec<ChestLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
            .fieldOf("item").forGetter(m -> m.item)).apply(inst, ChestLootModifier::new)));
    private final Item item;

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for(LootItemCondition condition : this.conditions) {
            if(!condition.test(context)) {
                return generatedLoot;
            }
        }
        RandomSource source = RandomSource.create();
        if(source.nextInt(0,101) < this.chance){
            generatedLoot.add(new ItemStack(this.item,source.nextInt(1,this.count+1)));
        }

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}

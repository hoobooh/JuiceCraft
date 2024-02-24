package com.usagin.juicecraft.data.loot;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.usagin.juicecraft.Init.ItemInit;
import com.usagin.juicecraft.particles.AlteLightningParticle;
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

    public static final Supplier<Codec<ChestLootModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec()
            .fieldOf("item").forGetter(m -> m.item)).and(Codec.INT.fieldOf("count").forGetter(m -> m.count)).and(Codec.INT.fieldOf("chance").forGetter(m -> m.chance)).apply(inst, ChestLootModifier::new)));
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

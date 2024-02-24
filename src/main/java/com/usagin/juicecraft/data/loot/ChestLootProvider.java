package com.usagin.juicecraft.data.loot;

import com.usagin.juicecraft.Init.ItemInit;
import com.usagin.juicecraft.JuiceCraft;
import com.usagin.juicecraft.particles.AlteLightningParticle;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.Set;

public class ChestLootProvider extends GlobalLootModifierProvider {
    public ChestLootProvider(PackOutput output){
        super(output, JuiceCraft.MODID);

    }
    @Override
    protected void start() {
        this.addAllItems(new ResourceLocation("chests/abandoned_mineshaft"));
        this.addAllItems(new ResourceLocation("chests/ancient_city"));
        this.addAllItems(new ResourceLocation("chests/bastion_treasure"));
        this.addAllItems(new ResourceLocation("chests/end_city_treasure"));
        this.addAllItems(new ResourceLocation("chests/buried_treasure"));
        this.addAllItems(new ResourceLocation("chests/ruined_portal"));
        this.addAllItems(new ResourceLocation("chests/simple_dungeon"));
        this.addAllItems(new ResourceLocation("chests/underwater_ruin_big"));
        this.addAllItems(new ResourceLocation("chests/pillager_outpost"));
        this.addAllItems(new ResourceLocation("chests/shipwreck_treasure"));
        this.addAllItems(new ResourceLocation("chests/stronghold_library"));
        this.addAllItems(new ResourceLocation("chests/stronghold_corridor"));
        this.addAllItems(new ResourceLocation("chests/stronghold_crossing"));
        this.addAllItems(new ResourceLocation("chests/igloo_chest"));
        this.addAllItems(new ResourceLocation("chests/jungle_temple"));
        this.addAllItems(new ResourceLocation("chests/desert_pyramid"));
    }

    public void addAllItems(ResourceLocation loc){
        String has = loc.getPath().replace('/','.').replace(':','.') + ".";
        add(has + "orange",new ChestLootModifier(new LootItemCondition[]{
               new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.ORANGE.get(),8,10));
        add(has + "altescooking",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.ALTESCOOKING.get(),3,12));
        add(has + "sakiscookie",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.SAKISCOOKIE.get(),3,8));
        add(has + "pudding",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.PUDDING.get(),3,8));
        add(has + "cookedseagull",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.COOKEDSEAGULL.get(),8,20));
        add(has + "candy",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.CANDY.get(),9,15));
        add(has + "golden_orange",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.GOLDEN_ORANGE.get(),2,5));
        add(has + "redbeanicecream",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.REDBEANICECREAM.get(),2,2));
        add(has + "sumika_memory",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.SUMIKA_MEMORY.get(),1,2));
        add(has + "activator",new ChestLootModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(loc).build()
        }, ItemInit.ACTIVATOR.get(),1,2));


    }
}

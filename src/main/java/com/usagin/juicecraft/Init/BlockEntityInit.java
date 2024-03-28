package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.blocks.FriendBedEntity;
import com.usagin.juicecraft.blocks.plushies.AltePlushieBlockEntity;
import com.usagin.juicecraft.blocks.plushies.SoraPlushieBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.Init.BlockInit.*;
import static com.usagin.juicecraft.JuiceCraft.MODID;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<FriendBedEntity>> FRIEND_BED = BLOCK_ENTITIES.register("friend_bed", () -> BlockEntityType.Builder.of(FriendBedEntity::new, WHITE_FRIEND_BED_BLOCK.get(),ORANGE_FRIEND_BED_BLOCK.get(),YELLOW_FRIEND_BED_BLOCK.get(),BLACK_FRIEND_BED_BLOCK.get(),RED_FRIEND_BED_BLOCK.get(),BLUE_FRIEND_BED_BLOCK.get(),GRAY_FRIEND_BED_BLOCK.get(),PINK_FRIEND_BED_BLOCK.get(),GREEN_FRIEND_BED_BLOCK.get(),PURPLE_FRIEND_BED_BLOCK.get(),CYAN_FRIEND_BED_BLOCK.get(),BROWN_FRIEND_BED_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<AltePlushieBlockEntity>> ALTE_PLUSHIE = BLOCK_ENTITIES.register("alte_plushie", () -> BlockEntityType.Builder.of(AltePlushieBlockEntity::new, BlockInit.ALTE_PLUSHIE.get()).build(null));
    public static final RegistryObject<BlockEntityType<SoraPlushieBlockEntity>> SORA_PLUSHIE = BLOCK_ENTITIES.register("sora_plushie", () -> BlockEntityType.Builder.of(SoraPlushieBlockEntity::new, BlockInit.SORA_PLUSHIE.get()).build(null));


}

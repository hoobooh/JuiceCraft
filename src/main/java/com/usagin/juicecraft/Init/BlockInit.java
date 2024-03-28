package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.blocks.FriendBedBlock;
import com.usagin.juicecraft.blocks.plushies.AltePlushieBlock;
import com.usagin.juicecraft.blocks.plushies.SoraPlushieBlock;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Creates SoraEntityModel new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> ALTE_PLUSHIE = BLOCKS.register("alte_plushie", () -> new AltePlushieBlock(BlockBehaviour.Properties.of().noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> SORA_PLUSHIE = BLOCKS.register("sora_plushie", () -> new SoraPlushieBlock(BlockBehaviour.Properties.of().noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> WHITE_FRIEND_BED_BLOCK = BLOCKS.register("white_friend_bed", () -> bed(DyeColor.WHITE));
    public static final RegistryObject<Block> ORANGE_FRIEND_BED_BLOCK = BLOCKS.register("orange_friend_bed", () -> bed(DyeColor.ORANGE));
    public static final RegistryObject<Block> YELLOW_FRIEND_BED_BLOCK = BLOCKS.register("yellow_friend_bed", () -> bed(DyeColor.YELLOW));
    public static final RegistryObject<Block> BLACK_FRIEND_BED_BLOCK = BLOCKS.register("black_friend_bed", () -> bed(DyeColor.BLACK));
    public static final RegistryObject<Block> RED_FRIEND_BED_BLOCK = BLOCKS.register("red_friend_bed", () -> bed(DyeColor.RED));
    public static final RegistryObject<Block> BLUE_FRIEND_BED_BLOCK = BLOCKS.register("blue_friend_bed", () -> bed(DyeColor.BLUE));
    public static final RegistryObject<Block> GRAY_FRIEND_BED_BLOCK = BLOCKS.register("gray_friend_bed", () -> bed(DyeColor.GRAY));
    public static final RegistryObject<Block> PINK_FRIEND_BED_BLOCK = BLOCKS.register("pink_friend_bed", () -> bed(DyeColor.PINK));
    public static final RegistryObject<Block> GREEN_FRIEND_BED_BLOCK = BLOCKS.register("green_friend_bed", () -> bed(DyeColor.GREEN));
    public static final RegistryObject<Block> PURPLE_FRIEND_BED_BLOCK = BLOCKS.register("purple_friend_bed", () -> bed(DyeColor.PURPLE));
    public static final RegistryObject<Block> CYAN_FRIEND_BED_BLOCK = BLOCKS.register("cyan_friend_bed", () -> bed(DyeColor.CYAN));
    public static final RegistryObject<Block> BROWN_FRIEND_BED_BLOCK = BLOCKS.register("brown_friend_bed", () -> bed(DyeColor.BROWN));

    private static BedBlock bed(DyeColor pColor) {
        return new FriendBedBlock(pColor, BlockBehaviour.Properties.of().mapColor((a) -> a.getValue(BedBlock.PART) == BedPart.FOOT ? pColor.getMapColor() : MapColor.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY));
    }

}

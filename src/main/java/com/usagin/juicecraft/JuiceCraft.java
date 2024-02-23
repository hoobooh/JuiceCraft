package com.usagin.juicecraft;

import com.mojang.logging.LogUtils;
import com.usagin.juicecraft.Init.BlockEntityInit;
import com.usagin.juicecraft.Init.CodecInit;
import com.usagin.juicecraft.Init.sounds.AlteSoundInit;
import com.usagin.juicecraft.Init.sounds.SoraSoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.usagin.juicecraft.Init.EntityInit.ENTITIES;
import static com.usagin.juicecraft.Init.ItemInit.ITEMS;
import static com.usagin.juicecraft.Init.JuiceCreativeTab.CREATIVE_MODE_TABS;
import static com.usagin.juicecraft.Init.MenuInit.MENUS;
import static com.usagin.juicecraft.Init.ParticleInit.PARTICLES;
import static com.usagin.juicecraft.Init.ProjectileInit.PROJECTILES;
import static com.usagin.juicecraft.Init.sounds.UniversalSoundInit.SOUNDS;
import static com.usagin.juicecraft.Init.BlockInit.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JuiceCraft.MODID)
public class JuiceCraft {
    // Define mod id in SoraEntityModel common place for everything to reference
    public static final String MODID = "juicecraft";
    // Create SoraEntityModel Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    // Create SoraEntityModel Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    // Create SoraEntityModel Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    // Creates SoraEntityModel new BlockItem with the id "examplemod:example_block", combining the namespace and path
    // Directly reference SoraEntityModel slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // Creates SoraEntityModel new food item with the id "examplemod:example_id", nutrition 1 and saturation 2

    //public static final RegistryObject<Item> ORANGE = ITEMS.register("orange.png", () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(1).saturationMod(1F).build())));

    // Creates SoraEntityModel creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public JuiceCraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        SoraSoundInit.SORA_SOUNDS.register(modEventBus);
        AlteSoundInit.ALTE_SOUNDS.register(modEventBus);
        SOUNDS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        BlockEntityInit.BLOCK_ENTITIES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        MENUS.register(modEventBus);
        ENTITIES.register(modEventBus);
        PARTICLES.register(modEventBus);
        PROJECTILES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        CodecInit.CODREG.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to SoraEntityModel creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}

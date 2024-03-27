package com.usagin.juicecraft.Init.sounds;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.usagin.juicecraft.JuiceCraft.MODID;
import static net.minecraftforge.registries.ForgeRegistries.SOUND_EVENTS;

public class UniversalSoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(SOUND_EVENTS, MODID);
    public static final RegistryObject<SoundEvent> RECOVERY = registerFriendSoundEvent("friend_recovery");
    public static final RegistryObject<SoundEvent> FRIEND_DEATH = registerFriendSoundEvent("friend_death");
    public static final RegistryObject<SoundEvent> HYPER_EQUIP = registerFriendSoundEvent("friend_hyper_equip");
    public static final RegistryObject<SoundEvent> LIGHT_ATTACK = registerFriendSoundEvent("light_attack");
    public static final RegistryObject<SoundEvent> MEDIUM_ATTACK = registerFriendSoundEvent("medium_attack");
    public static final RegistryObject<SoundEvent> HEAVY_ATTACK = registerFriendSoundEvent("heavy_attack");
    public static final RegistryObject<SoundEvent> COUNTER_BLOCK = registerFriendSoundEvent("counter_block");
    public static final RegistryObject<SoundEvent> COUNTER_ATTACK = registerFriendSoundEvent("counter_attack");
    public static final RegistryObject<SoundEvent> NORMAUP = registerFriendSoundEvent("normaup");
    public static final RegistryObject<SoundEvent> GLITCH = registerFriendSoundEvent("friend_glitch");
    public static final RegistryObject<SoundEvent> LASER_BLAST = registerFriendSoundEvent("laser_blast");
    public static final RegistryObject<SoundEvent> ELECTRIC_STATIC = registerFriendSoundEvent("electric_static");
    public static final RegistryObject<SoundEvent> DICE_THROW = registerFriendSoundEvent("dicethrow");
    public static final RegistryObject<SoundEvent> MEMORY_WRITE = registerFriendSoundEvent("memory_write");
    public static final RegistryObject<SoundEvent> CRITICAL_HIT = registerFriendSoundEvent("critical_hit");
    public static final RegistryObject<SoundEvent> HARBINGER_SLASH = registerFriendSoundEvent("harbinger_slash");
    public static final RegistryObject<SoundEvent> HARBINGER_SLAM = registerFriendSoundEvent("harbinger_slam");
    public static final RegistryObject<SoundEvent> HARBINGER_HIT = registerFriendSoundEvent("harbinger_hit");
    public static final RegistryObject<SoundEvent> HARBINGER_STEP = registerFriendSoundEvent("harbinger_step");
    public static final RegistryObject<SoundEvent> HARBINGER_DEATH = registerFriendSoundEvent("harbinger_death");
    public static final RegistryObject<SoundEvent> HARBINGER_INTRO = registerFriendSoundEvent("harbinger_intro");
    public static final RegistryObject<SoundEvent> HARBINGER_UNSHEATHE = registerFriendSoundEvent("harbinger_unsheathe");
    public static final RegistryObject<SoundEvent> ACCELERATOR = registerFriendSoundEvent("accelerator");
    public static final RegistryObject<SoundEvent> LIGHTNING = registerFriendSoundEvent("lightning");
    static Logger LOGGER = LogUtils.getLogger();

    public static RegistryObject<SoundEvent> registerFriendSoundEvent(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name)));
    }
}

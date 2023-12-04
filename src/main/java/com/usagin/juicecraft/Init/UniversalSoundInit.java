package com.usagin.juicecraft.Init;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.usagin.juicecraft.JuiceCraft.MODID;
import static net.minecraftforge.registries.ForgeRegistries.SOUND_EVENTS;

public class UniversalSoundInit {
    static Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(SOUND_EVENTS, MODID);
    public static final RegistryObject<SoundEvent> RECOVERY = registerFriendSoundEvent("friend_recovery");
    public static final RegistryObject<SoundEvent> FRIEND_DEATH = registerFriendSoundEvent("friend_death");
    public static final RegistryObject<SoundEvent> HYPER_EQUIP = registerFriendSoundEvent("friend_hyper_equip");
    public static RegistryObject<SoundEvent> registerFriendSoundEvent(String name){
        LOGGER.info("sounds/"+name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name)));
    }
}

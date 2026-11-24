package com.kvermin.ultimate_respawn.sound;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class URSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, UltimateRespawn.MOD_ID);

    public static Holder<SoundEvent> ELDRITCH_STEP = SOUND_EVENTS.register(
            "eldritch_step",
            SoundEvent::createVariableRangeEvent
    );
    public static Holder<SoundEvent> WORMS_BURST = SOUND_EVENTS.register(
            "worms_burst",
            SoundEvent::createVariableRangeEvent
    );
}

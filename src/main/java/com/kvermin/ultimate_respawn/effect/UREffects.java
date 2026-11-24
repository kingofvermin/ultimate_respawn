package com.kvermin.ultimate_respawn.effect;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UREffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, UltimateRespawn.MOD_ID);

    public static final Holder<MobEffect> SANDBLASTED = EFFECTS.register(
            "sandblasted",
            () -> new URMobEffect(MobEffectCategory.HARMFUL, 14402683)
    );

    public static class URMobEffect extends MobEffect {
        protected URMobEffect(MobEffectCategory category, int color) {
            super(category, color);
        }
    }
}

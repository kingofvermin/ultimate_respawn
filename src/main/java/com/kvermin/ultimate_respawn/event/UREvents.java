package com.kvermin.ultimate_respawn.event;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.effect.UREffects;
import com.kvermin.ultimate_respawn.entity.URMobs;
import com.kvermin.ultimate_respawn.entity.beetle.Beetle;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorker;
import com.kvermin.ultimate_respawn.entity.chthonian_knight.ChthonianKnight;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCow;
import com.kvermin.ultimate_respawn.entity.worms.Worms;
import net.minecraft.client.Camera;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.joml.Vector3f;

@EventBusSubscriber(modid = UltimateRespawn.MOD_ID)
public class UREvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(URMobs.BEETLE.get(), Beetle.createAttributes().build());
        event.put(URMobs.BOREHOLE_ANT.get(), BoreholeWorker.createAttributes().build());
        event.put(URMobs.CHTHONIAN_KNIGHT.get(), ChthonianKnight.createAttributes().build());
        event.put(URMobs.ORCHAUROCH.get(), AppleCow.createAttributes().build());
        event.put(URMobs.WORMS.get(), Worms.createAttributes().build());
    }

    private static FogType getFogType(Camera camera) {
        FogType blockFogType = camera.getFluidInCamera();
        return blockFogType == FogType.NONE ? FogType.ATMOSPHERIC : blockFogType;
    }
    @SubscribeEvent
    public static void computeFogColor(ViewportEvent.ComputeFogColor event) {
        Entity entity = event.getCamera().entity();

        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(UREffects.SANDBLASTED)) {
            Vector3f color = ARGB.vector3fFromRGB24(14402683);
            event.setRed(color.x);
            event.setGreen(color.y);
            event.setBlue(color.z);
        }
    }
    @SubscribeEvent
    public static void renderFog(ViewportEvent.RenderFog event) {
        Entity entity = event.getCamera().entity();

        if (entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(UREffects.SANDBLASTED)) {
            event.setNearPlaneDistance(4.0F);
            event.setFarPlaneDistance(16.0F);
        }
    }
}

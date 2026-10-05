package com.kvermin.ultimate_respawn.event;

import com.kvermin.ultimate_respawn.UltimateRespawn;
import com.kvermin.ultimate_respawn.entity.URMobs;
import com.kvermin.ultimate_respawn.entity.beetle.Beetle;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorker;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCow;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = UltimateRespawn.MODID)
public class UREvents {

    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(URMobs.BEETLE.get(), Beetle.createAttributes().build());
        event.put(URMobs.BOREHOLE_ANT.get(), BoreholeWorker.createAttributes().build());
        event.put(URMobs.ORCHAUROCH.get(), AppleCow.createAttributes().build());
    }
}

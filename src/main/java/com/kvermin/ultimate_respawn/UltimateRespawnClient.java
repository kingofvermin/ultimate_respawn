package com.kvermin.ultimate_respawn;

import com.kvermin.ultimate_respawn.entity.URMobs;
import com.kvermin.ultimate_respawn.entity.beetle.Beetle;
import com.kvermin.ultimate_respawn.entity.beetle.BeetleModel;
import com.kvermin.ultimate_respawn.entity.beetle.BeetleRenderer;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorker;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorkerModel;
import com.kvermin.ultimate_respawn.entity.borehole_ant.BoreholeWorkerRenderer;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCow;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCowModel;
import com.kvermin.ultimate_respawn.entity.orchauroch.AppleCowRenderer;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = UltimateRespawn.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = UltimateRespawn.MODID, value = Dist.CLIENT)
public class UltimateRespawnClient {
    public UltimateRespawnClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(URMobs.BEETLE.get(), BeetleRenderer::new);
        event.registerEntityRenderer(URMobs.BOREHOLE_ANT.get(), BoreholeWorkerRenderer::new);
        event.registerEntityRenderer(URMobs.ORCHAUROCH.get(), AppleCowRenderer::new);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BeetleModel.LAYER_LOCATION, BeetleModel::createBodyLayer);
        event.registerLayerDefinition(BoreholeWorkerModel.LAYER_LOCATION, BoreholeWorkerModel::createBodyLayer);
        event.registerLayerDefinition(AppleCowModel.LAYER_LOCATION, AppleCowModel::createBodyLayer);
    }
}

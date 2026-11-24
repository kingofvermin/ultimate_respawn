package com.kvermin.ultimate_respawn;

import com.kvermin.ultimate_respawn.block.URBlocks;
import com.kvermin.ultimate_respawn.effect.UREffects;
import com.kvermin.ultimate_respawn.entity.URMobs;
import com.kvermin.ultimate_respawn.item.URItems;

import com.kvermin.ultimate_respawn.particle.URParticleTypes;
import com.kvermin.ultimate_respawn.sound.URSounds;
import com.kvermin.ultimate_respawn.worldgen.tree.URTreePartTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(UltimateRespawn.MOD_ID)
public class UltimateRespawn {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "ultimate_respawn";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public UltimateRespawn(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        URBlocks.BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        URItems.ITEMS.register(modEventBus);

        URMobs.ENTITY_TYPES.register(modEventBus);

        URSounds.SOUND_EVENTS.register(modEventBus);
        URParticleTypes.PARTICLE_TYPES.register(modEventBus);
        UREffects.EFFECTS.register(modEventBus);

        URTreePartTypes.TRUNK_PLACER_TYPES.register(modEventBus);
        URTreePartTypes.ROOT_PLACER_TYPES.register(modEventBus);
        URTreePartTypes.TREE_DECORATOR_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (UltimateRespawn) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab.TabVisibility tabVisibility = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.insertAfter(Blocks.POPLAR_BUTTON.asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_LOG.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_LOG.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_WOOD.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_WOOD.get().asItem().getDefaultInstance(),
                    URBlocks.STRIPPED_ARCH_TREE_LOG.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.STRIPPED_ARCH_TREE_LOG.get().asItem().getDefaultInstance(),
                    URBlocks.STRIPPED_ARCH_TREE_WOOD.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.STRIPPED_ARCH_TREE_WOOD.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_PLANKS.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_PLANKS.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_STAIRS.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_STAIRS.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_SLAB.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_SLAB.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_FENCE.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_FENCE.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_FENCE_GATE.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_FENCE_GATE.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_DOOR.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_DOOR.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_TRAPDOOR.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_TRAPDOOR.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_PRESSURE_PLATE.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_PRESSURE_PLATE.get().asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_BUTTON.get().asItem().getDefaultInstance(),
                    tabVisibility);

            event.insertAfter(URBlocks.ARCH_TREE_BUTTON.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_LOG.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_LOG.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_WOOD.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_WOOD.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_PLANKS.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_PLANKS.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_STAIRS.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_STAIRS.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_SLAB.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_SLAB.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_FENCE.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_FENCE.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_FENCE_GATE.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_FENCE_GATE.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_DOOR.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_DOOR.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_TRAPDOOR.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_TRAPDOOR.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_PRESSURE_PLATE.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.BUTTRESS_PRESSURE_PLATE.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_BUTTON.get().asItem().getDefaultInstance(),
                    tabVisibility);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.insertAfter(Blocks.POPLAR_LOG.asItem().getDefaultInstance(),
                    URBlocks.ARCH_TREE_LOG.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URBlocks.ARCH_TREE_LOG.get().asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_LOG.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(Blocks.WILDFLOWERS.asItem().getDefaultInstance(),
                    URBlocks.GROUND_ARUM.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertBefore(Blocks.SPORE_BLOSSOM.asItem().getDefaultInstance(),
                    URBlocks.EPIPHYTE.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(Blocks.CACTUS.asItem().getDefaultInstance(),
                    URBlocks.BUTTRESS_ROOT.get().asItem().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(Blocks.VINE.asItem().getDefaultInstance(),
                    URBlocks.FAIRY_TABLECLOTH.get().asItem().getDefaultInstance(),
                    tabVisibility);
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.insertAfter(Items.NETHERITE_SWORD.getDefaultInstance(),
                    URItems.ORICHALCUM_GREATSWORD.get().getDefaultInstance(),
                    tabVisibility);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.insertAfter(Items.NETHERITE_INGOT.getDefaultInstance(),
                    URItems.ORICHALCUM_SCRAP.get().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URItems.ORICHALCUM_SCRAP.get().getDefaultInstance(),
                    URItems.ORICHALCUM_INGOT.get().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(Items.SLIME_BALL.getDefaultInstance(),
                    URItems.CRUOR_BALL.get().getDefaultInstance(),
                    tabVisibility);
            event.insertBefore(Items.LEATHER.getDefaultInstance(),
                    URItems.CHITIN.get().getDefaultInstance(),
                    tabVisibility);
        } else if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.insertAfter(Items.MOOSHROOM_SPAWN_EGG.getDefaultInstance(),
                    URItems.ORCHAUROCH_SPAWN_EGG.get().getDefaultInstance(),
                    tabVisibility);
            event.insertBefore(Items.DROWNED_SPAWN_EGG.getDefaultInstance(),
                    URItems.CHTHONIAN_KNIGHT_SPAWN_EGG.get().getDefaultInstance(),
                    tabVisibility);
            event.insertBefore(Items.CAVE_SPIDER_SPAWN_EGG.getDefaultInstance(),
                    URItems.BEETLE_SPAWN_EGG.get().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(URItems.BEETLE_SPAWN_EGG.get().getDefaultInstance(),
                    URItems.BOREHOLE_ANT_SPAWN_EGG.get().getDefaultInstance(),
                    tabVisibility);
            event.insertAfter(Items.SLIME_SPAWN_EGG.getDefaultInstance(),
                    URItems.WORMS_SPAWN_EGG.get().getDefaultInstance(),
                    tabVisibility);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.insertAfter(Items.NETHERITE_HOE.getDefaultInstance(),
                    URItems.ORICHALCUM_DREDGE.get().getDefaultInstance(),
                    tabVisibility);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }
}

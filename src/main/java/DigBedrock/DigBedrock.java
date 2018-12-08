package DigBedrock;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = DigBedrock.MOD_ID,
        name = DigBedrock.MOD_NAME,
        version = DigBedrock.MOD_VERSION,
        dependencies = DigBedrock.MOD_DEPENDENCIES,
        useMetadata = true,
        acceptedMinecraftVersions = DigBedrock.MOD_MC_VERSION)
public class DigBedrock {
    public static final String MOD_ID = "digbedrock";
    public static final String MOD_NAME = "DigBedrock";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "";
    public static final String MOD_MC_VERSION = "[1.12,1.99.99]";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Blocks.BEDROCK.setHardness(200.0F).setHarvestLevel("pickaxe", 3);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBreakingEvent(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() == Blocks.BEDROCK) {
            if (event.getPos().getY() == 0) {
                event.setCanceled(true);
            } else {
                Block.spawnAsEntity(event.getWorld(), event.getPos(), new ItemStack(Item.getItemFromBlock(Blocks.BEDROCK), 1));
            }
        }
    }

    @SubscribeEvent
    public void digSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock() == Blocks.BEDROCK) {
            if (event.getPos().getY() < 40 && event.getPos().getY() > 0) {
                event.setNewSpeed(event.getOriginalSpeed() * event.getPos().getY());
            }
            if (event.getPos().getY() >= 40) {
                event.setNewSpeed(event.getOriginalSpeed() * 40);
            }
        }
    }
}
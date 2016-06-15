package DigBedrock;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod(modid=DigBedrock.MOD_ID,
        name=DigBedrock.MOD_NAME,
        version=DigBedrock.MOD_VERSION,
        acceptedMinecraftVersions = DigBedrock.MOD_MC_VERSION)
public class DigBedrock {

    public static final String MOD_ID = "DigBedrock";
    public static final String MOD_NAME = "DigBedrock";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_MC_VERSION = "@VERSION@";

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Blocks.bedrock.setHardness(200.0F).setHarvestLevel("pickaxe", 3);
	}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onHarvestBedrock(PlayerInteractEvent event) {
        if (event.pos != null) {
            Block block = event.world.getBlockState(event.pos).getBlock();
            if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && block == Blocks.bedrock && event.pos.getY() == 0) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void breakBedRockEvent(BlockEvent.BreakEvent event) {
        Block block = event.state.getBlock();
        if (block == Blocks.bedrock && event.pos.getY() == 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void digSpeed(PlayerEvent.BreakSpeed event) {
        if (event.state.getBlock() == Blocks.bedrock) {
            if (event.pos.getY() <= 0) {
                event.setCanceled(true);
            }
            if (event.pos.getY() < 40 && event.pos.getY() > 0) {
                event.newSpeed = event.originalSpeed * event.pos.getY();
            }
            if (event.pos.getY() >= 40) {
                event.newSpeed = event.originalSpeed * 40;
            }
        }
    }
}
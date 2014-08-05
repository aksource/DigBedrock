package DigBedrock;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod(modid="DigBedrock", name="DigBedrock", version="@VERSION@",dependencies="required-after:FML")

public class DigBedrock
{
	@Mod.Instance("DigBedrock")
	public static DigBedrock instance;
	
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
        Block block = event.world.getBlock(event.x, event.y, event.z);
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && block == Blocks.bedrock && event.y == 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void digSpeed(PlayerEvent.BreakSpeed event) {
        if (event.block == Blocks.bedrock) {
            if (event.y < 40 && event.y > 0) {
                event.newSpeed = event.originalSpeed * event.y;
            }
            if (event.y >= 40) {
                event.newSpeed = event.originalSpeed * 40;
            }
        }
    }
}
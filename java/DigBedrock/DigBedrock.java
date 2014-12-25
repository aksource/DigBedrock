package DigBedrock;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
        Block block = event.world.getBlockState(event.pos).getBlock();
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK && block == Blocks.bedrock && event.pos.getY() == 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void digSpeed(PlayerEvent.BreakSpeed event) {
        if (event.state.getBlock() == Blocks.bedrock) {
            if (event.pos.getY() < 40 && event.pos.getY() > 0) {
                event.newSpeed = event.originalSpeed * event.pos.getY();
            }
            if (event.pos.getY() >= 40) {
                event.newSpeed = event.originalSpeed * 40;
            }
        }
    }
}
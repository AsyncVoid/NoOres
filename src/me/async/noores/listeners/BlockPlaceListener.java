package me.async.noores.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

	public BlockPlaceListener() {
		
	}

	@EventHandler()
	public void onBlockPlace(BlockPlaceEvent ev)
	{
		if(ev.getBlock().getType() == Material.STONE)
		{
			if(ev.getBlock().getLocation().getBlockY() < 60)
			{
				ev.getPlayer().sendMessage("You can't place stone here. Maybe above ground?");
				ev.setCancelled(true);
			}
		}
	}
}

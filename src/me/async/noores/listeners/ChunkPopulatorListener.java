package me.async.noores.listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

public class ChunkPopulatorListener implements Listener {

	public ChunkPopulatorListener() {
		
	}

	@EventHandler
	public void onChunkPopulate(ChunkPopulateEvent ev)
	{
		if(ev.getWorld().getEnvironment() == World.Environment.NORMAL)
		{
			//Bukkit.getServer().broadcastMessage("Chunk populated: " + ev.getChunk().getX() + ", " + ev.getChunk().getZ());
			for(int x = 0; x < 16; x++)
			{
				for(int z = 0; z < 16; z++)
				{
					for(int y = 0; y < 16; y++)
					{
						Block block = ev.getChunk().getBlock(x, y, z);
						if(block.getType() == Material.DIAMOND_ORE)
						{
							block.setType(Material.STONE);
						}
					}
				}
			}
		}
	}
}

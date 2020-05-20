package me.async.noores.listeners;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener{

	private static final Random rand = new Random();
	
	public BlockBreakListener() {
		
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent ev)
	{
		if(ev.getBlock().getType() == Material.STONE)
		{
			int miningLevel = 0;
			ItemStack is = ev.getPlayer().getInventory().getItemInMainHand();
			if(is == null) return;
			if(is.getType() == Material.DIAMOND_PICKAXE || is.getType() == Material.IRON_PICKAXE)
				miningLevel = 2;
			if(is.getType() == Material.STONE_PICKAXE)
				miningLevel = 1;
			
			Block block = ev.getBlock();
			if(block.getY() < 16 && miningLevel >= 2) //Could be diamond
			{
				if(rand.nextInt(1) == 0)   //(1440) < 5   //(4096) < 4
				{
					ev.setExpToDrop(rand.nextInt(4) + 4);
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.DIAMOND, 1));
					//return;
				}
			}
			if(block.getY() < 32 && miningLevel >= 2) //Could be gold
			{
				if(rand.nextInt(1) == 0)   //(7936) < 8.2
				{
					ev.setExpToDrop(rand.nextInt(4) + 4);
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_ORE, 1));
					//return;
				}
			}
			if(block.getY() < 63 && miningLevel >= 1) //Could be iron
			{
				if(rand.nextInt(1) == 0)   //(16128) < 77 
				{
					ev.setExpToDrop(rand.nextInt(4) + 4);
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.IRON_ORE, 1));
					//return;
				}
			}
		}
	}
	
	
}

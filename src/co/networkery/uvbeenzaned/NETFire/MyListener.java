package co.networkery.uvbeenzaned.NETFire;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class MyListener implements Listener
{
	
    public File configFile;
    public File usersFile;
    public FileConfiguration config;
    public FileConfiguration users;
	
	@EventHandler
	public void onPowerBlock(BlockRedstoneEvent event)
	{
		if(config.getBoolean("enabled"))
		{
			Block pblock = event.getBlock();
			if(event.getNewCurrent() > 0)
			{
				if(pblock.getType() == Material.NETHERRACK)
				{
					if(config.getBoolean("remove-block-above"))
					{
						pblock.getRelative(BlockFace.UP).setType(Material.FIRE);
					}
					else
					{
						if(pblock.getRelative(BlockFace.UP).getType() == Material.AIR)
						{
							pblock.getRelative(BlockFace.UP).setType(Material.FIRE);
						}
					}
				}
			}
			else
			{
				if(pblock.getRelative(BlockFace.UP).getType() == Material.FIRE)
				{
					pblock.getRelative(BlockFace.UP).setType(Material.AIR);
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		if(config.getBoolean("enabled"))
		{
			Block newblock = event.getBlockPlaced();
			if(newblock.getType() == Material.NETHERRACK)
			{
				if(newblock.getBlockPower() > 0)
				{
					if(config.getBoolean("remove-block-above"))
					{
						newblock.getRelative(BlockFace.UP).setType(Material.FIRE);
					}
					else
					{
						if(newblock.getRelative(BlockFace.UP).getType() == Material.AIR)
						{
							newblock.getRelative(BlockFace.UP).setType(Material.FIRE);
						}
					}
				}
			}
		}
	}
}

package co.networkery.uvbeenzaned.NETFire;

import java.io.File;
import java.util.Arrays;

import static org.bukkit.Material.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import static org.bukkit.block.BlockFace.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class NETFireListener implements Listener
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
            if(pblock.getType() == REDSTONE_WIRE)
            {
                setAlight(pblock, event.getNewCurrent() > 0, 1);
            }
            else
            {
                setAlight(pblock, event.getNewCurrent() > 0, 0);
            }
        }
    }

    /**
     * Kinda sorta emulates pre-1.7 redstone notification behaviour.
     */
    private void setAlight(Block block, boolean alight, int level)
    {
        for(BlockFace dir: Arrays.asList(WEST, NORTH, EAST, SOUTH))
        {
            Block relBlock = block.getRelative(dir);
            if(relBlock.getType() == NETHERRACK)
            {
                if(alight && (config.getBoolean("remove-block-above") || relBlock.getRelative(UP).isEmpty()))
                {
                    relBlock.getRelative(UP).setType(FIRE);
                }
                else if((!alight) && relBlock.getRelative(UP).getType() == FIRE)
                {
                    relBlock.getRelative(UP).setType(AIR);
                }
                if (level > 0)
                {
                    setAlight(relBlock, alight, level - 1);
                }
            }
        }
    }
}

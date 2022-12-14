package com.teambucket.kurumi.noliterservermanager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class SecurityExplode implements Listener
{
    @EventHandler
    public void OnBlockExplode(BlockExplodeEvent event)
    {
        event.blockList().clear();
    }

    @EventHandler
    public void OnEntityExplode(EntityExplodeEvent event)
    {
        event.blockList().clear();
    }
}

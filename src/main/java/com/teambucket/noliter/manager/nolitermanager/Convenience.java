package com.teambucket.noliter.manager.nolitermanager;

import de.tr7zw.nbtapi.NBTTileEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Convenience implements Listener
{
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Block block = player.getTargetBlock(5);

        ActionBarCommand(player, block);
    }

    public static void ActionBarCommand(Player player, Block block)
    {
        if (player.getGameMode() == GameMode.CREATIVE)
        {
            String command = Security.GetCommand(block);
            if (command != "")
                player.sendActionBar(Component.text(command));
        }
    }
}

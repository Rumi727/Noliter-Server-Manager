package com.teambucket.kurumi.noliterservermanager;

import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

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
            if (!Objects.equals(command, ""))
                player.sendActionBar(Component.text(command));
        }
    }
}

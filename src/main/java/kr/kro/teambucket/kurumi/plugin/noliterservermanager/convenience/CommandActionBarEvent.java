package kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience;

import kr.kro.teambucket.kurumi.plugin.noliterservermanager.security.CommandBlockEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public final class CommandActionBarEvent implements Listener
{
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Block block = player.getTargetBlock(null, 5);

        ActionBarCommand(player, block);
    }

    public static void ActionBarCommand(Player player, Block block)
    {
        if (player.getGameMode() == GameMode.CREATIVE)
        {
            String command = CommandBlockEvent.GetCommand(block);
            if (!Objects.equals(command, ""))
                player.sendActionBar(Component.text(command));
        }
    }
}

package kr.kro.teamdodoco.rumi.plugin.noliterservermanager.convenience;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class SlimeBallThrowEvent implements Listener
{
    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Action action = event.getAction();
        World world = player.getWorld();
        ItemStack itemStack = event.getItem();

        if (action.isRightClick())
        {
            if (itemStack != null && itemStack.getType() == Material.SLIME_BALL)
            {
                event.setCancelled(true);
                player.launchProjectile(Snowball.class).setItem(new ItemStack(Material.SLIME_BALL));

                List<Player> players = world.getPlayers();
                for (Player player2 : players)
                    player2.playSound(Sound.sound(Key.key("minecraft:entity.snowball.throw"), Sound.Source.PLAYER, 0.5f, 0.5f), location.getX(), location.getY(), location.getZ());

                if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                    itemStack.add(-1);
            }
        }
    }
}

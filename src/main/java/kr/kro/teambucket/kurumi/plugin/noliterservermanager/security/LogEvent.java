package kr.kro.teambucket.kurumi.plugin.noliterservermanager.security;

import kr.kro.teambucket.kurumi.plugin.noliterservermanager.Debug;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class LogEvent implements Listener
{
    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        Location position = player.getLocation();
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        Debug.LogNoTitle("[" + player.getWorld().getName() + "] " + x + ", " + y + ", " + z);
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        Location position = player.getLocation();
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        Debug.LogNoTitle("[" + player.getWorld().getName() + "] " + x + ", " + y + ", " + z);
    }
}

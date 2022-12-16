package kr.kro.teambucket.kurumi.plugin.noliterservermanager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SecurityLog implements Listener
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
}

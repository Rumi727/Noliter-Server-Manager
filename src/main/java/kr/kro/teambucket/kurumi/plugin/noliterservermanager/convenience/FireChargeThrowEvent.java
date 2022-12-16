package kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class FireChargeThrowEvent implements Listener
{
    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemStack = event.getItem();

        if (action.isRightClick())
        {
            if (itemStack != null && itemStack.getType() == Material.FIRE_CHARGE)
            {
                event.setCancelled(true);
                player.launchProjectile(Fireball.class);

                if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
                    itemStack.add(-1);
            }
        }
    }
}

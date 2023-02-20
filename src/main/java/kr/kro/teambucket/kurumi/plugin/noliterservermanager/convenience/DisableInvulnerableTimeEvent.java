package kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience;

import kr.kro.teambucket.kurumi.plugin.noliterservermanager.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public final class DisableInvulnerableTimeEvent implements Listener
{
    public static boolean disablePlayerInvulnerableTime = false;
    public static boolean disableMobInvulnerableTime = false;

    @EventHandler
    public void OnEntityDamageEvent(EntityDamageEvent event)
    {
        Entity damageEntity = event.getEntity();
        boolean disableInvulnerableTime;
        if (damageEntity instanceof Player)
            disableInvulnerableTime = disablePlayerInvulnerableTime;
        else
            disableInvulnerableTime = disableMobInvulnerableTime;

        if (disableInvulnerableTime && damageEntity instanceof LivingEntity livingEntity)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    livingEntity.setNoDamageTicks(0);
                }
            }.runTaskLater(Main.plugin, 0);
        }
    }
}
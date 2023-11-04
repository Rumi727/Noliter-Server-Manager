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
    private static boolean disablePlayerInvulnerableTime = false;
    private static boolean disableMobInvulnerableTime = false;

    public static boolean isDisablePlayerInvulnerableTime() {
        return disablePlayerInvulnerableTime;
    }

    public static void setDisablePlayerInvulnerableTime(boolean value) {
        DisableInvulnerableTimeEvent.disablePlayerInvulnerableTime = value;

        Main.config.set("disablePlayerInvulnerableTime", value);
        Main.plugin.saveConfig();
    }

    public static boolean isDisableMobInvulnerableTime() {
        return disableMobInvulnerableTime;
    }

    public static void setDisableMobInvulnerableTime(boolean value) {
        DisableInvulnerableTimeEvent.disableMobInvulnerableTime = value;

        Main.config.set("disableMobInvulnerableTime", value);
        Main.plugin.saveConfig();
    }

    @EventHandler
    public void OnEntityDamageEvent(EntityDamageEvent event)
    {
        Entity damageEntity = event.getEntity();
        boolean disableInvulnerableTime;
        if (damageEntity instanceof Player)
            disableInvulnerableTime = isDisablePlayerInvulnerableTime();
        else
            disableInvulnerableTime = isDisableMobInvulnerableTime();

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
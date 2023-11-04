package kr.kro.teamdodoco.rumi.plugin.noliterservermanager.convenience;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class ItemFrameProtect implements Listener
{
    @EventHandler
    public void OnEntityDamageEvent(EntityDamageEvent event)
    {
        if (event.getEntity().getType() == EntityType.ITEM_FRAME)
            if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
                event.setCancelled(true);
    }
}

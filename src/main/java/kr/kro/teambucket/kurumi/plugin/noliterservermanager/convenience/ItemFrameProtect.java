package kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import kr.kro.teambucket.kurumi.plugin.noliterservermanager.Debug;
import kr.kro.teambucket.kurumi.plugin.noliterservermanager.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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

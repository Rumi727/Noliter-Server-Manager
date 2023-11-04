package kr.kro.teamdodoco.rumi.plugin.noliterservermanager.convenience;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import kr.kro.teamdodoco.rumi.plugin.noliterservermanager.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public final class EntityDamageEffectEvent implements Listener
{
    @EventHandler
    public void OnEntityDamageEvent(EntityDamageEvent event)
    {
        if (event.getDamage() <= 0 && event.getCause() == EntityDamageEvent.DamageCause.MAGIC)
        {
            Entity damageEntity = event.getEntity();
            PacketContainer damageEffect = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
            damageEffect.getIntegers().write(0, damageEntity.getEntityId());
            damageEffect.getBytes().write(0, (byte) 2);

            //@SuppressWarnings("unchecked")
            Collection<? extends Player> players = Main.server.getOnlinePlayers();
            for (Player player : players)
            {
                try
                {
                    Main.packetManager.sendServerPacket(player, damageEffect);
                }
                catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }

            event.setCancelled(true);
        }
    }
}

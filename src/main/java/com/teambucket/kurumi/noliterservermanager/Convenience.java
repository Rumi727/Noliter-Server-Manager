package com.teambucket.kurumi.noliterservermanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Objects;

public class Convenience implements Listener
{
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Block block = player.getTargetBlock(5);

        ActionBarCommand(player, block);
    }

    @EventHandler
    public void OnEntityDamageEvent(EntityDamageEvent event)
    {
        if (event.getDamage() <= 0 && event.getCause() == EntityDamageEvent.DamageCause.MAGIC)
        {
            Entity damageEntity = event.getEntity();

            PacketContainer damageEffect = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
            damageEffect.getIntegers().write(0, damageEntity.getEntityId());
            damageEffect.getBytes().write(0, (byte) 2);

            @SuppressWarnings("unchecked")
            Collection<Player> players = (Collection<Player>) Main.server.getOnlinePlayers();
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
    public static void ActionBarCommand(Player player, Block block)
    {
        if (player.getGameMode() == GameMode.CREATIVE)
        {
            String command = Security.GetCommand(block);
            if (!Objects.equals(command, ""))
                player.sendActionBar(Component.text(command));
        }
    }
}

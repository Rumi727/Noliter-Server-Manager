package com.teambucket.kurumi.noliterservermanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
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

    @EventHandler
    public void NotePlayEvent(NotePlayEvent notePlayEvent)
    {
        if (notePlayEvent.isCancelled())
            return;

        Block block = notePlayEvent.getBlock();
        Location location = block.getLocation();
        Instrument instrument = notePlayEvent.getInstrument();
        Note note = notePlayEvent.getNote();
        World world = block.getWorld();
        boolean noDelay = false;

        BlockState signBlock = world.getBlockAt(location.getBlockX(), location.getBlockY() - 1, location.getBlockZ()).getState();
        if (signBlock instanceof Sign)
            noDelay = ((Sign)signBlock).line(0).equals(Component.text("no delay"));

        signBlock = world.getBlockAt(location.getBlockX(), location.getBlockY() - 2, location.getBlockZ()).getState();
        if (signBlock instanceof Sign && !noDelay)
            noDelay = ((Sign)signBlock).line(0).equals(Component.text("no delay"));

        if (!noDelay)
        {
            notePlayEvent.setCancelled(true);

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    @SuppressWarnings("unchecked")
                    Collection<Player> players = (Collection<Player>) Main.server.getOnlinePlayers();
                    for (Player player : players)
                    {
                        //noinspection deprecation
                        player.spawnParticle(Particle.NOTE,location.getX() + 0.5, location.getY() + 1, location.getZ() + 0.5, 0, note.getId() / 24d, 0, 0, 1);
                        player.playNote(location, instrument, note);
                    }
                }
            }.runTaskLater(Main.plugin, 1);
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

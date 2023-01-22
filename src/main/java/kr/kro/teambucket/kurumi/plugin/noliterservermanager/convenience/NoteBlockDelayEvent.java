package kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience;

import kr.kro.teambucket.kurumi.plugin.noliterservermanager.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public final class NoteBlockDelayEvent implements Listener
{
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

            new BukkitRunnable ()
            {
                @Override
                public void run()
                {
                    @SuppressWarnings("unchecked")
                    Collection<Player> players = (Collection<Player>) Main.server.getOnlinePlayers();
                    for (Player player : players)
                    {
                        Note.Tone tone = note.getTone();
                        int id = 0;
                        if (tone == Note.Tone.G)
                            id = 1;
                        else if (tone == Note.Tone.A)
                            id = 3;
                        else if (tone == Note.Tone.B)
                            id = 5;
                        else if (tone == Note.Tone.C)
                            id = 6;
                        else if (tone == Note.Tone.D)
                            id = 8;
                        else if (tone == Note.Tone.E)
                            id = 10;
                        else if (tone == Note.Tone.F)
                            id = 11;

                        if (note.isSharped())
                            id++;

                        id %= Note.Tone.TONES_COUNT;
                        id += note.getOctave() * Note.Tone.TONES_COUNT;

                        player.spawnParticle(Particle.NOTE,location.getX() + 0.5, location.getY() + 1, location.getZ() + 0.5, 0, id / (Note.Tone.TONES_COUNT * 2d), 0, 0, 1);
                        player.playNote(location, instrument, note);
                    }
                }
            }.runTaskLater(Main.plugin, 1);
        }
    }
}

package kr.kro.teambucket.kurumi.noliterservermanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.List;
import java.util.logging.Logger;

public final class Main extends JavaPlugin
{
    public static Plugin plugin;
    public static Server server;
    public static Logger debug;
    public static ProtocolManager packetManager;
    public static ScoreboardManager scoreboardManager;
    public static Scoreboard newScoreboard;
    public static List<World> worlds;

    public static final String commandLabel = "noliter";

    @Override
    public void onEnable()
    {
        plugin = this;
        server = getServer();
        debug = server.getLogger();

        packetManager = ProtocolLibrary.getProtocolManager();

        scoreboardManager = Bukkit.getScoreboardManager();
        newScoreboard = scoreboardManager.getNewScoreboard();
        worlds = Bukkit.getWorlds();



        packetManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.SET_COMMAND_BLOCK)
        {
            @Override
            public void onPacketReceiving(PacketEvent event)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bukkit.getScheduler().runTask(plugin, () -> SecurityCommandBlock.OnCommandMerge(event.getPlayer()));
            }
        });

        //region Event
        server.getPluginManager().registerEvents(new SecurityCommandBlock(), this);
        server.getPluginManager().registerEvents(new SecurityExplode(), this);
        server.getPluginManager().registerEvents(new SecurityLog(), this);

        server.getPluginManager().registerEvents(new Convenience(), this);
        //endregion

        //region Command
        PluginCommand label = getCommand(commandLabel);
        if (label != null)
            label.setExecutor(new CommandManager());
        //endregion
    }
}
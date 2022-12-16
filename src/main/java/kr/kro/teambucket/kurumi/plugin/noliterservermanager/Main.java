package kr.kro.teambucket.kurumi.plugin.noliterservermanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import kr.kro.teambucket.kurumi.plugin.noliterservermanager.command.CommandManager;
import kr.kro.teambucket.kurumi.plugin.noliterservermanager.security.CommandBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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
    public static PluginManager pluginManager;
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

        pluginManager = server.getPluginManager();

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

                Bukkit.getScheduler().runTask(plugin, () -> CommandBlockEvent.OnCommandMerge(event.getPlayer()));
            }
        });

        EventManager.Init(this, pluginManager);

        PluginCommand label = getCommand(commandLabel);
        if (label != null)
            label.setExecutor(new CommandManager());
    }
}

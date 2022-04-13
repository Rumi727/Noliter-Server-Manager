package com.teambucket.kurumi.noliterservermanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.List;
import java.util.logging.Logger;

public final class Main extends JavaPlugin
{
    public static Server server;
    public static Logger debug;
    public static ProtocolManager packetManager;
    public static ScoreboardManager scoreboardManager;
    public static Scoreboard newScoreboard;
    public static List<World> worlds;

    @Override
    public void onEnable()
    {
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

                Bukkit.getScheduler().runTask(plugin, () ->
                {
                    Security.OnCommandMerge(event.getPlayer());
                });
            }
        });

        server.getPluginManager().registerEvents(new Security(), this);
        server.getPluginManager().registerEvents(new Convenience(), this);
    }

    @Override
    public void onDisable()
    {
        debug.info("Noliter Manager Disabled");
    }
}

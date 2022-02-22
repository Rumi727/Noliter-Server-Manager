package com.teambucket.noliter.manager.nolitermanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class Main extends JavaPlugin
{
    public static Server server;
    public static Logger debug;
    public static ProtocolManager packetManager;

    @Override
    public void onEnable()
    {
        server = getServer();
        debug = server.getLogger();

        server.getPluginManager().registerEvents(new Security(), this);
        server.getPluginManager().registerEvents(new Convenience(), this);


        packetManager = ProtocolLibrary.getProtocolManager();
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
                    Player player = event.getPlayer();
                    Block block = player.getTargetBlock(5);
                    Location pos = block.getLocation();
                    String name = block.getType().name();

                    if (Security.GetCommandAuto(block))
                    {
                        if (name == "COMMAND_BLOCK")
                            Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 항상 활성화된 명령 블록을 수정 했습니다");
                        else if (name == "CHAIN_COMMAND_BLOCK")
                            Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 항상 활성화된 연쇄형 명령 블록을 수정 했습니다");
                        else if (name == "REPEATING_COMMAND_BLOCK")
                            Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 항상 활성화된 반복형 명령 블록을 수정 했습니다");
                    }
                    else
                    {
                        if (name == "COMMAND_BLOCK")
                            Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 명령 블록을 수정 했습니다");
                        else if (name == "CHAIN_COMMAND_BLOCK")
                            Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 연쇄형 명령 블록을 수정 했습니다");
                        else if (name == "REPEATING_COMMAND_BLOCK")
                            Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 반복형 명령 블록을 수정 했습니다");
                    }

                    String command = Security.GetCommand(block);
                    if (command != "")
                        Main.debug.info("입력된 커맨드: " + command);
                    else
                        Main.debug.info("입력된 커맨드가 없습니다");

                    Convenience.ActionBarCommand(player, block);
                });
            }
        });
    }

    @Override
    public void onDisable()
    {
        debug.info("Noliter Manager Disabled");
    }
}

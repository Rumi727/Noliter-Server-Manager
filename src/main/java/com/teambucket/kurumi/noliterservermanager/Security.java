package com.teambucket.kurumi.noliterservermanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Objects;

public class Security implements Listener
{
    @EventHandler
    public void OnPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        Location position = player.getLocation();
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        Main.debug.info("[" + player.getWorld().getName() + "] " + x + ", " + y + ", " + z);
    }

    @EventHandler
    public void OnPlaceEvent(BlockPlaceEvent event)
    {
        Block block = event.getBlock();
        Location pos = block.getLocation();
        String name = block.getType().name();
        Player player = event.getPlayer();

        if (GetCommandAuto(block))
        {
            switch (name) {
                case "COMMAND_BLOCK":
                    Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 항상 활성화된 명령 블록을 설치 했습니다");
                    break;
                case "CHAIN_COMMAND_BLOCK":
                    Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 항상 활성화된 연쇄형 명령 블록을 설치 했습니다");
                    break;
                case "REPEATING_COMMAND_BLOCK":
                    Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 항상 활성화된 반복형 명령 블록을 설치 했습니다");
                    break;
            }
        }
        else
        {
            switch (name) {
                case "COMMAND_BLOCK":
                    Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 명령 블록을 설치 했습니다");
                    break;
                case "CHAIN_COMMAND_BLOCK":
                    Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 연쇄형 명령 블록을 설치 했습니다");
                    break;
                case "REPEATING_COMMAND_BLOCK":
                    Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 반복형 명령 블록을 설치 했습니다");
                    break;
            }
        }

        String command = GetCommand(block);
        if (!Objects.equals(command, ""))
            Main.debug.info("입력된 커맨드: " + command);
        else
            Main.debug.info("입력된 커맨드가 없습니다");

        Convenience.ActionBarCommand(player, block);
    }

    @EventHandler
    public void OnBreakEvent(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        Location pos = block.getLocation();
        String name = block.getType().name();
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType().name().equals("WOODEN_SWORD"))
            return;
        else if (player.getInventory().getItemInMainHand().getType().name().equals("STONE_SWORD"))
            return;
        else if (player.getInventory().getItemInMainHand().getType().name().equals("GOLDEN_SWORD"))
            return;
        else if (player.getInventory().getItemInMainHand().getType().name().equals("IRON_SWORD"))
            return;
        else if (player.getInventory().getItemInMainHand().getType().name().equals("DIAMOND_SWORD"))
            return;
        else if (player.getInventory().getItemInMainHand().getType().name().equals("NETHERITE_SWORD"))
            return;
        else if (player.getInventory().getItemInMainHand().getType().name().equals("DEBUG_STICK"))
            return;

        String command = GetCommand(block);
        if (!Objects.equals(command, ""))
        {
            player.sendMessage("비어있지 않은 명령 블록을 손으로 파괴 할 수 없습니다");
            event.setCancelled(true);

            if (GetCommandAuto(block))
            {
                switch (name) {
                    case "COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 항상 활성화된 명령 블록을 파괴하려 했습니다");
                        break;
                    case "CHAIN_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 항상 활성화된 연쇄형 명령 블록을 파괴하려 했습니다");
                        break;
                    case "REPEATING_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 항상 활성화된 반복형 명령 블록을 파괴하려 했습니다");
                        break;
                }
            }
            else
            {
                switch (name) {
                    case "COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 명령 블록을 파괴하려 했습니다");
                        break;
                    case "CHAIN_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 연쇄형 명령 블록을 파괴하려 했습니다");
                        break;
                    case "REPEATING_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 반복형 명령 블록을 파괴하려 했습니다");
                        break;
                }
            }

            Main.debug.info("입력된 커맨드가 없습니다");
        }
        else
        {
            if (GetCommandAuto(block))
            {
                switch (name) {
                    case "COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 항상 활성화된 명령 블록을 파괴 했습니다");
                        break;
                    case "CHAIN_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 항상 활성화된 연쇄형 명령 블록을 파괴 했습니다");
                        break;
                    case "REPEATING_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 항상 활성화된 반복형 명령 블록을 파괴 했습니다");
                        break;
                }
            }
            else
            {
                switch (name) {
                    case "COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 명령 블록을 파괴 했습니다");
                        break;
                    case "CHAIN_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 연쇄형 명령 블록을 파괴 했습니다");
                        break;
                    case "REPEATING_COMMAND_BLOCK":
                        Main.debug.info(player.getName() + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 있는 반복형 명령 블록을 파괴 했습니다");
                        break;
                }
            }

            Main.debug.info("입력된 커맨드: " + command);
        }
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

    public static String GetCommand(Block block)
    {
        String name = block.getType().name();

        if (name.equals("COMMAND_BLOCK") || name.equals("CHAIN_COMMAND_BLOCK") || name.equals("REPEATING_COMMAND_BLOCK"))
        {
            NBTTileEntity nbt = new NBTTileEntity(block.getState());
            return nbt.getString("Command");
        }

        return "";
    }

    public static boolean GetCommandAuto(Block block)
    {
        String name = block.getType().name();

        if (name.equals("COMMAND_BLOCK") || name.equals("CHAIN_COMMAND_BLOCK") || name.equals("REPEATING_COMMAND_BLOCK"))
        {
            NBTTileEntity nbt = new NBTTileEntity(block.getState());
            return nbt.getBoolean("auto");
        }

        return false;
    }
}

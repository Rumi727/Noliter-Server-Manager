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
        String blockName = block.getType().name();
        Player player = event.getPlayer();

        if (blockName.equals("COMMAND_BLOCK") || blockName.equals("CHAIN_COMMAND_BLOCK") || blockName.equals("REPEATING_COMMAND_BLOCK"))
            CommandMergeLog(player, block, "설치");
    }

    @EventHandler
    public void OnBreakEvent(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        String blockName = block.getType().name();
        Player player = event.getPlayer();

        if (blockName.equals("COMMAND_BLOCK") || blockName.equals("CHAIN_COMMAND_BLOCK") || blockName.equals("REPEATING_COMMAND_BLOCK"))
        {
            String playerSelectedItemName = player.getInventory().getItemInMainHand().getType().name();
            if (playerSelectedItemName.equals("WOODEN_SWORD"))
                return;
            else if (playerSelectedItemName.equals("STONE_SWORD"))
                return;
            else if (playerSelectedItemName.equals("GOLDEN_SWORD"))
                return;
            else if (playerSelectedItemName.equals("IRON_SWORD"))
                return;
            else if (playerSelectedItemName.equals("DIAMOND_SWORD"))
                return;
            else if (playerSelectedItemName.equals("NETHERITE_SWORD"))
                return;
            else if (playerSelectedItemName.equals("DEBUG_STICK"))
                return;

            String command = GetCommand(block);
            if (!Objects.equals(command, "")) {
                player.sendMessage("비어있지 않은 명령 블록을 손으로 파괴 할 수 없습니다");
                event.setCancelled(true);

                CommandMergeLog(player, block, "파괴하려");
            }
            else
                CommandMergeLog(player, block, "파괴");
        }
    }

    public static void OnCommandMerge(Player player)
    {
        Block block = player.getTargetBlock(5);
        if (block == null)
            return;

        CommandMergeLog(player, block, "수정");
        Convenience.ActionBarCommand(player, block);
    }

    public static void CommandMergeLog(Player player, Block block, String text)
    {
        String playerName = player.getName();
        String blockName = block.getType().name();
        Location pos = block.getLocation();

        String temp = "";
        if (Security.GetCommandAuto(block))
        {
            switch (blockName) {
                case "COMMAND_BLOCK":
                    temp = "항상 활성화된 명령 블록";
                    break;
                case "CHAIN_COMMAND_BLOCK":
                    temp = "항상 활성화된 연쇄형 명령 블록";
                    break;
                case "REPEATING_COMMAND_BLOCK":
                    temp = "항상 활성화된 반복형 명령 블록";
                    break;
            }
        }
        else
        {
            switch (blockName) {
                case "COMMAND_BLOCK":
                    temp = "명령 블록";
                    break;
                case "CHAIN_COMMAND_BLOCK":
                    temp = "연쇄형 명령 블록";
                    break;
                case "REPEATING_COMMAND_BLOCK":
                    temp = "반복형 명령 블록";
                    break;
            }
        }

        Main.debug.info(playerName + "(" + player.getUniqueId() + ")" + "(이)가 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 " + temp + "을 " + text + " 했습니다");

        String command = Security.GetCommand(block);
        if (!Objects.equals(command, ""))
            Main.debug.info("입력된 커맨드: " + command);
        else
            Main.debug.info("입력된 커맨드가 없습니다");
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

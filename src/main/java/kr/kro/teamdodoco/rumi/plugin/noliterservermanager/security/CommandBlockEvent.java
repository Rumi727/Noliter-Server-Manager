package kr.kro.teamdodoco.rumi.plugin.noliterservermanager.security;

import de.tr7zw.nbtapi.NBTTileEntity;
import kr.kro.teamdodoco.rumi.plugin.noliterservermanager.Debug;
import kr.kro.teamdodoco.rumi.plugin.noliterservermanager.convenience.CommandActionBarEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public final class CommandBlockEvent implements Listener
{
    //region Event
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
            switch (playerSelectedItemName)
            {
                case "WOODEN_SWORD", "STONE_SWORD", "GOLDEN_SWORD", "IRON_SWORD", "DIAMOND_SWORD", "NETHERITE_SWORD", "DEBUG_STICK" ->
                {
                    return;
                }
            }

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
        Block block = player.getTargetBlock(null, 5);

        CommandMergeLog(player, block, "수정");
        CommandActionBarEvent.ActionBarCommand(player, block);
    }
    //endregion

    //region Log
    public static void CommandMergeLog(Player player, Block block, String text)
    {
        String playerName = player.getName();
        String blockName = block.getType().name();
        String worldName = player.getWorld().getName();
        Location pos = block.getLocation();

        String commandBlockText = "";
        if (GetCommandAuto(block))
        {
            switch (blockName)
            {
                case "COMMAND_BLOCK" -> commandBlockText = "항상 활성화된 명령 블록";
                case "CHAIN_COMMAND_BLOCK" -> commandBlockText = "항상 활성화된 연쇄형 명령 블록";
                case "REPEATING_COMMAND_BLOCK" -> commandBlockText = "항상 활성화된 반복형 명령 블록";
            }
        }
        else
        {
            commandBlockText = switch (blockName)
                    {
                        case "COMMAND_BLOCK" -> "명령 블록";
                        case "CHAIN_COMMAND_BLOCK" -> "연쇄형 명령 블록";
                        case "REPEATING_COMMAND_BLOCK" -> "반복형 명령 블록";
                        default -> commandBlockText;
                    };
        }

        String outText = playerName + "(" + player.getUniqueId() + ")" + "(이)가 " + worldName + " 월드에서 " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " 좌표에 " + commandBlockText + "을 " + text + " 했습니다";
        String command = GetCommand(block);
        if (!Objects.equals(command, ""))
            outText += "\n입력된 커맨드: " + command;
        else
            outText += "\n입력된 커맨드가 없습니다";

        Debug.Log(outText);
    }
    //endregion

    //region Convenience
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
    //endregion
}

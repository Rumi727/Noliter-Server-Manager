package kr.kro.teamdodoco.rumi.plugin.noliterservermanager.command;

import kr.kro.teamdodoco.rumi.plugin.noliterservermanager.Main;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class CommandMessage
{
    public static final String unknownCommand = "알 수 없는 명령어입니다 도움말을 보려면 \"/help\"를 입력하세요";
    public static final String unknownArgument = "명령어에 잘못된 인수가 있습니다";

    public static final String noPermission = "이 명령어를 사용할 권한이 없습니다";

    public static String InvalidBool(String value)
    {
        return "잘못된 불입니다, 'true' 또는 'false'가 필요하지만 '%s'이(가) 입력되었습니다".replace("%s", value);
    }

    public static String GetValue(String title, String label, String value)
    {
        return "%1$s은(는) %3$s이(가) %2$s입니다".replace("%1$s", title).replace("%3$s", label).replace("%2$s", value);
    }

    public static String SetValue(String title, String label, String value)
    {
        return "%2$s의 %1$s을(를) %3$s(으)로 설정했습니다".replace("%2$s", title).replace("%1$s", label).replace("%3$s", value);
    }

    public static void FailSendMessage(CommandSender sender, String message)
    {
        sender.sendMessage("§c" + message);
    }

    public static void SucSendMessage(CommandSender sender, String message, boolean force, boolean isGetMessage)
    {
        if (isGetMessage && !force)
        {
            sender.sendMessage(message);
            return;
        }

        String message2 = "§7§o[Noliter] [" + sender.getName() + ": " + message + "]";
        if (sender instanceof ConsoleCommandSender)
        {
            if (force || Boolean.TRUE.equals(Main.server.getWorlds().get(0).getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK)))
            {
                for (Player player : Main.server.getOnlinePlayers())
                    player.sendMessage("§7§o[Noliter] [Server: " + message + "]");
            }

            sender.sendMessage(message);
        }
        else if (sender instanceof BlockCommandSender)
        {
            BlockState blockState = ((BlockCommandSender)sender).getBlock().getState();
            World world = blockState.getWorld();

            if (blockState instanceof CommandBlock)
            {
                if (force || Boolean.TRUE.equals(world.getGameRuleValue(GameRule.COMMAND_BLOCK_OUTPUT)))
                    Broadcast(message2, world, force);
            }
            else
                Broadcast(message2, world, force);

            sender.sendMessage(message);
        }
        else if (sender instanceof Player senderPlayer)
        {
            World world = senderPlayer.getWorld();
            ConsoleSendMessage(message2, senderPlayer.getWorld(), force);

            if (force || Boolean.TRUE.equals(world.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK)))
            {
                for (Player player : Main.server.getOnlinePlayers())
                {
                    if (senderPlayer.getUniqueId().equals(player.getUniqueId()))
                        sender.sendMessage(message);
                    else
                        player.sendMessage(message2);
                }
            }
        }
        else
        {
            Broadcast(message2, null, true);
            sender.sendMessage(message);
        }
    }

    static void Broadcast(String message, World world, boolean force)
    {
        ConsoleSendMessage(message, world, force);

        if (force || Boolean.TRUE.equals(world.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK)))
        {
            for (Player player : Main.server.getOnlinePlayers())
                player.sendMessage(message);
        }
    }

    static void ConsoleSendMessage(String message, World world, boolean force)
    {
        if (force || Boolean.TRUE.equals(world.getGameRuleValue(GameRule.LOG_ADMIN_COMMANDS)))
            Main.server.getConsoleSender().sendMessage(message);
    }
}

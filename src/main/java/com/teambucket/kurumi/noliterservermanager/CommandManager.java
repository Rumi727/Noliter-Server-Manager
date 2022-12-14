package com.teambucket.kurumi.noliterservermanager;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabExecutor
{
    public static boolean allowOpSecurityCommand = false;
    public static final String securityLabel = "security";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if (args.length == 0)
        {
            CommandMessage.FailSendMessage(sender, CommandMessage.unknownCommand);
            return false;
        }

        String arg0 = args[0];
        if (label.equals(Main.commandLabel))
        {
            if (arg0.equals(securityLabel))
            {
                if (allowOpSecurityCommand || sender instanceof ConsoleCommandSender)
                {
                    if (args.length >= 2)
                    {
                        String arg1 = args[1];

                        //region allowBlockExplode
                        if (arg1.equals("allowBlockExplode"))
                        {
                            if (args.length >= 3)
                            {
                                String arg2 = args[2];
                                if (arg2.equals("true"))
                                {
                                    SecurityExplode.allowBlockExplode = true;
                                    CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(securityLabel, "allowBlockExplode", arg2), true, false);

                                    return true;
                                }
                                else if (arg2.equals("false"))
                                {
                                    SecurityExplode.allowBlockExplode = false;
                                    CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(securityLabel, "allowBlockExplode", arg2), true, false);

                                    return true;
                                }
                                else
                                {
                                    CommandMessage.FailSendMessage(sender, CommandMessage.InvalidBool(arg2));
                                    return false;
                                }
                            }
                            else
                            {
                                CommandMessage.SucSendMessage(sender, CommandMessage.GetValue(securityLabel, "allowBlockExplode", ((Boolean)SecurityExplode.allowBlockExplode).toString()), false, true);
                                return true;
                            }
                        }
                        //endregion
                        //region allowOpSecurityCommand
                        else if (arg1.equals("allowOpSecurityCommand"))
                        {
                            if (args.length >= 3)
                            {
                                String arg2 = args[2];
                                if (arg2.equals("true"))
                                {
                                    allowOpSecurityCommand = true;
                                    CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(securityLabel, "allowOpSecurityCommand", arg2), true, false);

                                    return true;
                                }
                                else if (arg2.equals("false"))
                                {
                                    allowOpSecurityCommand = false;
                                    CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(securityLabel, "allowOpSecurityCommand", arg2), true, false);

                                    return true;
                                }
                                else
                                {
                                    CommandMessage.FailSendMessage(sender, CommandMessage.InvalidBool(arg2));
                                    return false;
                                }
                            }
                            else
                            {
                                CommandMessage.SucSendMessage(sender, CommandMessage.GetValue(securityLabel, "allowBlockExplode", ((Boolean)allowOpSecurityCommand).toString()), false, true);
                                return true;
                            }
                        }
                        //endregion
                    }
                }
                else
                {
                    CommandMessage.FailSendMessage(sender,  "보안 명령어는 콘솔에서만 사용할 수 있습니다!");
                    return false;
                }
            }

            CommandMessage.FailSendMessage(sender, CommandMessage.unknownArgument);
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        ArrayList<String> list = new ArrayList<>();
        if (args.length >= 1)
        {
            list.add(securityLabel);

            if (label.equals(Main.commandLabel) && args.length >= 2)
            {
                list.clear();

                if (args[0].equals(securityLabel))
                {
                    list.add("allowBlockExplode");
                    list.add("allowOpSecurityCommand");

                    String arg1 = args[1];
                    if (arg1.equals("allowBlockExplode") || arg1.equals("allowOpSecurityCommand"))
                    {
                        list.clear();

                        if (args.length == 3)
                        {
                            list.add("true");
                            list.add("false");
                        }
                    }
                    else if (args.length >= 3)
                        list.clear();
                }
            }
        }
        else
            return null;

        return list;
    }
}

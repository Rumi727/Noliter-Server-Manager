package kr.kro.teambucket.kurumi.plugin.noliterservermanager.command;

import kr.kro.teambucket.kurumi.plugin.noliterservermanager.Main;
import kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience.DisableInvulnerableTimeEvent;
import kr.kro.teambucket.kurumi.plugin.noliterservermanager.security.ExplodeEvent;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class CommandManager implements CommandExecutor, TabExecutor
{
    public static boolean allowOpSecurityCommand = false;
    public static final String securityLabel = "security";
    public static final String convenienceLabel = "convenience";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if (args.length == 0)
        {
            CommandMessage.FailSendMessage(sender, CommandMessage.unknownCommand);
            return false;
        }

        boolean isConsole = sender instanceof ConsoleCommandSender;
        String arg0 = args[0];
        if (label.equals(Main.commandLabel))
        {
            if (arg0.equals(securityLabel))
            {
                if (allowOpSecurityCommand || isConsole)
                {
                    if (!isConsole && !sender.isOp())
                    {
                        CommandMessage.FailSendMessage(sender,  CommandMessage.noPermission);
                        return false;
                    }

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
                                    ExplodeEvent.allowBlockExplode = true;
                                    CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(securityLabel, "allowBlockExplode", arg2), true, false);

                                    return true;
                                }
                                else if (arg2.equals("false"))
                                {
                                    ExplodeEvent.allowBlockExplode = false;
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
                                CommandMessage.SucSendMessage(sender, CommandMessage.GetValue(securityLabel, "allowBlockExplode", ((Boolean)ExplodeEvent.allowBlockExplode).toString()), false, true);
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
            else if (arg0.equals(convenienceLabel))
            {
                if (args.length >= 2)
                {
                    String arg1 = args[1];

                    //region disablePlayerInvulnerableTime
                    if (arg1.equals("disablePlayerInvulnerableTime"))
                    {
                        if (args.length >= 3)
                        {
                            String arg2 = args[2];
                            if (arg2.equals("true"))
                            {
                                DisableInvulnerableTimeEvent.disablePlayerInvulnerableTime = true;
                                CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(convenienceLabel, "disablePlayerInvulnerableTime", arg2), true, false);

                                return true;
                            }
                            else if (arg2.equals("false"))
                            {
                                DisableInvulnerableTimeEvent.disablePlayerInvulnerableTime = false;
                                CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(convenienceLabel, "disablePlayerInvulnerableTime", arg2), true, false);

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
                            CommandMessage.SucSendMessage(sender, CommandMessage.GetValue(convenienceLabel, "disablePlayerInvulnerableTime", ((Boolean)DisableInvulnerableTimeEvent.disablePlayerInvulnerableTime).toString()), false, true);
                            return true;
                        }
                    }
                    //endregion
                    //region disableMobInvulnerableTime
                    else if (arg1.equals("disableMobInvulnerableTime"))
                    {
                        if (args.length >= 3)
                        {
                            String arg2 = args[2];
                            if (arg2.equals("true"))
                            {
                                DisableInvulnerableTimeEvent.disableMobInvulnerableTime = true;
                                CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(convenienceLabel, "disableMobInvulnerableTime", arg2), true, false);

                                return true;
                            }
                            else if (arg2.equals("false"))
                            {
                                DisableInvulnerableTimeEvent.disableMobInvulnerableTime = false;
                                CommandMessage.SucSendMessage(sender, CommandMessage.SetValue(convenienceLabel, "disableMobInvulnerableTime", arg2), true, false);

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
                            CommandMessage.SucSendMessage(sender, CommandMessage.GetValue(convenienceLabel, "disableMobInvulnerableTime", ((Boolean)DisableInvulnerableTimeEvent.disableMobInvulnerableTime).toString()), false, true);
                            return true;
                        }
                    }
                    //endregion
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
            list.add(convenienceLabel);

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
                else if (args[0].equals(convenienceLabel))
                {
                    list.add("disablePlayerInvulnerableTime");
                    list.add("disableMobInvulnerableTime");

                    String arg1 = args[1];
                    if (arg1.equals("disablePlayerInvulnerableTime") || arg1.equals("disableMobInvulnerableTime"))
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

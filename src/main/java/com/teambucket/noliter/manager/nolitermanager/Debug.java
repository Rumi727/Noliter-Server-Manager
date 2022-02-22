package com.teambucket.noliter.manager.nolitermanager;

public class Debug
{
    public static void Log(String message)
    {
        Main.debug.info("[NoliterManager] " + message);
    }

    public static void LogWarning(String message)
    {
        Main.debug.warning("[NoliterManager] " + message);
    }
}

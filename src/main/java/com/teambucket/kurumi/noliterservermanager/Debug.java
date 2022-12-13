package com.teambucket.kurumi.noliterservermanager;

public class Debug
{
    public static void Log(String message) { Main.debug.info("[Noliter Server Manager] " + message); }

    public static void LogNoTitle(String message) { Main.debug.info(message); }
}

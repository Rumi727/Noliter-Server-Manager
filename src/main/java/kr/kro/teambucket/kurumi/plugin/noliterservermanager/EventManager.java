package kr.kro.teambucket.kurumi.plugin.noliterservermanager;

import kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience.*;
import kr.kro.teambucket.kurumi.plugin.noliterservermanager.security.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public final class EventManager
{
    public static void Init(Plugin plugin, PluginManager pluginManager)
    {
        //region Security
        pluginManager.registerEvents(new CommandBlockEvent(), plugin);
        pluginManager.registerEvents(new ExplodeEvent(), plugin);
        pluginManager.registerEvents(new LogEvent(), plugin);
        //endregion

        //region Convenience
        pluginManager.registerEvents(new LeftClickEvent(), plugin);
        pluginManager.registerEvents(new RightClickEvent(), plugin);

        pluginManager.registerEvents(new CommandActionBarEvent(), plugin);
        pluginManager.registerEvents(new EntityDamageEffectEvent(), plugin);
        pluginManager.registerEvents(new FireChargeThrowEvent(), plugin);
        pluginManager.registerEvents(new NoteBlockDelayEvent(), plugin);
        pluginManager.registerEvents(new SlimeBallThrowEvent(), plugin);
        //endregion
    }
}

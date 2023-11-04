package kr.kro.teamdodoco.rumi.plugin.noliterservermanager.convenience;

import kr.kro.teamdodoco.rumi.plugin.noliterservermanager.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public final class LeftClickEvent implements Listener
{
    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.isLeftClick())
        {
            Objective objective = Main.mainScoreboard.getObjective("leftClick");
            if (objective == null)
                objective = Main.mainScoreboard.registerNewObjective("leftClick", Criteria.DUMMY, Component.text("leftClick"));

            Objective cancel = Main.mainScoreboard.getObjective("leftClickEventCancel");
            if (cancel == null)
                Main.mainScoreboard.registerNewObjective("leftClickEventCancel", Criteria.DUMMY, Component.text("leftClickEventCancel"));
            else if (cancel.getScore(player).getScore() == 1)
                event.setCancelled(true);

            Score score = objective.getScore(player);
            score.setScore(score.getScore() + 1);
        }
    }
}

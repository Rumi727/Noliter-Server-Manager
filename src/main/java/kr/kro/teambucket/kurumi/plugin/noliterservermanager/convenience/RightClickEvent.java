package kr.kro.teambucket.kurumi.plugin.noliterservermanager.convenience;

import kr.kro.teambucket.kurumi.plugin.noliterservermanager.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public final class RightClickEvent implements Listener
{
    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.isRightClick())
        {
            Objective objective = Main.mainScoreboard.getObjective("rightClick");
            if (objective == null)
                objective = Main.mainScoreboard.registerNewObjective("rightClick", Criteria.DUMMY, Component.text("rightClick"));

            Objective cancel = Main.mainScoreboard.getObjective("rightClickEventCancel");
            if (cancel == null)
                Main.mainScoreboard.registerNewObjective("rightClickEventCancel", Criteria.DUMMY, Component.text("rightClickEventCancel"));
            else if (cancel.getScore(player).getScore() == 1)
                event.setCancelled(true);

            Score score = objective.getScore(player);
            score.setScore(score.getScore() + 1);
        }
    }
}

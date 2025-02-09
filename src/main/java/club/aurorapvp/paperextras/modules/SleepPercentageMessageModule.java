package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.Config;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;
import club.aurorapvp.paperextras.PaperExtras;

import java.util.List;

/**
 * If enabled, sends messages in chat containing amount of players required to sleep based on
 * playersSleepingPercentage gamerule.
 */
public class SleepPercentageMessageModule implements PaperExtrasModule, Listener {
  private final MiniMessage miniMsg = PaperExtras.getInstance().miniMessage;
  private final String playerSleepMessage, nightSkipMessage;
  private final String sleepMessageBypass = "paperextras.sleepmessagebypass";

  protected SleepPercentageMessageModule() {
    Config config = PaperExtras.getPluginConfig();
    this.playerSleepMessage =
        config.getString(
            "settings.chat.send-sleep-percentage-message.player-sleeping",
            "<grey><playername> has fallen asleep. <sleeping> out of <needed> required players in <worldname> are sleeping.");
    this.nightSkipMessage =
        config.getString(
            "settings.chat.send-sleep-percentage-message.skipping-night",
            "<grey>Enough players have slept! Skipping through the night in <worldname>.");
  }

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public boolean shouldEnable() {
    DefaultPermissions.registerPermission(
        sleepMessageBypass,
        "Allows player to not display a message in chat when they sleep",
        PermissionDefault.OP);
    if ((playerSleepMessage == null || playerSleepMessage.isBlank())
        && (nightSkipMessage == null || nightSkipMessage.isBlank())) return false;
    return PaperExtras.getPluginConfig()
        .getBoolean("settings.chat.send-sleep-percentage-message.enabled", false);
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onPlayerDeepSleep(PlayerDeepSleepEvent event) {
    if (playerSleepMessage == null || playerSleepMessage.isBlank()) return;
    if (event.getPlayer().hasPermission(sleepMessageBypass)) return;
    World world = event.getPlayer().getWorld();
    List<Player> playerList = world.getPlayers();
    String playerName = event.getPlayer().getName();
    String worldName = world.getName();
    int currentSleepCount = 0;
    int worldOnlineTotal = playerList.size();
    Integer worldSleepPercent = world.getGameRuleValue(GameRule.PLAYERS_SLEEPING_PERCENTAGE);
    Integer neededSleepers = (int) Math.ceil((worldSleepPercent / 100.0) * worldOnlineTotal);
    for (Player player : playerList) {
      if (player.isDeeplySleeping()) currentSleepCount += 1;
    }
    world.sendMessage(
        miniMsg.deserialize(
            playerSleepMessage,
            Placeholder.unparsed("playername", playerName),
            Placeholder.unparsed("sleeping", String.valueOf(currentSleepCount)),
            Placeholder.unparsed("needed", String.valueOf(neededSleepers)),
            Placeholder.unparsed("worldname", worldName)));
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void nightSkip(TimeSkipEvent event) {
    if (!event.getSkipReason().equals(TimeSkipEvent.SkipReason.NIGHT_SKIP)) return;
    if (nightSkipMessage == null || nightSkipMessage.isBlank()) return;
    String worldName = event.getWorld().getName();
    event
        .getWorld()
        .sendMessage(
            miniMsg.deserialize(nightSkipMessage, Placeholder.unparsed("worldname", worldName)));
  }
}

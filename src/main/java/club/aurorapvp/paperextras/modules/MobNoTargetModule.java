package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

/**
 * If enabled, players having target.bypass.<mojang_mob_name> permission won't be targetted by that
 * type of mob.
 */
public class MobNoTargetModule implements PaperExtrasModule, Listener {

  protected MobNoTargetModule() {}

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public boolean shouldEnable() {
    return PaperExtras.getPluginConfig().getBoolean("settings.use-notarget-permissions", false);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onMobTarget(EntityTargetEvent event) {
    if (!(event.getTarget() instanceof Player player)) return;
    if (!player.hasPermission("target.bypass." + event.getEntityType().getKey().getKey())) return;
    event.setCancelled(true);
  }
}

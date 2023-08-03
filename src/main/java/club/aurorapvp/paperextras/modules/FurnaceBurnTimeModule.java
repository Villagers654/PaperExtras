package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;

/** If enabled, multiplier field will be used to modify fuel burn time in furnaces. */
public class FurnaceBurnTimeModule implements PaperExtrasModule, Listener {

  private final double furnaceBurnTimeMultiplier;

  protected FurnaceBurnTimeModule() {
    furnaceBurnTimeMultiplier =
        PaperExtras.getPluginConfig().getDouble("settings.furnace.burn-time.multiplier", 1.0);
  }

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public boolean shouldEnable() {
    if (!PaperExtras.getPluginConfig().getBoolean("settings.furnace.burn-time.enabled", false))
      return false;
    return furnaceBurnTimeMultiplier != 1.0;
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onFurnaceBurn(FurnaceBurnEvent event) {
    int burnTime = event.getBurnTime();
    event.setBurnTime((int) (burnTime * furnaceBurnTimeMultiplier));
  }
}

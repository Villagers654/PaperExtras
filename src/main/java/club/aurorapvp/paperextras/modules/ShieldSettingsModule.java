package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import club.aurorapvp.paperextras.modules.listeners.ShieldCooldownListener;
import club.aurorapvp.paperextras.modules.listeners.ShieldDamageReductionListener;
import org.bukkit.event.Listener;

/**
 * Shield configurations {@link ShieldCooldownListener} - Amount of ticks (1/20th of a second) of
 * cooldown for a shield after hitting it with an axe crit. By default this is 100 ticks (5
 * seconds).
 *
 * <p>{@link ShieldDamageReductionListener} - Value between 0 and 1. This is the percentage of
 * damage reduction that defending with a shield will provide. By default shield reduces 100% of the
 * damage (1.0).
 */
public class ShieldSettingsModule implements PaperExtrasModule, Listener {

  private static final double DEFAULT_SHIELD_DAMAGE_REDUCTION = 1.0;
  private static final int DEFAULT_SHIELD_COOLDOWN = 5 * 20;

  private double shieldDamageReduction = DEFAULT_SHIELD_DAMAGE_REDUCTION;
  private int shieldCooldown = DEFAULT_SHIELD_COOLDOWN;

  protected ShieldSettingsModule() {}

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    if (shieldDamageReduction != DEFAULT_SHIELD_DAMAGE_REDUCTION) {
      plugin
          .getServer()
          .getPluginManager()
          .registerEvents(new ShieldDamageReductionListener(shieldDamageReduction), plugin);
    }
    if (shieldCooldown != DEFAULT_SHIELD_COOLDOWN) {
      plugin
          .getServer()
          .getPluginManager()
          .registerEvents(new ShieldCooldownListener(shieldCooldown), plugin);
    }
  }

  @Override
  public boolean shouldEnable() {
    shieldDamageReduction =
        PaperExtras.getPluginConfig()
            .getDouble("settings.shield.damage-reduction", shieldDamageReduction);
    shieldDamageReduction = Math.min(1.0, Math.max(0, shieldDamageReduction));

    shieldCooldown =
        PaperExtras.getPluginConfig().getInt("settings.shield.cooldown", shieldCooldown);
    shieldCooldown = Math.max(0, shieldCooldown);

    return shieldDamageReduction != DEFAULT_SHIELD_DAMAGE_REDUCTION
        || shieldCooldown != DEFAULT_SHIELD_COOLDOWN;
  }
}

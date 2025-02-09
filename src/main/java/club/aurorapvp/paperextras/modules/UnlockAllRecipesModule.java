package club.aurorapvp.paperextras.modules;

import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;
import club.aurorapvp.paperextras.PaperExtras;

import java.util.Iterator;

/**
 * Unlocks all available recipes on join. Players can be exempt from this by denying them
 * paperextras.unlockallrecipesonjoin permission.
 */
public class UnlockAllRecipesModule implements PaperExtrasModule, Listener {

  private final String permission = "paperextras.unlockallrecipesonjoin";

  protected UnlockAllRecipesModule() {}

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public boolean shouldEnable() {
    DefaultPermissions.registerPermission(
        permission,
        "Players with this permission will have all recipes unlocked upon login if that feature is enabled in the config",
        PermissionDefault.TRUE);
    return PaperExtras.getPluginConfig().getBoolean("settings.unlock-all-recipes-on-join", false);
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onPlayerJoinRecipeUnlock(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!player.hasPermission(permission)) return;
    Iterator<Recipe> recipes = Bukkit.recipeIterator();
    while (recipes.hasNext()) {
      Recipe recipe = recipes.next();
      if (!(recipe instanceof Keyed keyedRecipe)) continue;
      player.discoverRecipe(keyedRecipe.getKey());
    }
  }
}

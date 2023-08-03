package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.Config;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;
import club.aurorapvp.paperextras.PaperExtras;

/**
 * Adds a build height limit to the nether Configuration:
 *
 * <p>**enabled** Enables the feature.
 *
 * <p>**height-limit** Maximum height players without paperextras.netherbuildheightbypass permission
 * can build in nether worlds.
 *
 * <p>**no-permission-message** Message to display in action bar when trying to build above set
 * limit in nether worlds.
 */
public class NetherBuildHeightModule implements PaperExtrasModule, Listener {

  private final int configBuildHeight;
  private final String noPermissionMessageContent;
  private final String netherBuildHeightBypassPermission = "paperextras.netherbuildheightbypass";

  protected NetherBuildHeightModule() {
    Config config = PaperExtras.getPluginConfig();
    this.configBuildHeight =
        config.getInt("settings.block-building-above-nether.height-limit", 128);
    this.noPermissionMessageContent =
        config.getString(
            "settings.block-building-above-nether.no-permission-message",
            "<red>Max build height in this world is: <gold><height>");
  }

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public boolean shouldEnable() {
    DefaultPermissions.registerPermission(
        netherBuildHeightBypassPermission,
        "Allows player to bypass the configured max nether build height",
        PermissionDefault.OP);
    return PaperExtras.getPluginConfig()
        .getBoolean("settings.block-building-above-nether.enabled", false);
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onNetherRoofBuild(BlockPlaceEvent event) {
    Block block = event.getBlock();
    Player player = event.getPlayer();
    if (!(block.getWorld().getEnvironment().equals(World.Environment.NETHER))) return;
    if (block.getLocation().getBlockY() < configBuildHeight) return;
    if (player.hasPermission(netherBuildHeightBypassPermission)) return;
    if (!"".equals(noPermissionMessageContent)) {
      player.sendActionBar(
          PaperExtras.getInstance()
              .miniMessage
              .deserialize(
                  noPermissionMessageContent,
                  Placeholder.unparsed("height", String.valueOf(configBuildHeight))));
    }
    event.setCancelled(true);
  }
}

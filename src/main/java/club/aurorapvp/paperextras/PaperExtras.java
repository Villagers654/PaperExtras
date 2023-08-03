package club.aurorapvp.paperextras;

import club.aurorapvp.paperextras.modules.PaperExtrasModule;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperExtras extends JavaPlugin {

  private static Config config;
  private static PaperExtras instance;
  public final MiniMessage miniMessage = MiniMessage.miniMessage();

  @Override
  public void onEnable() {
    instance = this;
    config = new Config();

    PluginCommand command = getCommand("paperextras");
    if (command != null) {
      PaperExtrasCommand cmd = new PaperExtrasCommand();
      command.setExecutor(cmd);
      command.setTabCompleter(cmd);
    }

    PaperExtrasModule.reloadModules();
    config.saveConfig();
  }

  public static Config getPluginConfig() {
    return config;
  }

  public static PaperExtras getInstance() {
    return instance;
  }

  void reloadConfig(CommandSender commandSender) {
    Bukkit.getScheduler()
        .runTaskAsynchronously(
            this,
            () -> {
              config = new Config();
              PaperExtrasModule.reloadModules();
              config.saveConfig();
              commandSender.sendMessage(Component.text("PaperExtras configuration reloaded!"));
            });
  }

  public static NamespacedKey key(String string) {
    return new NamespacedKey(PaperExtras.getInstance(), string);
  }
}

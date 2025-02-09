package club.aurorapvp.paperextras.modules;

import io.papermc.paper.event.player.AsyncChatEvent;
import club.aurorapvp.paperextras.PaperExtras;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Allows players to send a message with a slash at the start by escaping it with backslash
 * (\/command that will appear as /command in chat).
 */
public class EscapeCommandSlashModule implements PaperExtrasModule, Listener {

  protected EscapeCommandSlashModule() {}

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public boolean shouldEnable() {
    return PaperExtras.getPluginConfig().getBoolean("settings.chat.escape-commands", false);
  }

  @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
  public void onCommandEscape(AsyncChatEvent event) {
    String message = PlainTextComponentSerializer.plainText().serialize(event.message());
    String[] messageSplit = message.split(" ");
    String command = messageSplit[0].substring(1);
    Component component =
        event
            .message()
            .replaceText(
                TextReplacementConfig.builder()
                    .match("(\\\\/\\S*)")
                    .replacement(command)
                    .once()
                    .build());
    event.message(component);
  }
}

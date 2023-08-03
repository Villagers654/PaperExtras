package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.GrindstoneInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * If enabled and player has books in their inventory while disenchanting item in a grindstone,
 * books will be consumed to return the enchantments removed from the item to the player. No exp
 * will drop when doing this.
 */
public class GrindstoneEnchantsBooksModule implements PaperExtrasModule, Listener {

  private final Set<Player> grindstoneUsers = new HashSet<>();
  private final ItemStack BOOK = new ItemStack(Material.BOOK);

  protected GrindstoneEnchantsBooksModule() {}

  @Override
  public void enable() {
    PaperExtras plugin = PaperExtras.getInstance();
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @Override
  public boolean shouldEnable() {
    return PaperExtras.getPluginConfig()
        .getBoolean("settings.grindstone.gives-enchants-back", false);
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!(event.getInventory() instanceof GrindstoneInventory grindstone)) {
      return; // not a grindstone, do nothing
    }

    // Check if the clicked slot is the result slot
    if (event.getSlotType() != InventoryType.SlotType.RESULT) {
      return; // not the result slot, do nothing
    }

    if (!(event.getWhoClicked() instanceof Player player)) {
      return;
    }

    grindstoneUsers.add(player);

    handleItemMove(grindstone, player);
  }

  @EventHandler
  public void onInventoryDrag(InventoryDragEvent event) {
    if (!(event.getInventory() instanceof GrindstoneInventory grindstone)) {
      return; // not a grindstone, do nothing
    }

    // Check if any of the slots included in the drag are the result slot
    if (!event.getInventorySlots().contains(2)) {
      return; // result slot not included in the drag, do nothing
    }

    if (!(event.getWhoClicked() instanceof Player player)) {
      return;
    }

    grindstoneUsers.add(player);

    handleItemMove(grindstone, player);
  }

  @EventHandler
  public void onPlayerExpChange(PlayerExpChangeEvent event) {
    Player player = event.getPlayer();

    // If the player is in the set, set the experience amount to 0 and remove them from the set
    if (grindstoneUsers.remove(player)) {
      event.setAmount(0);
    }
  }

  public void handleItemMove(GrindstoneInventory grindstoneInventory, Player player) {
    ItemStack lowerItem = grindstoneInventory.getLowerItem();
    if (lowerItem != null && !lowerItem.getType().isEmpty()) {
      return; // lower slot is not empty, do nothing
    }

    ItemStack upperItem = grindstoneInventory.getUpperItem();
    if (upperItem == null || upperItem.getType().isEmpty()) {
      return; // upper slot is empty, do nothing
    }

    Map<Enchantment, Integer> enchants;
    if (upperItem.getType() == Material.ENCHANTED_BOOK) {
      if (!upperItem.hasItemMeta()) {
        return;
      }
      enchants = ((EnchantmentStorageMeta) upperItem.getItemMeta()).getStoredEnchants();
    } else {
      if (!upperItem.getItemMeta().hasEnchants()) {
        return;
      }
      enchants = upperItem.getItemMeta().getEnchants();
    }

    if (enchants.isEmpty()) {
      return;
    }

    Location location = player.getLocation();
    World world = location.getWorld();
    PlayerInventory playerInventory = player.getInventory();

    for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
      if (entry.getKey().isCursed()) {
        continue; // grindstones don't remove curses
      }
      if (player.getGameMode() != GameMode.CREATIVE) {
        if (!playerInventory.containsAtLeast(BOOK, 1)) {
          return; // no more books to extract to
        }

        if (!playerInventory.removeItem(BOOK).isEmpty()) {
          return; // could not remove book
        }
      }

      ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
      EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
      meta.addStoredEnchant(entry.getKey(), entry.getValue(), true);
      book.setItemMeta(meta);

      playerInventory
          .addItem(book)
          .forEach(
              (index, stack) -> {
                Item drop = world.dropItemNaturally(location, stack);
                drop.setPickupDelay(0);
              });
    }
  }
}

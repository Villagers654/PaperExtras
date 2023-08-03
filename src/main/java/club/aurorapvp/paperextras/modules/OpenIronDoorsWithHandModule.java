package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.Config;
import club.aurorapvp.paperextras.PaperExtras;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Allows opening iron doors and trapdoors with a hand, just like wooden doors.
 */
public class OpenIronDoorsWithHandModule implements PaperExtrasModule, Listener {

    private final boolean doors, trapdoors;

    protected OpenIronDoorsWithHandModule() {
        Config config = PaperExtras.getPluginConfig();
        doors = config.getBoolean("settings.gameplay-settings.open-iron-doors-with-hand", false);
        trapdoors = config.getBoolean("settings.gameplay-settings.open-iron-trapdoors-with-hand", false);
    }
    @Override
    public void enable() {
        PaperExtras plugin = PaperExtras.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean shouldEnable() {
        return doors || trapdoors;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onIronDoorOpen(PlayerInteractEvent event) {
        if (!EquipmentSlot.HAND.equals(event.getHand())) return;
        if (event.getPlayer().isSneaking()) return;
        if (!event.getAction().isRightClick()) return;
        if (event.getClickedBlock() == null) return;
        Block block = event.getClickedBlock();
        World world = block.getWorld();
        switch (block.getType()) {
            case IRON_DOOR -> {
                if (!doors) return;
                event.setCancelled(true);
                if (open(block, event.getPlayer())) {
                    world.playSound(block.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1f, 1f);
                } else {
                    world.playSound(block.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1f, 1f);
                }
            }
            case IRON_TRAPDOOR -> {
                if (!trapdoors) return;
                event.setCancelled(true);
                if (open(block, event.getPlayer())) {
                    world.playSound(block.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1f, 1f);
                } else {
                    world.playSound(block.getLocation(), Sound.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1f, 1f);
                }
            }
        }
    }

    private boolean open(Block block, Player player) {
        Openable openable = (Openable) block.getBlockData();
        boolean isOpen = openable.isOpen();
        openable.setOpen(!isOpen);
        block.setBlockData(openable);
        player.swingMainHand();
        return !isOpen;
    }


}

package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import club.aurorapvp.paperextras.util.RecipeUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.SmithingTransformRecipe;

/** Allows upgrading tools from wood to stone in the smithing table */
public class UpgradeWoodToStoneToolsModule implements PaperExtrasModule {

  protected UpgradeWoodToStoneToolsModule() {}

  @Override
  public void enable() {
    SmithingTransformRecipe recipe;
    recipe =
        new SmithingTransformRecipe(
            PaperExtras.key("pick_wood_to_stone"), // key
            new ItemStack(Material.STONE_PICKAXE), // result
            new RecipeChoice.MaterialChoice(Material.WOODEN_PICKAXE), // template
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE), // base
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE) // addition
            );
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingTransformRecipe(
            PaperExtras.key("axe_wood_to_stone"), // key
            new ItemStack(Material.STONE_AXE), // result
            new RecipeChoice.MaterialChoice(Material.WOODEN_AXE), // template
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE), // base
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE) // addition
            );
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingTransformRecipe(
            PaperExtras.key("shovel_wood_to_stone"), // key
            new ItemStack(Material.STONE_SHOVEL), // result
            new RecipeChoice.MaterialChoice(Material.WOODEN_SHOVEL), // template
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE), // base
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE) // addition
            );
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingTransformRecipe(
            PaperExtras.key("hoe_wood_to_stone"), // key
            new ItemStack(Material.STONE_HOE), // result
            new RecipeChoice.MaterialChoice(Material.WOODEN_HOE), // template
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE), // base
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE) // addition
            );
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingTransformRecipe(
            PaperExtras.key("sword_wood_to_stone"), // key
            new ItemStack(Material.STONE_SWORD), // result
            new RecipeChoice.MaterialChoice(Material.WOODEN_SWORD), // template
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE), // base
            new RecipeChoice.MaterialChoice(Material.COBBLESTONE, Material.BLACKSTONE) // addition
            );
    RecipeUtil.addSmithingRecipe(recipe);
  }

  @Override
  public boolean shouldEnable() {
    boolean shouldEnable =
        PaperExtras.getPluginConfig()
            .getBoolean("settings.smithing-table.tools.wood-to-stone", false);
    if (shouldEnable) return true;
    RecipeUtil.removeRecipe("sword_wood_to_stone");
    RecipeUtil.removeRecipe("pick_wood_to_stone");
    RecipeUtil.removeRecipe("axe_wood_to_stone");
    RecipeUtil.removeRecipe("shovel_wood_to_stone");
    RecipeUtil.removeRecipe("hoe_wood_to_stone");
    return false;
  }
}

package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import club.aurorapvp.paperextras.util.RecipeUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

/** Allows upgrading tools from iron to diamond in the smithing table */
public class UpgradeIronToDiamondsToolsModule implements PaperExtrasModule {

  protected UpgradeIronToDiamondsToolsModule() {}

  @Override
  public void enable() {
    SmithingRecipe recipe;
    recipe =
        new SmithingRecipe(
            PaperExtras.key("pick_iron_to_diamond"),
            new ItemStack(Material.DIAMOND_PICKAXE),
            new RecipeChoice.MaterialChoice(Material.IRON_PICKAXE),
            new RecipeChoice.MaterialChoice(Material.DIAMOND),
            true);
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingRecipe(
            PaperExtras.key("axe_iron_to_diamond"),
            new ItemStack(Material.DIAMOND_AXE),
            new RecipeChoice.MaterialChoice(Material.IRON_AXE),
            new RecipeChoice.MaterialChoice(Material.DIAMOND),
            true);
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingRecipe(
            PaperExtras.key("shovel_iron_to_diamond"),
            new ItemStack(Material.DIAMOND_SHOVEL),
            new RecipeChoice.MaterialChoice(Material.IRON_SHOVEL),
            new RecipeChoice.MaterialChoice(Material.DIAMOND),
            true);
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingRecipe(
            PaperExtras.key("hoe_iron_to_diamond"),
            new ItemStack(Material.DIAMOND_HOE),
            new RecipeChoice.MaterialChoice(Material.IRON_HOE),
            new RecipeChoice.MaterialChoice(Material.DIAMOND),
            true);
    RecipeUtil.addSmithingRecipe(recipe);
    recipe =
        new SmithingRecipe(
            PaperExtras.key("sword_iron_to_diamond"),
            new ItemStack(Material.DIAMOND_SWORD),
            new RecipeChoice.MaterialChoice(Material.IRON_SWORD),
            new RecipeChoice.MaterialChoice(Material.DIAMOND),
            true);
    RecipeUtil.addSmithingRecipe(recipe);
  }

  @Override
  public boolean shouldEnable() {
    boolean shouldEnable =
        PaperExtras.getPluginConfig()
            .getBoolean("settings.smithing-table.tools.iron-to-diamond", false);
    if (shouldEnable) return true;
    RecipeUtil.removeRecipe("sword_iron_to_diamond");
    RecipeUtil.removeRecipe("pick_iron_to_diamond");
    RecipeUtil.removeRecipe("axe_iron_to_diamond");
    RecipeUtil.removeRecipe("shovel_iron_to_diamond");
    RecipeUtil.removeRecipe("hoe_iron_to_diamond");
    return false;
  }
}

package club.aurorapvp.paperextras.modules;

import club.aurorapvp.paperextras.PaperExtras;
import club.aurorapvp.paperextras.util.RecipeUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.SmithingTransformRecipe;

/**
 * Allows upgrading tools from iron to diamond in the smithing table
 */
public class UpgradeIronToDiamondsToolsModule implements PaperExtrasModule {

    protected UpgradeIronToDiamondsToolsModule() {
    }

    @Override
    public void enable() {
        SmithingTransformRecipe recipe;
        recipe =
                new SmithingTransformRecipe(
                        PaperExtras.key("pick_iron_to_diamond"), // key
                        new ItemStack(Material.DIAMOND_PICKAXE), // result
                        new RecipeChoice.MaterialChoice(Material.IRON_PICKAXE), // template
                        new RecipeChoice.MaterialChoice(Material.IRON_PICKAXE), // base
                        new RecipeChoice.MaterialChoice(Material.DIAMOND) // addition
                );
        RecipeUtil.addSmithingRecipe(recipe);
        recipe =
                new SmithingTransformRecipe(
                        PaperExtras.key("axe_iron_to_diamond"), // key
                        new ItemStack(Material.DIAMOND_AXE), // result
                        new RecipeChoice.MaterialChoice(Material.IRON_AXE), // template
                        new RecipeChoice.MaterialChoice(Material.IRON_AXE), // base
                        new RecipeChoice.MaterialChoice(Material.DIAMOND) // addition
                );
        RecipeUtil.addSmithingRecipe(recipe);
        recipe =
                new SmithingTransformRecipe(
                        PaperExtras.key("shovel_iron_to_diamond"), // key
                        new ItemStack(Material.DIAMOND_SHOVEL), // result
                        new RecipeChoice.MaterialChoice(Material.IRON_SHOVEL), // template
                        new RecipeChoice.MaterialChoice(Material.IRON_SHOVEL), // base
                        new RecipeChoice.MaterialChoice(Material.DIAMOND) // addition
                );
        RecipeUtil.addSmithingRecipe(recipe);
        recipe =
                new SmithingTransformRecipe(
                        PaperExtras.key("hoe_iron_to_diamond"), // key
                        new ItemStack(Material.DIAMOND_HOE), // result
                        new RecipeChoice.MaterialChoice(Material.IRON_HOE), // template
                        new RecipeChoice.MaterialChoice(Material.IRON_HOE), // base
                        new RecipeChoice.MaterialChoice(Material.DIAMOND) // addition
                );
        RecipeUtil.addSmithingRecipe(recipe);
        recipe =
                new SmithingTransformRecipe(
                        PaperExtras.key("sword_iron_to_diamond"), // key
                        new ItemStack(Material.DIAMOND_SWORD), // result
                        new RecipeChoice.MaterialChoice(Material.IRON_SWORD), // template
                        new RecipeChoice.MaterialChoice(Material.IRON_SWORD), // base
                        new RecipeChoice.MaterialChoice(Material.DIAMOND) // addition
                );
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

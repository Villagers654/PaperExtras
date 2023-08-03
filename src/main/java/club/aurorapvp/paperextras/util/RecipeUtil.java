package club.aurorapvp.paperextras.util;

import club.aurorapvp.paperextras.PaperExtras;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.SmithingRecipe;

public class RecipeUtil {

    public static void addSmithingRecipe(SmithingRecipe recipe) {
        if (Bukkit.getRecipe(recipe.getKey()) != null) return;
        Bukkit.getScheduler().runTask(PaperExtras.getInstance(), () -> Bukkit.addRecipe(recipe));
    }

    public static void removeRecipe(String key) {
        Bukkit.getScheduler().runTask(PaperExtras.getInstance(), () -> Bukkit.removeRecipe(new NamespacedKey(PaperExtras.getInstance(), key)));
    }

}

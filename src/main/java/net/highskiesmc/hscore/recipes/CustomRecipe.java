package net.highskiesmc.hscore.recipes;

import net.highskiesmc.hscore.highskies.HSPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class CustomRecipe implements Listener {

    private HSPlugin plugin;
    private ItemStack result, required;
    private ItemStack[] recipe;
    private int amount;

    public CustomRecipe(HSPlugin plugin) {
        this.plugin = plugin;
    }

    public CustomRecipe(HSPlugin plugin, ItemStack result, ItemStack[] recipe) {
        this.plugin = plugin;
        this.result = result;
        this.required = null;
        this.recipe = recipe;
        this.amount = 0;
    }

    public CustomRecipe(HSPlugin plugin, ItemStack result, ItemStack required, int amount) {
        this.plugin = plugin;
        this.result = result;
        this.required = required;
        this.recipe = null;
        this.amount = amount;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void craftItemEvent(CraftItemEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final CraftingInventory i = event.getInventory();
        final ItemStack r = event.getRecipe().getResult();
        if (r.isSimilar(result)) {
            int A = this.amount;
            final ItemStack[] m = i.getMatrix();
            final boolean shapeless = recipe == null;
            int amount = 0;
            for (int a = 0; a < m.length; a++) {
                final ItemStack is = m[a];
                if (is != null) {
                    if (!shapeless && !is.isSimilar(recipe[a])) {
                        event.setCancelled(true);
                        player.updateInventory();
                        return;
                    } else if (shapeless) {
                        if (is.isSimilar(required)) {
                            amount += is.getAmount();
                        } else {
                            event.setCancelled(true);
                            player.updateInventory();
                            return;
                        }
                    }
                }
            }
            if (shapeless && amount < A) {
                event.setCancelled(true);
                player.updateInventory();
            } else {
                int index = 0, removed = 0;
                if (event.isShiftClick()) {
                    event.setCancelled(true);
                    final int am = amount / this.amount;
                    ItemStack item = r.clone();
                    item.setAmount(am);
                    //giveItem(player, item);
                    A = am * this.amount + 1;
                }
                final ItemStack[] mat = m.clone();
                for (ItemStack is : mat) {
                    if (removed != A) {
                        for (int z = 1; z < A; z++) {
                            if (is != null && removed != A) {
                                final int am = is.getAmount() - 1;
                                if (am == 0) {
                                    mat[index] = null;
                                } else {
                                    is.setAmount(am);
                                }
                                removed++;
                            }
                        }
                    }
                    index++;
                }
                i.setMatrix(mat);
                player.updateInventory();
            }
        }
    }
}

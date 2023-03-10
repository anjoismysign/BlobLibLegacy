package us.mytheria.blobliblegacy.entities.inventory;

import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;

public abstract class ButtonManager implements ButtonManagerMethods {
    private Map<String, Set<Integer>> stringKeys;
    private Map<Integer, ItemStack> integerKeys;

    public ButtonManager(Map<String, Set<Integer>> stringKeys,
                         Map<Integer, ItemStack> integerKeys) {
        this.stringKeys = stringKeys;
        this.integerKeys = integerKeys;
    }

    public ButtonManager() {
    }

    public Map<Integer, ItemStack> getIntegerKeys() {
        return integerKeys;
    }

    public Map<String, Set<Integer>> getStringKeys() {
        return stringKeys;
    }

    public void setIntegerKeys(Map<Integer, ItemStack> integerKeys) {
        this.integerKeys = integerKeys;
    }

    public void setStringKeys(Map<String, Set<Integer>> stringKeys) {
        this.stringKeys = stringKeys;
    }

    public Set<Integer> getSlots(String key) {
        return get(key);
    }

    public InventoryButton getButton(String key) {
        return new InventoryButton(key, getSlots(key));
    }


    public void addCommand(String key, String command) {
    }

    public String getCommand(String key) {
        return null;
    }

    public SuperInventoryButton getSuperButton(String key) {
        return null;
    }
}

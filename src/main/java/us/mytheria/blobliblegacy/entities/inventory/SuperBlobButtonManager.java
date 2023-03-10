package us.mytheria.blobliblegacy.entities.inventory;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import us.mytheria.blobliblegacy.entities.Uber;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class SuperBlobButtonManager extends ButtonManager {
    private HashMap<String, String> buttonCommands;

    public static SuperBlobButtonManager smartFromConfigurationSection(ConfigurationSection section) {
        SuperBlobButtonManager superBlobButtonManager = new SuperBlobButtonManager();
        superBlobButtonManager.read(section);
        return superBlobButtonManager;
    }

    /**
     * Builds a SuperButtonManager through the specified ConfigurationSection.
     * Uses HashMap to store buttons.
     *
     * @param section configuration section which contains all the buttons
     * @return a non abstract ButtonManager.
     */
    public static SuperBlobButtonManager fromConfigurationSection(ConfigurationSection section) {
        SuperBlobButtonManager blobButtonManager = new SuperBlobButtonManager();
        blobButtonManager.add(section);
        return blobButtonManager;
    }

    /**
     * Builds a non abstract ButtonManager without any buttons stored yet.
     */
    public SuperBlobButtonManager() {
        super(new HashMap<>(), new HashMap<>());
        buttonCommands = new HashMap<>();
    }

    @Override
    public boolean contains(String key) {
        return getStringKeys().containsKey(key);
    }

    @Override
    public boolean contains(int slot) {
        return getIntegerKeys().containsKey(slot);
    }

    @Override
    public Set<Integer> get(String key) {
        return getStringKeys().get(key);
    }

    @Override
    public Collection<String> keys() {
        return getStringKeys().keySet();
    }

    @Override
    public ItemStack get(int slot) {
        return getIntegerKeys().get(slot);
    }

    @Override
    public Collection<ItemStack> buttons() {
        return getIntegerKeys().values();
    }

    /**
     * adds all buttons inside a configuration section
     *
     * @param section configuration section which contains all the buttons
     * @return true if at least one button was succesfully added.
     * this is determined in case the being called after the first add call
     */
    @Override
    public boolean add(ConfigurationSection section) {
        Set<String> set = section.getKeys(false);
        Uber<Boolean> madeChanges = new Uber<>(false);
        set.stream().filter(s -> !contains(s)).forEach(s -> {
            madeChanges.talk(true);
            CommandMultiSlotable slotable = CommandMultiSlotable.read(section.getConfigurationSection(s), s);
            slotable.setInSuperBlobButtonManager(this);
        });
        return madeChanges.thanks();
    }

    public boolean read(ConfigurationSection section) {
        Set<String> set = section.getKeys(false);
        Uber<Boolean> madeChanges = new Uber<>(false);
        set.stream().filter(s -> !contains(s)).forEach(s -> {
            madeChanges.talk(true);
            CommandMultiSlotable slotable = CommandMultiSlotable.read(section.getConfigurationSection(s), s);
            slotable.setInSuperBlobButtonManager(this);
        });
        set.forEach(s -> {
            if (contains(s))
                return;
            madeChanges.talk(true);
            CommandMultiSlotable slotable = CommandMultiSlotable.read(section.getConfigurationSection(s), s);
            slotable.setInSuperBlobButtonManager(this);
        });
        return madeChanges.thanks();
    }

    @Override
    public void addCommand(String key, String command) {
        buttonCommands.put(key, command);
    }

    @Override
    public String getCommand(String key) {
        return buttonCommands.get(key);
    }

    @Override
    public SuperInventoryButton getSuperButton(String key) {
        String command = getCommand(key);
        return SuperInventoryButton.fromInventoryButton(getButton(key), command,
                command != null);
    }
}

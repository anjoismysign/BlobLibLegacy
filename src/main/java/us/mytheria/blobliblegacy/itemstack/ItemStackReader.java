package us.mytheria.blobliblegacy.itemstack;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import us.mytheria.blobliblegacy.SkullCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemStackReader {

    public static ItemStackBuilder read(ConfigurationSection section) {
        String inputMaterial = section.getString("Material", "DIRT");
        ItemStackBuilder builder;
        if (!inputMaterial.startsWith("HEAD-")) {
            String data = "";
            if (inputMaterial.contains(":")) {
                inputMaterial = inputMaterial.split(":")[0];
                data = inputMaterial.split(":")[1];
            }
            Material material = Material.getMaterial(inputMaterial);
            if (material == null) {
                Bukkit.getLogger().severe("Material " + inputMaterial + " is not a valid material. Using DIRT instead.");
                material = Material.DIRT;
            }
            if (data.length() == 0)
            builder = ItemStackBuilder.build(material);
            else
                builder = ItemStackBuilder.build(new ItemStack(material, 1, (byte) Byte.parseByte(data)));
        } else
            builder = ItemStackBuilder.build(SkullCreator.itemFromUrl(inputMaterial.substring(5)));

        if (section.contains("Amount")) {
            builder = builder.amount(section.getInt("Amount"));
        }
        if (section.contains("DisplayName")) {
            builder = builder.displayName(ChatColor
                    .translateAlternateColorCodes('&', section
                            .getString("DisplayName")));
        }
        if (section.contains("Lore")) {
            List<String> input = section.getStringList("Lore");
            List<String> lore = new ArrayList<>();
            input.forEach(string -> lore.add(ChatColor
                    .translateAlternateColorCodes('&', string)));
            builder = builder.lore(lore);
        }
        if (section.contains("Unbreakable")) {
            builder = builder.unbreakable(section.getBoolean("Unbreakable"));
        }
        if (section.contains("Color")) {
            builder = builder.color(parseColor(section.getString("Color")));
        }
        if (section.contains("Enchantments")) {
            List<String> enchantNames = section.getStringList("Enchantments");
            builder = builder.deserializeAndEnchant(enchantNames);
        }
        boolean showAll = section.getBoolean("ShowAllItemFlags", false);
        if (showAll)
            builder = builder.showAll();
        if (section.contains("ItemFlags")) {
            List<String> flagNames = section.getStringList("ItemFlags");
            builder = builder.deserializeAndFlag(flagNames);
        }
        return builder;
    }

    public static ItemStackBuilder read(File file, String path) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return read(Objects.requireNonNull(config.getConfigurationSection(path)));
    }

    public static ItemStackBuilder read(File file) {
        return read(file, "ItemStack");
    }

    public static ItemStackBuilder read(YamlConfiguration config, String path) {
        return read(Objects.requireNonNull(config.getConfigurationSection(path)));
    }

    public static ItemStackBuilder read(YamlConfiguration config) {
        return read(config, "ItemStack");
    }

    public static Color parseColor(String color) {
        String[] input = color.split(",");
        if (input.length != 3) {
            throw new IllegalArgumentException("Color " + color + " is not a valid color.");
        }
        try {
            int r = Integer.parseInt(input[0]);
            int g = Integer.parseInt(input[1]);
            int b = Integer.parseInt(input[2]);
            return Color.fromRGB(r, g, b);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Color " + color + " is not a valid color.");
        }
    }

    public static String parse(Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
}

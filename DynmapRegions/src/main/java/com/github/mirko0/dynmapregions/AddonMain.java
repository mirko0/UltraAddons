package com.github.mirko0.dynmapregions;

import com.github.mirko0.dynmapregions.regions.RegionsManager;
import lombok.Getter;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraRegions.UltraRegions;
import me.TechsCode.UltraRegions.gui.Overview;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Used as a main class of addons.
 * For this to work you must call the instance somewhere in elements constructor does not matter what element you call it in.
 */
@Getter
public class AddonMain implements Listener {

    @Getter
    public static final AddonMain instance = new AddonMain("DynmapRegions", "mirko0");
    private String name, author;

    public AddonMain(String name, String author) {
        this.name = name; this.author = author;
        try {
            onLoad();
            UltraRegions.getInstance().log(name + " onLoad executed. " + "Author: " + author);
        } catch (Exception e) {
            UltraRegions.getInstance().log(name + " onLoad failed please contact author. " + "Author: " + author);
            e.printStackTrace();
        }
    }
    public static void log(String message) {
        UltraRegions.getInstance().log("|DynmapRegions|: " + ChatColor.translateAlternateColorCodes('&', message));
    }

    @Getter
    private RegionsManager regionsManager;

    private void onLoad() {
        setupSettingsFile();
        long time = System.currentTimeMillis();
        Plugin ultraRegions = Bukkit.getPluginManager().getPlugin("UltraRegions");
        Plugin dynmap = Bukkit.getPluginManager().getPlugin("dynmap");

        if(ultraRegions == null) {
           log("&cCould not find UltraRegions! This plugin is needed for this addon to work!");
            return;
        }
        if(dynmap == null) {
           log("&cCould not find Dynmap! This plugin is needed for this addon to work!");
            return;
        }
        if (!ultraRegions.isEnabled() || !dynmap.isEnabled()) {
            log("&cEither Dynmap or UltraRegions is disabled! DynmapRegions addons will not be enabled.");
            return;
        }

        regionsManager = new RegionsManager(this);

        log("Successfully loaded &9DynmapRegions&7 in &e" + (System.currentTimeMillis() - time) + "ms&7!");
        log("Created with help from Original Plugin: https://www.spigotmc.org/resources/ultraregions-dynmap.76766/");
        log("I must credit &bEazyFTW &ffor creating the original plugin. Most of the code is still based on what he made in the original plugin");
        Bukkit.getPluginManager().registerEvents(this, UltraRegions.getInstance().getBootstrap());
    }

    private YamlConfiguration configFile;
    private final File configFileO = new File("plugins/UltraRegions/dynmapregions.yml");
    private BotSettings settings;
    private void setupSettingsFile() {
        try {
            if (!configFileO.exists()) {
                configFileO.createNewFile();
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFileO);
            int refreshTimeInTicks = (int) config.get("refresh_time", 1);
            String layerName = config.get("layer_name", "Regions").toString();
            String lineColor = config.get("line_color", ColorUtil.DefaultColors.BLUE.getHexCode()).toString();
            String fillColor = config.get("fill_color", ColorUtil.DefaultColors.GRAY.getHexCode()).toString();
            int lineWeight = (int) config.get("line_weight", 1);
            double lineOpacity = (double) config.get("line_opacity", 0.8);
            double fillOpacity = (double) config.get("fill_opacity", 0.35);
            boolean use3D = (boolean) config.get("use_3D", true);

            config.save(configFileO);
            this.configFile = config;
            settings = new BotSettings(refreshTimeInTicks * 20 * 60, layerName, lineColor, fillColor, lineOpacity, fillOpacity, lineWeight, use3D);
        } catch (IOException e) {
            UltraCustomizer.getInstance().log("Error while loading settings file");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onStop(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals("UltraRegions") || event.getPlugin().getName().equals("dynmap")) {
            AddonMain.log("Shutting down due to UltraRegions or dynmap disabling.");
            regionsManager.stop();
        }
    }


}

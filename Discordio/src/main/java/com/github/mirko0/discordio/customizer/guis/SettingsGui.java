package com.github.mirko0.discordio.customizer.guis;

import com.github.mirko0.discordio.AddonMain;
import com.github.mirko0.discordio.BotSettings;
import com.github.mirko0.discordio.dbot.BotMain;
import me.TechsCode.UltraCustomizer.ColorPalette;
import me.TechsCode.UltraCustomizer.UltraCustomizer;
import me.TechsCode.UltraCustomizer.base.addons.gui.AddonsMarketplaceListView;
import me.TechsCode.UltraCustomizer.base.addons.gui.InstalledAddonsView;
import me.TechsCode.UltraCustomizer.base.dialog.UserInput;
import me.TechsCode.UltraCustomizer.base.gui.ActionType;
import me.TechsCode.UltraCustomizer.base.gui.Button;
import me.TechsCode.UltraCustomizer.base.gui.GUI;
import me.TechsCode.UltraCustomizer.base.gui.Model;
import me.TechsCode.UltraCustomizer.base.item.XMaterial;
import me.TechsCode.UltraCustomizer.base.legacy.Common;
import me.TechsCode.UltraCustomizer.base.translations.Phrase;
import me.TechsCode.UltraCustomizer.base.visual.Animation;
import me.TechsCode.UltraCustomizer.base.visual.Color;
import me.TechsCode.UltraCustomizer.base.visual.Colors;
import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;

public abstract class SettingsGui extends GUI {
    private final UltraCustomizer plugin;

    public SettingsGui(Player player, UltraCustomizer customizer) {
        super(player, customizer);
        this.plugin = customizer;
    }

    public abstract void onBack();

    public String getCurrentTitle() {
        return "Discordio > Settings";
    }

    public void construct(Model model) {
        model.setSlots(36);
        model.button(5, this::TokenButton);
        model.button(12, this::activityText);
        model.button(13, this::activityUrl);
        model.button(15, this::activityType);
        model.button(16, this::onlineStatusButton);

        model.button(32, (var1x) -> {
            Common.BackButton(var1x, (var1y) -> this.onBack());
        });
    }

    private void TokenButton(Button button) {
        button.material(XMaterial.TOTEM_OF_UNDYING)
                .name(Animation.wave("Bot Token", ColorPalette.MAIN, Colors.WHITE))
                .lore("§7Modify the bot token using this button.", "§7Changing this value will reboot the discord bot.", "", "Status: " + (AddonMain.instance.getDiscordBot().isRunning() ? "running" : "offline"));
        button.action(actionType -> {
            new UserInput(p, this.plugin, Phrase.create("Discordio.UI.token", "Bot Token"), Phrase.create("Discordio.UI.value", "Please enter the value in chat.")) {
                public boolean onResult(final String result) {
                    AddonMain.instance.getConfigFile().set("token", result);
                    AddonMain.instance.getSettings().setToken(result);
                    try {
                        AddonMain.instance.getConfigFile().save(AddonMain.instance.getConfigFileO());
                    } catch (IOException e) {
                        AddonMain.log("Error happened while saving config file during token input");
                    }
                    AddonMain.instance.getDiscordBot().stop();
                    reopen();
                    AddonMain.instance.getDiscordBot().start();
                    return true;
                }

                public void onClose(Player player) {
                    reopen();
                }
            };
        });
    }

    private void handleActivityButton(String key, String result) {
        YamlConfiguration configFile = AddonMain.instance.getConfigFile();
        configFile.set(key, result);
        String activityText = configFile.get("activityText", "Hi!").toString();
        String activityUrl = configFile.get("activityUrl", "NOT_SET").toString();
        String activityType = configFile.get("activityType", "CUSTOM_STATUS").toString();
        BotSettings settings = AddonMain.instance.getSettings();
        settings.setActivityText(activityText);
        settings.setActivityType(activityType);
        settings.setActivityUrl(activityUrl);
        JDA jda = AddonMain.instance.getDiscordBot().getJda();
        if (settings.isPAPI() && activityText.contains("%")) activityText = PlaceholderAPI.setPlaceholders(null, activityText);
        if (jda != null) jda.getPresence().setActivity(BotMain.constructActivity(activityText, activityUrl, activityType));
        try {
            configFile.save(AddonMain.instance.getConfigFileO());
        } catch (IOException e) {
            AddonMain.log("Error happened while saving config file during activity button");
        }
    }


    private void activityText(Button button) {
        button.material(XMaterial.BOOK)
                .name(Animation.wave("Activity Text", ColorPalette.MAIN, Colors.WHITE))
                .lore("§7Modify the bot activity text.");
        button.action(actionType -> {
            new UserInput(p, this.plugin, "Activity Text", Phrase.create("Discordio.UI.value", "Please enter the value in chat.")) {
                public boolean onResult(final String result) {
                    handleActivityButton("activityText", result);
                    reopen();
                    return true;
                }

                public void onClose(Player player) {
                    reopen();
                }
            };
        });
    }

    private void activityUrl(Button button) {
        button.material(XMaterial.CHAIN)
                .name(Animation.wave("Activity Url", ColorPalette.MAIN, Colors.WHITE))
                .lore("§7Modify the bot activity url.");
        button.action(actionType -> {
            new UserInput(p, this.plugin, "Activity Url", Phrase.create("Discordio.UI.value", "Please enter the value in chat.")) {
                public boolean onResult(final String result) {
                    handleActivityButton("activityUrl", result);
                    reopen();
                    return true;
                }

                public void onClose(Player player) {
                    reopen();
                }
            };
        });
    }

    private void activityType(Button button) {
        button.material(XMaterial.COMMAND_BLOCK)
                .name(Animation.wave("Activity Type", ColorPalette.MAIN, Colors.WHITE))
                .lore("§7Modify the bot activity type.", "§7(CUSTOM_STATUS, PLAYING, WATCHING, STREAMING)");
        button.action(actionType -> {
            new UserInput(p, this.plugin, "Activity Type", Phrase.create("Discordio.UI.value", "Please enter the value in chat.")) {
                public boolean onResult(final String result) {
                    handleActivityButton("activityType", result);
                    reopen();
                    return true;
                }

                public void onClose(Player player) {
                    reopen();
                }
            };
        });
    }

    private void onlineStatusButton(Button button) {
        button.material(XMaterial.BELL)
                .name(Animation.wave("Online Status", ColorPalette.MAIN, Colors.WHITE))
                .lore("§7Modify bot online status.",
                        "",
                        "§7Current: " + AddonMain.instance.getSettings().getOnlineStatus().getKey(),
                        "§aOnline §7- left click",
                        "§8Offline §7- right click",
                        "§eIdle §7- middle click",
                        "§cDo Not Disturb §7- drop item")
        ;
        button.action(actionType -> {
            OnlineStatus status = OnlineStatus.ONLINE;
            if (actionType.equals(ActionType.LEFT)) status = OnlineStatus.ONLINE;
            if (actionType.equals(ActionType.RIGHT)) status = OnlineStatus.OFFLINE;
            if (actionType.equals(ActionType.MIDDLE)) status = OnlineStatus.IDLE;
            if (actionType.equals(ActionType.Q)) status = OnlineStatus.DO_NOT_DISTURB;
            AddonMain.instance.getSettings().setOnlineStatus(status);
            AddonMain.instance.getConfigFile().set("onlineStatus", status.name());
            JDA jda = AddonMain.instance.getDiscordBot().getJda();
            if (jda != null) jda.getPresence().setStatus(status);
            try {
                AddonMain.instance.getConfigFile().save(AddonMain.instance.getConfigFileO());
            } catch (IOException e) {
                AddonMain.log("Error while saving online status!");
            }
            reopen();
        });
    }
}

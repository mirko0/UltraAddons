package com.github.mirko0.discordio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

@AllArgsConstructor
@Getter
@Setter
public class BotSettings {
    private String token;
    private OnlineStatus onlineStatus;
    private String activityText;
    private String activityUrl;
    private String activityType;
    private boolean pAPI;
}
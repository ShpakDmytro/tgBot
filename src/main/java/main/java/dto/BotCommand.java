package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BotCommand {
    public String command;
    public String description;

    public BotCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }
}

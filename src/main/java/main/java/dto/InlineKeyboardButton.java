package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InlineKeyboardButton {
    @JsonProperty("text")
    public String text;
    @JsonProperty("callback_data")
    public String callback_data;
    @JsonProperty("url")
    public String url = "";

    public InlineKeyboardButton() {
    }

    public InlineKeyboardButton(String text, String callback_data) {
        this.text = text;
        this.callback_data = callback_data;
    }

    public InlineKeyboardButton setUrl(String url) {
        InlineKeyboardButton button = new InlineKeyboardButton(this.text,this.callback_data = null);
        button.url = url;
        return button;
    }

    @Override
    public String toString() {
        return "text " + text + "callback_data " + callback_data;
    }
}

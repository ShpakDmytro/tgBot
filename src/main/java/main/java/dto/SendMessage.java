package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessage {
    public Long chat_id;
    public String text;
    @JsonProperty("reply_markup")
    InlineKeyboardMarkup reply_markup;

    public SendMessage(Long chat_id, String text) {
        this.chat_id = chat_id;
        this.text = text;
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        ArrayList <InlineKeyboardButton> buttons = new ArrayList<>();
        markup.inline_keyboard = new ArrayList<>();
        markup.inline_keyboard.add(buttons);
        this.reply_markup = markup;
    }

    public SendMessage(Long chat_id, String text,InlineKeyboardMarkup inline_keyboard) {
        this.chat_id = chat_id;
        this.text = text;
        this.reply_markup = inline_keyboard;
    }

    @Override
    public String toString() {
        return "chat_id=" + chat_id + " text=" + text + " inline_keyboard" + reply_markup;
    }
}

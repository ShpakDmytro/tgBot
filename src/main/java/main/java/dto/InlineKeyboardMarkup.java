package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InlineKeyboardMarkup {
    public ArrayList<ArrayList<InlineKeyboardButton>> inline_keyboard;

    public InlineKeyboardMarkup() {}

    @Override
    public String toString() {
        return " " + inline_keyboard;
    }
}

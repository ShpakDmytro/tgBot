package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    @JsonProperty("message_id")
    public Long message_id;
    @JsonProperty("from")
    public User user;
    @JsonProperty("chat")
    public Chat chat;
    @JsonProperty("date")
    public Integer date;
    @JsonProperty("text")
    public String text;
    @JsonProperty("entities")
    public ArrayList <Entities> entities;
    @JsonProperty("reply_markup")
    public InlineKeyboardMarkup reply_markup;
    @JsonProperty("photo")
    PhotoSize [] photo;

    public Message() {}
}

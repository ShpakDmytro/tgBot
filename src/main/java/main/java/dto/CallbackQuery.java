package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackQuery {
    @JsonProperty("id")
    public String id;
    @JsonProperty("from")
    public User user;
    @JsonProperty("message")
    public Message message;
    @JsonProperty("chat_instance")
    public String chat_instance;
    @JsonProperty("data")
    public String data;

    public CallbackQuery() {
    }
}

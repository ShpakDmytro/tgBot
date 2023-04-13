package main.java.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Update {
    @JsonProperty("update_id")
    public Long update_id;
    @JsonProperty("message")
    public Message message;
    @JsonProperty("callback_query")
    public CallbackQuery callbackQuery;

    public Update() {}
}

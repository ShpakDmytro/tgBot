package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendPhoto {
    @JsonProperty("chat_id")
    String chat_id;
    @JsonProperty("photo")
    String photo;

    public SendPhoto(Long id, String message) {
        this.chat_id = String.valueOf(id);
        this.photo = message;
    }
}

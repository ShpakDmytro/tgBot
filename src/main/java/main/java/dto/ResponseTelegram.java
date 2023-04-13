package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTelegram {
    @JsonProperty("ok")
    public boolean ok;
    @JsonProperty("result")
    public ArrayList<Update> updates;

    public ResponseTelegram() {}
}

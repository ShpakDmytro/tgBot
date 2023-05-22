package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entities {
    @JsonProperty("offset")
    public Integer offset;
    @JsonProperty("length")
    public Integer length;
    @JsonProperty("type")
    public String type;

    public Entities() {}
}

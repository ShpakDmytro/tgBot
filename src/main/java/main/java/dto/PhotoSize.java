package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoSize {

    @JsonProperty("file_id")
    String file_id;
    @JsonProperty("file_unique_id")
    String file_unique_id;
    @JsonProperty("width")
    Integer width;
    @JsonProperty("height")
    Integer height;

    public PhotoSize() {
    }
}

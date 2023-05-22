package main.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty("id")
    public Long id;
    @JsonProperty("is_bot")
    public boolean is_bot;
    @JsonProperty("first_name")
    public String first_name;
    @JsonProperty("last_name")
    public String last_name;
    @JsonProperty("username")
    public String username;
    @JsonProperty("language_code")
    public String language_code;

    public User() {}
}

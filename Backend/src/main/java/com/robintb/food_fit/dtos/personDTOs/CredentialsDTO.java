package com.robintb.food_fit.dtos.personDTOs;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CredentialsDTO {
    private String username;
    private char[] password;

    @JsonCreator
    public CredentialsDTO(@JsonProperty("username") String username,
                          @JsonProperty("password") char[] password){
        this.username = username;
        this.password = password;
    }
}

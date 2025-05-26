package org.rtss.mosad_backend.dto.user_dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AuthDTO {

    @JsonProperty("Authenticated")
    private boolean authenticated;
    @JsonProperty("access_token")
    private String accessToken;

    public AuthDTO(boolean authenticated, String accessToken) {
        this.authenticated = authenticated;
        this.accessToken = accessToken;
    }

    public AuthDTO() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

}

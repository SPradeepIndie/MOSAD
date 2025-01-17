package org.rtss.mosad_backend.dto.user_dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class AuthDTO {

    @JsonProperty("Authenticated:")
    private boolean authenticated;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    public AuthDTO(boolean authenticated, String accessToken, String refreshToken) {
        this.authenticated = authenticated;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AuthDTO() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}

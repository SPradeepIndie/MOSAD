package org.rtss.mosad_backend.dto.user_dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AuthDTO {

    @JsonProperty("Authenticated")
    private boolean authenticated;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("role")
    private String role;
    @JsonProperty("branchId")
    private Long branchId;

    public AuthDTO(boolean authenticated, String accessToken, String role, Long branchId) {
        this.authenticated = authenticated;
        this.accessToken = accessToken;
        this.role = role;
        this.branchId = branchId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "AuthDTO{" +
                "authenticated=" + authenticated +
                ", accessToken='" + accessToken + '\'' +
                ", role='" + role + '\'' +
                ", branchID='" + branchId + '\'' +
                '}';
    }
}

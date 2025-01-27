package org.rtss.mosad_backend.entity.user_management;

import jakarta.persistence.*;

@Entity
public class BlackListTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    public BlackListTokens() {
    }

    public BlackListTokens(Long tokenId, String token, TokenType tokenType) {
        this.tokenId = tokenId;
        this.token = token;
        this.tokenType = tokenType;
    }

    public BlackListTokens(String token, TokenType tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}

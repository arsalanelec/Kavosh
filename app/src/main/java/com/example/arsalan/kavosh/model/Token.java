package com.example.arsalan.kavosh.model;

public class Token {

    /*"token_type": "Bearer",
    "expires_in": 31535999,
    "access_token": "",
    "refresh_token": ""*/
    private String tokenType;
    private int expireIn;
    private String accessToken;
    private String refreshToken;

    public Token() {
    }

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

    public String getAccessToken() {

        return "Bearer " + accessToken;
    }

    public void setAccessToken(String accessToken) {

        this.accessToken = accessToken;
    }

    public String getAccessTokenWOBearer() {

        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

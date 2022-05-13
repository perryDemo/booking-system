package com.agoda.clone.agoda.service;

import com.agoda.clone.agoda.model.RefreshToken;

public interface RefreshTokenService {
    public RefreshToken generateRefreshToken();
    void validateRefreshToken(String token);
    public void deleteRefreshToken(String token);
}

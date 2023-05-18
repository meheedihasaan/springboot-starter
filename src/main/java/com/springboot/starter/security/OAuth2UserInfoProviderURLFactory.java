package com.springboot.starter.security;

import com.springboot.starter.enums.SocialAuthType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OAuth2UserInfoProviderURLFactory {

    @Value("${provider.google.userInfoURI}")
    private String googleUserInfoURI;

    @Value("${provider.facebook.userInfoURI}")
    private String facebookUserInfoURI;

    @Value("${provider.apple.userInfoURI}")
    private String appleUserInfoURI;

    public String getUserInfoURI(SocialAuthType provider) {
        if (provider.equals(SocialAuthType.GOOGLE)) {
            return googleUserInfoURI;
        }

        if (provider.equals(SocialAuthType.FACEBOOK)) {
            return facebookUserInfoURI;
        }

        return appleUserInfoURI;
    }
}

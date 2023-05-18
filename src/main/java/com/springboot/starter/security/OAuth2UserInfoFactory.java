package com.springboot.starter.security;

import com.springboot.starter.enums.SocialAuthType;
import com.springboot.starter.exceptions.OAuth2AuthenticationProcessingException;
import com.springboot.starter.models.FacebookOAuth2UserInfo;
import com.springboot.starter.models.GoogleOAuth2UserInfo;
import com.springboot.starter.models.OAuth2UserInfo;
import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(SocialAuthType provider, Map<String, Object> attributes) {
        if (provider.equals(SocialAuthType.GOOGLE)) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        if (provider.equals(SocialAuthType.FACEBOOK)) {
            return new FacebookOAuth2UserInfo(attributes);
        }

        throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + provider + " is not supported yet.");
    }
}

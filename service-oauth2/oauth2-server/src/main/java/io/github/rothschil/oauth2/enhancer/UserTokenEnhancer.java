package io.github.rothschil.oauth2.enhancer;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;
import java.util.UUID;


/**
 * 用户自定义token令牌，包括access_token和refresh_token
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/7/9 19:43
 * @since 1.0.0
 */
public class UserTokenEnhancer implements TokenEnhancer {
    /**
     * 重新定义令牌token
     * @date 2019/7/9 19:56
     * @since 1.0
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
            token.setValue(getToken());
            //使用DefaultExpiringOAuth2RefreshToken类生成refresh_token，自带过期时间，否则不生效，refresh_token一直有效
            DefaultExpiringOAuth2RefreshToken refreshToken = (DefaultExpiringOAuth2RefreshToken) token.getRefreshToken();
            if (refreshToken != null) {
                token.setRefreshToken(new DefaultExpiringOAuth2RefreshToken(getToken(), refreshToken.getExpiration()));
            }
            Map<String, Object> additionalInformation = Maps.newHashMap();
            additionalInformation.put("client_id", authentication.getOAuth2Request().getClientId());
            //添加额外配置信息
            token.setAdditionalInformation(additionalInformation);
            return token;
        }
        return accessToken;
    }

    /**
     * @Description 生成自定义token
     * @date 2019/7/9 19:50
     * @since 1.0
     */
    private String getToken() {
        return StringUtils.join(UUID.randomUUID().toString().replace("-", ""));
    }
}

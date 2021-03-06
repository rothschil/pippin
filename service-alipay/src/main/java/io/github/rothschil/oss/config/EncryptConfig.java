package io.github.rothschil.oss.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.rothschil.oss.bo.EncryptSdk;

/**
 * 信息安全处理类
 *
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2018/4/23 - 15:18
 * @since 1.0.0
 */
@Configuration
public class EncryptConfig {

    private static final Logger LOG = LoggerFactory.getLogger(EncryptConfig.class);

    @Autowired
    private EncryptSdk encryptSdk;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptSdk.getStorehouse());
        config.setKeyObtentionIterations(encryptSdk.getIterations());
        config.setPoolSize(encryptSdk.getSize());
        config.setProviderName(encryptSdk.getProviderName());
        config.setSaltGeneratorClassName(encryptSdk.getSaltGeneratorClassName());
        config.setIvGeneratorClassName(encryptSdk.getLvGeneratorClassName());
        config.setStringOutputType(encryptSdk.getStringOutputType());
        encryptor.setConfig(config);
        return encryptor;
    }

}

package io.github.rothschil.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/** Socket 配置
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/13 - 9:32
 * @since 1.0.0
 */
@Configuration
public class WebSocketConfig {

    /**
     * 用于扫描和注册所有携带ServerEndPoint注解的实例。
     * <p>
     * PS:若部署到外部容器 则无需提供此类。
     *
     * @return ServerEndpointExporter
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2019/10/11-20:10
     **/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

package com.ozkansari.springboot.example.embeddedTomcatAjponly.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableEmbeddedTomcatAjpConfig
@Configuration
public class EmbeddedTomcatConfig {

    @Value("${tomcat.ajp.port}")
    private int ajpPort;
    
    @Value("${tomcat.ajp.redirectPort:8443}")
    private int redirectPort;

    @Value("${tomcat.ajp.secret:#{null}}")
    private String tomcatAjpSecret;

    /**
     * The standard protocol value for an AJP connector is AJP/1.3 which uses an
     * auto-switching mechanism to select either a Java NIO based connector or an
     * APR/native based connector.
     */
    @Value("${tomcat.ajp.protocol:AJP/1.3}")
    private String tomcatAjpProtocol;

    @Value("${tomcat.ajp.scheme:http}")
    private String tomcatAjpScheme;

    @Value("${tomcat.ajp.allowTrace:false}")
    private boolean tomcatAjpAllowTrace;
    
    @Value("${tomcat.ajp.only:true}")
    private boolean tomcatAjpOnly;

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return server -> {
            if (server instanceof TomcatServletWebServerFactory) {
                TomcatServletWebServerFactory tomcatServletWebServerFactory = (TomcatServletWebServerFactory) server;
                if (tomcatAjpOnly) {
                    tomcatServletWebServerFactory.setProtocol(tomcatAjpProtocol);
                    tomcatServletWebServerFactory.setPort(ajpPort);
                    TomcatConnectorCustomizer ajpCustomizer = connector -> initAjpConnector(connector);
                    tomcatServletWebServerFactory.getTomcatConnectorCustomizers().add(ajpCustomizer);
                } else {
                    Connector connector = new Connector(tomcatAjpProtocol);
                    initAjpConnector(connector);
                    tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector);
                }
            }
        };
    }

    private void initAjpConnector(Connector connector) {
        connector.setPort(ajpPort);
        connector.setAllowTrace(tomcatAjpAllowTrace);
        connector.setScheme(tomcatAjpScheme);
        connector.setRedirectPort(redirectPort);
        AbstractAjpProtocol<? extends Object> ajpProtocol = findAjpProtocol(connector);
        if (StringUtils.isEmpty(tomcatAjpSecret)) {
            connector.setSecure(false);
            ajpProtocol.setSecretRequired(false);
        } else {
            connector.setSecure(true);
            ajpProtocol.setSecret(tomcatAjpSecret);
        }
    }

    @SuppressWarnings("unchecked")
    private AbstractAjpProtocol<? extends Object> findAjpProtocol(Connector connector) {
        ProtocolHandler protocolHandler = connector.getProtocolHandler();
        if (protocolHandler instanceof AbstractAjpProtocol) {
            return (AbstractAjpProtocol<? extends Object>) protocolHandler;
        }
        log.error("AJP cannot be configured for {} protocol", protocolHandler.getClass());
        return null;    
    }
}

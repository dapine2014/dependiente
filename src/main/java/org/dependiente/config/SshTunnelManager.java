package org.dependiente.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "ssh.tunnel", havingValue = "active")
public class SshTunnelManager {

    private static final String SSH_USER = "";
    private static final String SSH_REMOTE_HOST = "";
    private static final String SSH_PRIVATE_KEY_PATH = "";
    private static final String SSH_PASS_PHRASE = "";
    private static final Integer SSH_REMOTE_PORT = ;
    private static final String DATASOURCE_URL = ";
    private static final String DATASOURCE_USERNAME = "";
    private static final String DATASOURCE_PASSWORD = "";
    private static final String DRIVER_CLASS_NAME = "";
    private Session session;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        try {
            JSch jsch = new JSch();
            Resource resource = resourceLoader.getResource(SSH_PRIVATE_KEY_PATH);

            // Leer el contenido del archivo de clave privada
            try (InputStream inputStream = resource.getInputStream()) {
                String privateKey = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                jsch.addIdentity("sshKey", privateKey.getBytes(), null, SSH_PASS_PHRASE.getBytes());
            }

            session = jsch.getSession(SSH_USER, SSH_REMOTE_HOST, SSH_REMOTE_PORT);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

        } catch (JSchException | IOException e) {
            throw new RuntimeException("Error al inicializar el túnel SSH", e);
        }
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        try {
            if (session != null && session.isConnected()) {
                session.setPortForwardingL(5432, "", 5432);
                dataSource.setDriverClassName(DRIVER_CLASS_NAME);
                dataSource.setUrl(DATASOURCE_URL);
                dataSource.setUsername(DATASOURCE_USERNAME);
                dataSource.setPassword(DATASOURCE_PASSWORD);
            } else {
                throw new RuntimeException("La sesión SSH no está conectada");
            }
        } catch (JSchException e) {
            throw new RuntimeException("Error al configurar el port forwarding", e);
        }

        return dataSource;
    }

    @PreDestroy
    public void closeTunnel() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

}

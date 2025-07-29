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
import org.springframework.util.StreamUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "ssh.tunnel", havingValue = "active")
public class SshTunnelManager {

    private static final String SSH_REMOTE_HOST = "44.215.190.245";
    private static final String SSH_USER = "ec2-user";
    private static final String SSH_PRIVATE_KEY_PATH = "classpath:key-Gal-dev.pem";

    private static final String SSH_PASS_PHRASE = "";
    private static final Integer SSH_REMOTE_PORT = 22;
    private static final String DATASOURCE_URL = "jdbc:postgresql://localhost:5432/uatgal2";
    private static final String DATASOURCE_USERNAME = "uatusergal";
    private static final String DATASOURCE_PASSWORD = "uatpassw0rdgal";
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    private Session session;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        try {
            JSch jsch = new JSch();
            Resource resource = resourceLoader.getResource(SSH_PRIVATE_KEY_PATH);

            // Leer el contenido de la clave privada
            String privateKeyContent = StreamUtils.copyToString(
                    resource.getInputStream(),
                    StandardCharsets.UTF_8
            );

            // Agregar la identidad usando el contenido de la clave
            jsch.addIdentity(
                    "sshKey",
                    privateKeyContent.getBytes(),
                    null,
                    SSH_PASS_PHRASE.getBytes()
            );

            session = jsch.getSession(SSH_USER, SSH_REMOTE_HOST, SSH_REMOTE_PORT);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
        } catch (JSchException | IOException e) {
            throw new RuntimeException("Error al inicializar el túnel SSH", e);
        }
    }

    @Bean
    public DataSource dataSource() {
        if (session == null || !session.isConnected()) {
            throw new RuntimeException("La sesión SSH no está establecida");
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        try {
            session.setPortForwardingL(5432, "rds-gal-postgres-uat.cluster-c52ockommp8u.us-east-1.rds.amazonaws.com", 5432);
            dataSource.setDriverClassName(DRIVER_CLASS_NAME);
            dataSource.setUrl(DATASOURCE_URL);
            dataSource.setUsername(DATASOURCE_USERNAME);
            dataSource.setPassword(DATASOURCE_PASSWORD);
        } catch (JSchException e) {
            throw new RuntimeException("Error al configurar el reenvío de puertos SSH", e);
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

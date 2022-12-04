package com.github.shaart.demo.jgroups.config;

import lombok.extern.slf4j.Slf4j;
import org.jgroups.JChannel;
import org.jgroups.Receiver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ServiceReplicasConfig {

    @Value("${app.replicas.name}")
    private String replicaName;

    @Bean
    public JChannel serviceReplicasChannel(Receiver receiver) throws Exception {
        JChannel channel = new JChannel("src/main/resources/udp.xml");

        // Set a name
        channel.name(replicaName);

        // Do not ignore our messages
        channel.setDiscardOwnMessages(false);

        // Register for callbacks
        channel.setReceiver(receiver);

        // Connect
        String clusterName = "demo-app-cluster";
        log.info("Connecting to cluster = {}", clusterName);
        channel.connect(clusterName);

        // Start state transfer
        channel.getState(null, 0);

        return channel;
    }
}

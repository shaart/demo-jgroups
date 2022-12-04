package com.github.shaart.demo.jgroups.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppStartedListener {

    @EventListener
    public void contextStared(ContextRefreshedEvent event) {
        log.info("Context started");
    }
}

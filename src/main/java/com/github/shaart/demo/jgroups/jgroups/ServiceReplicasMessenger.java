package com.github.shaart.demo.jgroups.jgroups;

import com.github.shaart.demo.jgroups.dto.BroadcastResponseDto;
import com.github.shaart.demo.jgroups.model.BroadcastResult;
import com.github.shaart.demo.jgroups.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ObjectMessage;
import org.jgroups.Receiver;
import org.jgroups.View;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceReplicasMessenger implements Receiver {

    public static final int STATE_VERSION = 0;

    private final ObjectProvider<JChannel> channelProvider;
    private final PersonService personService;
    private View lastView;

    public BroadcastResponseDto sendNewPerson(String personName) {
        log.info("Sending message '{}' to other replicas", personName);
        Map<BroadcastResult, List<String>> result = new EnumMap<>(BroadcastResult.class);
        Arrays.stream(BroadcastResult.values())
                .forEach(it -> result.put(it, new ArrayList<>()));

        JChannel channel = channelProvider.getObject();
        channel.view().getMembers().forEach(address -> {
            log.info("Sending message '{}' to replicas '{}'", personName, address);
            var message = new ObjectMessage(address, personName);
            try {
                channel.send(message);
                result.get(BroadcastResult.SUCCESS).add(address.toString());
            } catch (Exception e) {
                log.error("Error sending message '{}' to replica '{}'", personName, address, e);
                result.get(BroadcastResult.FAIL).add(address.toString());
            }
        });

        return BroadcastResponseDto.builder()
                .result(result)
                .build();
    }

    @Override
    public void receive(Message msg) {
        String name = msg.getObject();
        log.info("Received message from replicas: {}", name);
        personService.createPerson(name);
    }

    @Override
    public void viewAccepted(View newView) {
        // Save view if this is the first
        if (lastView == null) {
            log.info("Received initial view: {}", newView);
        } else {
            // Compare to last view
            log.info("Received new view.");

            List<Address> newMembers = View.newMembers(lastView, newView);
            log.info("New members: {}", newMembers);

            List<Address> exMembers = View.leftMembers(lastView, newView);
            log.info("Exited members: {}", exMembers);
        }
        lastView = newView;
    }

    @Override
    public void setState(InputStream input) throws Exception {
        int read = input.read();
        if (read == STATE_VERSION) {
            log.info("Got setState = {}", read);
        } else {
            log.error("Got setState = {}", read);
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        int value = STATE_VERSION;
        log.info("Got getState. Sending value: {}", value);
        output.write(value);
    }
}

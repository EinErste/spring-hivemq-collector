package org.ein.erste.iot.hivemq.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

@Service
@Slf4j
@RequiredArgsConstructor
public class MqttLogstashMessageConsumer implements MqttMessageConsumer {
    @Value("${logstash.domain}")
    private String domain;
    @Value("${logstash.port}")
    private Integer port;
    private Socket socket;

    @Override
    public void consume(String s) {
        restoreConnection();
        try {
            DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            os.writeBytes(s + "\n");
            os.flush();
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void restoreConnection(){
        while (socket == null || socket.isClosed()){
            try {
                socket = new Socket(domain, port);
                Thread.sleep(1000);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}

package org.ein.erste.iot.hivemq.collector;

import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ClientEmulator {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final Random random = new Random();
    private MqttClient client;
    private Gson gson = new Gson();
    private String name;
    public ClientEmulator(String name) throws Exception {
        this.name = name;
        client = new MqttClient(
                Utils.MQTT_DOMAIN,
                MqttClient.generateClientId(),
                new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        client.connect(options);
    }

    public void sendData(double tempDeviation, double humidityDeviation) throws Exception{
        double temperature = 20 + random.nextGaussian() * tempDeviation;
        double humidity = 50 + random.nextGaussian() * humidityDeviation;
        long timestamp = System.currentTimeMillis();
        var data = new ClientData(name, temperature, humidity, timestamp);

        byte[] jsonDataBytes = gson.toJson(data).getBytes(StandardCharsets.UTF_8);

        MqttMessage message = new MqttMessage();
        message.setPayload(jsonDataBytes);
        client.publish("/device/" + name, message);
    }

    public static void main(String[] args) throws Exception{
        ClientEmulator clientEmulator = new ClientEmulator("rogue_device");
        while (true){
            clientEmulator.sendData(20, 10);
            Thread.sleep(1000);
        }
    }
}

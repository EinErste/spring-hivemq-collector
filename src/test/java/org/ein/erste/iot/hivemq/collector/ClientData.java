package org.ein.erste.iot.hivemq.collector;

public record ClientData(String name, double temperature, double humidity, long timestamp) {
}

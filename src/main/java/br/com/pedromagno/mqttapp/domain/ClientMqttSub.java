package br.com.pedromagno.mqttapp.domain;

public class ClientMqttSub extends ClientMqtt {

    public ClientMqttSub(String serverURI, String port, String topic, String username, String password) {
        super(serverURI, port, topic, username, password);
    }
}

package br.com.pedromagno.mqttapp.domain;

public class ClientMqttPub extends ClientMqtt {
    private String message;

    public ClientMqttPub(String serverURI, String port, String topic) {
        super(serverURI, port, topic);
    }

    public ClientMqttPub(String serverURI, String port, String topic, String username, String password) {
        super(serverURI, port, topic, username, password);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

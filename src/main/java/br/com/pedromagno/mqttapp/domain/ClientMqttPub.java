package br.com.pedromagno.mqttapp.domain;

public class ClientMqttPub extends ClientMqtt {
    private String message;

    public ClientMqttPub(String serverURI, String port, String topic, String message, String username, String password) {
        super(serverURI, port, topic, username, password);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

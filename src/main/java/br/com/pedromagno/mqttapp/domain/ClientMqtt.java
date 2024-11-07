package br.com.pedromagno.mqttapp.domain;

public abstract class ClientMqtt {
    private String serverURI;
    private String port;
    private String username;
    private String password;
    private String topic;

    public ClientMqtt(String serverURI, String port, String topic) {
        this.serverURI = serverURI;
        this.port = port;
        this.topic = topic;
    }


    public ClientMqtt(String serverURI, String port, String topic, String username, String password) {
        this.serverURI = serverURI;
        this.port = port;
        this.topic = topic;
        this.username = username;
        this.password = password;
    }

    public String getServerURI() {
        return serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}

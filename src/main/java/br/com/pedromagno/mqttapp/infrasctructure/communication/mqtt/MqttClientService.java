package br.com.pedromagno.mqttapp.infrasctructure.communication.mqtt;

import br.com.pedromagno.mqttapp.MqttApplication;
import br.com.pedromagno.mqttapp.domain.ClientMqtt;
import br.com.pedromagno.mqttapp.domain.ClientMqttPub;
import org.eclipse.paho.client.mqttv3.*;

public class MqttClientService implements MqttCallbackExtended {

    private IMqttClient mqttClient;

    private MqttConnectOptions mqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setConnectionTimeout(10);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setKeepAliveInterval(50);
        mqttConnectOptions.setMaxInflight(3);

        return mqttConnectOptions;
    }


    public void connectToBroker(ClientMqtt clientMqtt) {
        String brokerUri = "tcp://" + clientMqtt.getServerURI()+ ":" + clientMqtt.getPort();

        try {
            this.mqttClient = new MqttClient(brokerUri, "mqtt-application");
            mqttClient.setCallback(this);  // Definindo o callback para o cliente MQTT

            MqttConnectOptions options = mqttConnectOptions();
            if(clientMqtt.getUsername() != null && clientMqtt.getPassword() != null && clientMqtt.getUsername().isEmpty() && clientMqtt.getPassword().isEmpty()){
                options.setUserName(clientMqtt.getUsername());
                options.setPassword(clientMqtt.getPassword().toCharArray());
            }
            mqttClient.connect(options);

            MqttApplication.getApplication().atualizarTextArea("Conectado ao broker MQTT!");

            // Subscreve ao tópico
            subscribeToTopic(clientMqtt.getTopic());
            MqttApplication.getApplication().atualizarTextArea("Inscrito no tópico: " + clientMqtt.getTopic());

        } catch (MqttException e) {
            e.printStackTrace();
            MqttApplication.getApplication().atualizarTextArea("Erro ao conectar ao broker MQTT: " + e.getMessage());
        }
    }

    private void subscribeToTopic(String topic) throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.subscribe(topic);
        } else {
            MqttApplication.getApplication().atualizarTextArea("Cliente não está conectado!");
        }
    }

    // Métodos da interface MqttCallbackExtended

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        MqttApplication.getApplication().atualizarTextArea("Conexão com o broker completada. Reconnect: " + reconnect + ", URI do servidor: " + serverURI);
    }

    @Override
    public void connectionLost(Throwable cause) {
        MqttApplication.getApplication().atualizarTextArea("Conexão perdida: " + cause.getMessage());
        // Tente reconectar aqui, por exemplo:
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            MqttApplication.getApplication().atualizarTextArea("Erro ao reconectar ao broker MQTT: " + e.getMessage());
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        MqttApplication.getApplication().atualizarTextArea("Mensagem recebida no tópico " + topic + ": " + new String(message.getPayload()));
        String mensagemRecebida = new String(message.getPayload());
        MqttApplication.getApplication().atualizarTextArea(mensagemRecebida);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        MqttApplication.getApplication().atualizarTextArea("Mensagem entregue com sucesso: " + token.getMessageId());
    }

    public void publish(ClientMqttPub client, String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        if(!mqttClient.isConnected()){
            mqttClient.connect(mqttConnectOptions());
        }
        mqttClient.publish(client.getTopic(), mqttMessage);
        MqttApplication.getApplication().atualizarTextArea("Você está publicando no tópico: " + client.getTopic());
        MqttApplication.getApplication().atualizarTextArea("Mensagem publicada: " + mqttMessage.toString());
    }

    public void diconnect() throws MqttException {
        mqttClient.disconnect();
        MqttApplication.getApplication().atualizarTextArea("Desconectado do broker MQTT");
    }
}

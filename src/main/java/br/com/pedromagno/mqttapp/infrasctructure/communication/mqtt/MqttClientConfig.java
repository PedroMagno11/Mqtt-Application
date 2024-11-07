package br.com.pedromagno.mqttapp.infrasctructure.communication.mqtt;

import br.com.pedromagno.mqttapp.MqttApplication;
import br.com.pedromagno.mqttapp.domain.ClientMqtt;
import org.eclipse.paho.client.mqttv3.*;

public class MqttClientConfig implements MqttCallbackExtended {

    private IMqttClient mqttClient;
    private String mensagemRecebida;

    private MqttConnectOptions mqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setConnectionTimeout(10);
        mqttConnectOptions.setAutomaticReconnect(false);
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

            System.out.println("Conectado ao broker MQTT!");

            // Subscreve ao tópico
            subscribeToTopic(clientMqtt.getTopic());
            System.out.println("Inscrito no tópico: " + clientMqtt.getTopic());

        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao broker MQTT: " + e.getMessage());
        }
    }

    private void subscribeToTopic(String topic) throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.subscribe(topic);
        } else {
            System.out.println("Cliente não está conectado!");
        }
    }

    // Métodos da interface MqttCallbackExtended

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("Conexão com o broker completada. Reconnect: " + reconnect + ", URI do servidor: " + serverURI);
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Conexão perdida: " + cause.getMessage());
        // Tente reconectar aqui, por exemplo:
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Mensagem recebida no tópico " + topic + ": " + new String(message.getPayload()));
        mensagemRecebida = new String(message.getPayload());
        MqttApplication.getApplication().atualizarTextArea(mensagemRecebida);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Mensagem entregue com sucesso: " + token.getMessageId());
    }

    public String getMensagemRecebida() {
        return mensagemRecebida;
    }
}

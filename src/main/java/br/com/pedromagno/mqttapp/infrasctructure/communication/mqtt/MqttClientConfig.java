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
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setKeepAliveInterval(50);
        mqttConnectOptions.setMaxInflight(3);

        return mqttConnectOptions;
    }


    public void connectToBroker(ClientMqtt clientMqtt) {
        String brokerUri = "tcp://" + clientMqtt.getServerURI()+ ":" + clientMqtt.getPort();

        try {
            String clientId = MqttClient.generateClientId();
            this.mqttClient = new MqttClient(brokerUri, clientId);
            mqttClient.setCallback(this);  // Definindo o callback para o cliente MQTT

            MqttConnectOptions options = mqttConnectOptions();

            if(clientMqtt.getUsername() != null && clientMqtt.getPassword() != null && !clientMqtt.getUsername().isEmpty() && !clientMqtt.getPassword().isEmpty()){
                options.setUserName(clientMqtt.getUsername());
                options.setPassword(clientMqtt.getPassword().toCharArray());
            }
            mqttClient.connect(options);

           MqttApplication.getApplication().atualizarTextArea("Conectado ao broker MQTT!");
            // Subscreve ao tópico
            subscribeToTopic(clientMqtt.getTopic());
//            System.out.println("Inscrito no tópico: " + clientMqtt.getTopic() + "\n");
            MqttApplication.getApplication().atualizarTextArea("Endereço do broker: " + clientMqtt.getServerURI() + ":" + clientMqtt.getPort());
            MqttApplication.getApplication().atualizarTextArea("Você está inscrito no tópico: " + clientMqtt.getTopic());

        } catch (MqttException e) {
            MqttApplication.getApplication().atualizarTextArea("Erro ao conectar ao broker MQTT: " + e.getMessage() + "\n");
        }
    }

    private void subscribeToTopic(String topic) throws MqttException {
        if (mqttClient.isConnected()) {
            mqttClient.subscribe(topic);
        } else {
            MqttApplication.getApplication().atualizarTextArea("Cliente não está conectado!\n");
        }
    }

    // Métodos da interface MqttCallbackExtended

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("Conexão com o broker completada. Reconnect: " + reconnect + ", URI do servidor: " + serverURI);
    }

    @Override
    public void connectionLost(Throwable cause) {
        MqttApplication.getApplication().atualizarTextArea("Conexão perdida: " + cause.getMessage());
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
        MqttApplication.getApplication().atualizarTextArea("[+] Mensagem recebida no tópico " + topic + ": " + mensagemRecebida);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Mensagem entregue com sucesso: " + token.getMessageId());
    }

    public String getMensagemRecebida() {
        return mensagemRecebida;
    }

    public boolean isConnected(){
        return mqttClient != null && mqttClient.isConnected();
    }

    public void disconnect() throws MqttException {
        if(mqttClient != null){
            mqttClient.disconnect();
        }
    }

    public void publish(String topic, MqttMessage message) throws MqttException {
        if(mqttClient == null){
            throw new MqttException(new Throwable("Cliente Mqtt não inializado. Você se conectou ao broker ?"));
        }
        if(!mqttClient.isConnected()) {
            throw new MqttException(new Throwable("Cliente Mqtt não está conectado. Conecte-se antes de publicar."));
        }

        mqttClient.publish(topic, message);
    }
}

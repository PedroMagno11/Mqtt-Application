package br.com.pedromagno.mqttapp.view.controllers;

import br.com.pedromagno.mqttapp.domain.ClientMqtt;
import br.com.pedromagno.mqttapp.domain.ClientMqttPub;
import br.com.pedromagno.mqttapp.domain.ClientMqttSub;
import br.com.pedromagno.mqttapp.infrasctructure.communication.mqtt.MqttClientService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.concurrent.Flow;

public class MqttAppController {
    public TextField serverUriFld;
    public TextField serverPortFld;
    public TextField usernameFld;
    public TextField passwordFld;
    public Button publishBtn;
    public Button connectBtn;
    public Button disconnectBtn;
    public TextArea logsTxtArea;
    public TextField topicSubFld;
    public TextField topicPubFld;
    public TextField messageFld;

    private MqttClientService service;
    private ClientMqtt clientPub;
    private ClientMqtt clientSub;

    @FXML
    public void initialize() {
        logsTxtArea.setText("Bem-vindo ao Mqtt Application!\n");
    }

    public void atualizarTextArea(String mensagem){
        Platform.runLater(() -> {
            if(this.logsTxtArea.getText().length() >= 60*mensagem.length()){
                this.logsTxtArea.clear();
            }
            this.logsTxtArea.appendText(mensagem + "\n");
        });
    }

    public MqttAppController() {
        this.service = new MqttClientService();
    }

    public void publish(ActionEvent actionEvent) throws MqttException {
        clientPub = new ClientMqttPub(serverUriFld.getText(), serverPortFld.getText(), topicPubFld.getText(), messageFld.getText(), usernameFld.getText(), passwordFld.getText());
        service.publish((ClientMqttPub) clientPub, messageFld.getText());
    }

    public void connect(ActionEvent actionEvent) {
        clientSub = new ClientMqttSub(serverUriFld.getText(),serverPortFld.getText(),topicSubFld.getText(), usernameFld.getText(), passwordFld.getText());
        service.connectToBroker(clientSub);
    }

    public void disconnect(ActionEvent actionEvent) throws MqttException {
        service.diconnect();
    }
}

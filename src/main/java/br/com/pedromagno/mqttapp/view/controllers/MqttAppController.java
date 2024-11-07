package br.com.pedromagno.mqttapp.view.controllers;

import br.com.pedromagno.mqttapp.domain.ClientMqttSub;
import br.com.pedromagno.mqttapp.infrasctructure.communication.mqtt.MqttClientConfig;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    private MqttClientConfig service;

    @FXML
    public void initialize() {
        logsTxtArea.setText("Bem-vindo ao Mqtt Application!");
    }

    public void atualizarTextArea(String mensagem){
        Platform.runLater(() -> {
            this.logsTxtArea.appendText(mensagem + "\n");
        });
    }

    public MqttAppController() {
        this.service = new MqttClientConfig();
    }

    public void publish(ActionEvent actionEvent) {

    }

    public void connect(ActionEvent actionEvent) {
        ClientMqttSub client = new ClientMqttSub(serverUriFld.getText(),serverPortFld.getText(),topicSubFld.getText(), usernameFld.getText(), passwordFld.getText());
        service.connectToBroker(client);
    }

    public void disconnect(ActionEvent actionEvent) {
    }
}

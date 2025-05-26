package br.com.pedromagno.mqttapp.view.controllers;

import br.com.pedromagno.mqttapp.domain.ClientMqttSub;
import br.com.pedromagno.mqttapp.infrasctructure.communication.mqtt.MqttClientConfig;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

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
    public TextField msgPubField;
    public Button cleanBtn;

    private MqttClientConfig service;

    @FXML
    public void initialize() {
        logsTxtArea.setText("Bem-vindo ao Mqtt Application!\n");
        publishBtn.setDisable(true);
        cleanBtn.setDisable(true);
        logsTxtArea.setEditable(false);
        logsTxtArea.setFocusTraversable(false);
    }

    public void atualizarTextArea(String mensagem){
        Platform.runLater(() -> {
            this.logsTxtArea.appendText(mensagem + "\n");

            /* Limpa automaticamente o logsTxtArea quando o número de mensagens exibidas for igual a 1000
            * essa funcionalidade remove as 900 primeiras mensagens e deixa somente as 100 últimas mensagens
            */
            removerAs900MensagensMaisAntigasDoLog();
        });
    }

    private void removerAs900MensagensMaisAntigasDoLog(){
        String[] linhas = logsTxtArea.getText().split("\n");
        if(linhas.length > 1000){
            StringBuilder builder = new StringBuilder();
            for(int i = linhas.length - 100; i< linhas.length; i++){
                builder.append(linhas[i]).append("\n");
            }
            logsTxtArea.setText(builder.toString());
        }
    }

    public MqttAppController() {
        this.service = new MqttClientConfig();
    }

    public void publish(ActionEvent actionEvent) {
        try{
            String topic = topicPubFld.getText();
            String msg = msgPubField.getText();

            MqttMessage message = new MqttMessage(msg.getBytes());
            message.setQos(1);
            service.publish(topic, message);
            logsTxtArea.appendText( "Mensagem publicada no tópico: " + topic + "\n");
        } catch (MqttException e){
            logsTxtArea.appendText("Erro ao publicar: " + e.getMessage() + "\n");
        }

    }

    public void connect(ActionEvent actionEvent) {
        if(!serverPortFld.getText().matches("\\d+")){
            logsTxtArea.appendText("Porta inválida!\n");
            return;
        }

        String topic = topicSubFld.getText().trim();
        if(topic.isEmpty()){
            logsTxtArea.appendText("O campo de tópico está vazio!\nPreencha um tópico antes de conectar.\n");
            return;
        }

        ClientMqttSub client = new ClientMqttSub(serverUriFld.getText(),serverPortFld.getText(),topic, usernameFld.getText(), passwordFld.getText());
        service.connectToBroker(client);
        publishBtn.setDisable(false);
        cleanBtn.setDisable(false);
    }

    public void disconnect(ActionEvent actionEvent) {
        try{
            if(service != null && service.isConnected()){
                service.disconnect();
                logsTxtArea.appendText("Disconectado com sucesso!\n");
                publishBtn.setDisable(true);
            }
        } catch (MqttException e){
            logsTxtArea.appendText("Erro ao desconectar: " + e.getMessage() + "\n");
        }
    }

    public void clean(ActionEvent actionEvent) {
        logsTxtArea.clear();
    }
}

package br.com.pedromagno.mqttapp;

import br.com.pedromagno.mqttapp.view.controllers.MqttAppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MqttApplication extends Application {
    private MqttAppController controller;
    private static MqttApplication application;

    public static MqttApplication getApplication(){
        return application;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mqtt-application-view.fxml"));
       Pane root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        application = this;
        Scene scene = new Scene(root);
        stage.setTitle("MQTT Application");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    public void atualizarTextArea(String mensagem){
        controller.atualizarTextArea(mensagem);
    }

    public MqttAppController getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch();
    }
}
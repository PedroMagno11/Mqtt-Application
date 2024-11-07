module br.com.pedromagno.mqttapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.eclipse.paho.client.mqttv3;


    opens br.com.pedromagno.mqttapp to javafx.fxml;
    exports br.com.pedromagno.mqttapp;
    exports br.com.pedromagno.mqttapp.view.controllers;
    opens br.com.pedromagno.mqttapp.view.controllers to javafx.fxml;
}
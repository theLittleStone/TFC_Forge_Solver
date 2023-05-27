package com.github.thelittlestone;

import com.github.thelittlestone.config.ConfigLoader;
import com.github.thelittlestone.dataController.FileLoader;
import com.github.thelittlestone.logic.WorldDataManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("gui/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ConfigLoader.saveConfig();
        WorldDataManager.updateAllWorld();
    }

    public static void main(String[] args) {
        launch();
    }
}
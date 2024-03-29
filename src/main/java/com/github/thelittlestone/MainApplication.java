package com.github.thelittlestone;

import com.github.thelittlestone.config.ConfigLoader;
import com.github.thelittlestone.gui.ComponentBoard;
import com.github.thelittlestone.logic.WorldDataManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    public static final int WIDTH = 960;
    public static final int HEIGHT = 540;
    public static final String VERSION = "1.0.0";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("guiXml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle("TFC Forge Solver!");
        Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("icon.png")));
        stage.getIcons().add(image);
        stage.setScene(scene);
        ComponentBoard.mainStage = stage;
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
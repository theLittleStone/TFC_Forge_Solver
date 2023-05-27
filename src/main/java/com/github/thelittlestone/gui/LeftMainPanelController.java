package com.github.thelittlestone.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Created by theLittleStone on 2023/5/28.
 */
public class LeftMainPanelController {
    @FXML
    public BorderPane leftMainPane;
    @FXML
    public Button addButton;
    @FXML
    public TextField newWorldTextField;
    @FXML
    public Button deleteButton;
    @FXML
    public ListView<String> worldListView;


    public void addButtonOnAction(){
        System.out.println(1);
    }
}

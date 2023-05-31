package com.github.thelittlestone.gui;

import com.github.thelittlestone.logic.json.JsonWorldRecipes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by theLittleStone on 2023/5/28.
 */
public class RightMainPanelController implements Initializable {
    @FXML
    public ChoiceBox<String> materialChoiceBox;
    @FXML
    public ChoiceBox<String> targetChoiceBox;
    @FXML
    public TextField valueTextField;
    @FXML
    public Button startButton;
    @FXML
    public Label showStepLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void refresh(JsonWorldRecipes newWorld){


    }
}

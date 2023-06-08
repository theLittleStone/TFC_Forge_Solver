package com.github.thelittlestone.gui;

import com.github.thelittlestone.logic.components.ActionCombination;
import com.github.thelittlestone.logic.components.ActionEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by theLittleStone on 2023/6/7.
 */
public class RightPaneController implements Initializable {
    @FXML
    public Label stepLabel;
    @FXML
    public Label valueLabel;

    @FXML
    public Button lightHitButton;
    @FXML
    public Button mediumHitButton;
    @FXML
    public Button heavyHitButton;
    @FXML
    public Button drawButton;

    @FXML
    public Button punchButton;
    @FXML
    public Button bendButton;
    @FXML
    public Button upsetButton;
    @FXML
    public Button shrinkButton;

    @FXML
    public Button backButton;
    @FXML
    public Button cleanButton;

    @FXML
    public Button saveButton;

    private ActionCombination actionCombination = new ActionCombination();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComponentBoard.rightPaneController = this;
    }
    @FXML
    public void lightHitButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.LightHit);
        update();
    }
    @FXML
    public void mediumHitButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.MediumHit);
        update();
    }
    @FXML
    public void heavyHitButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.HeavyHit);
        update();
    }
    @FXML
    public void drawButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.Draw);
        update();
    }
    @FXML
    public void punchButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.Punch);
        update();
    }
    @FXML
    public void bendButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.Bend);
        update();
    }
    @FXML
    public void upsetButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.Upset);
        update();
    }
    @FXML
    public void shrinkButtonOnAction(ActionEvent actionEvent) {
        actionCombination.add(ActionEnum.Shrink);
        update();
    }
    @FXML
    public void backButtonOnAction(ActionEvent actionEvent) {
        if (actionCombination.isEmpty()){
            return;
        }
        actionCombination.remove(actionCombination.size() - 1 );
        update();
    }
    @FXML
    public void cleanButtonOnAction(ActionEvent actionEvent) {
        clean();
    }
    @FXML
    public void saveButtonOnAction(ActionEvent actionEvent) {
        if (!ComponentBoard.middlePaneController.isCertain){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("请先选择好中间面板的配方");
            alert.showAndWait();
            return;
        }
        ComponentBoard.middlePaneController.valueSpinner.getValueFactory().setValue(actionCombination.getTotalValue());
        ComponentBoard.middlePaneController.setButtonOnAction(new ActionEvent());
    }

    public void update(){
        stepLabel.setText(actionCombination.labelContent());
        valueLabel.setText("= " + actionCombination.getTotalValue());
    }
    public void clean(){
        actionCombination = new ActionCombination();
        update();
    }
}

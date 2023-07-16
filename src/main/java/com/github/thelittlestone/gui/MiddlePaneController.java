package com.github.thelittlestone.gui;

import com.github.thelittlestone.logic.WorldDataManager;
import com.github.thelittlestone.logic.components.ActionCombination;
import com.github.thelittlestone.logic.json.JsonRecipe;
import com.github.thelittlestone.logic.json.JsonRecipeResult;
import com.github.thelittlestone.translate.NameMappingTableLoader;
import com.github.thelittlestone.translate.NameMappingUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by theLittleStone on 2023/5/28.
 */
public class MiddlePaneController implements Initializable {
    @FXML
    public ChoiceBox<NameMappingUnit> materialChoiceBox;
    @FXML
    public ChoiceBox<NameMappingUnit> targetChoiceBox;
    @FXML
    public Spinner<Integer> valueSpinner;
    @FXML
    public Button setButton;
    @FXML
    public Button startButton;
    @FXML
    public Label showStepLabel;

    private boolean isCleaning = false;
    public boolean isCertain = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //将自身注册到面板中供调用
        ComponentBoard.middlePaneController = this;

        //初始化数字选项框
        valueSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 1));

        //为材料选择框添加事件
        materialChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            isCleaning = true;
            targetChoiceBox.getItems().clear();

            NameMappingUnit selected = materialChoiceBox.getSelectionModel().getSelectedItem();
            //对targetChoiceBox添加内容
            if (selected != null) {
                targetChoiceBox.getItems().addAll(WorldDataManager.currentWorld.allResults(selected.origName));
            }else {
                targetChoiceBox.getItems().clear();
            }
            //其他元素清零
            valueSpinner.getValueFactory().setValue(0);
            showStepLabel.setText("");
            isCertain = false;
            isCleaning = false;
            //让右侧面板也清零
            ComponentBoard.rightPaneController.clean();
        });

        //为目标选择框添加事件
        targetChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            //防止误判
            if (!isCleaning) {
                String material = materialChoiceBox.getSelectionModel().getSelectedItem().origName;
                String target = targetChoiceBox.getSelectionModel().getSelectedItem().origName;
                JsonRecipe recipe = WorldDataManager.currentWorld.recipe(material, target);
                //设置其余组件内容
                valueSpinner.getValueFactory().setValue(recipe.value);
                showStepLabel.setText("");
                isCertain = true;
                //让右侧面板也清零
                ComponentBoard.rightPaneController.clean();
            }
        });
        //为valueSpinner添加失去焦点时的事件
        valueSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue){
                setButtonOnAction(new ActionEvent());
            }
        });
    }
    public void valueSpinnerOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Enter")){
            setButtonOnAction(new ActionEvent());

        }
    }

    public void setButtonOnAction(ActionEvent actionEvent) {
        if (!isCertain){
            return;
        }
        String material = materialChoiceBox.getSelectionModel().getSelectedItem().origName;
        String target = targetChoiceBox.getSelectionModel().getSelectedItem().origName;
        JsonRecipe recipe = WorldDataManager.currentWorld.recipe(material, target);
        recipe.value = valueSpinner.getValue();
    }

    public void startButtonOnAction(ActionEvent actionEvent) {
        if (!isCertain){
            return;
        }
        //先保存value值
        setButtonOnAction(new ActionEvent());
        String material = materialChoiceBox.getSelectionModel().getSelectedItem().origName;
        String target = targetChoiceBox.getSelectionModel().getSelectedItem().origName;
        JsonRecipe recipe = WorldDataManager.currentWorld.recipe(material, target);
        ActionCombination actionCombination = recipe.solve();
        showStepLabel.setText(actionCombination.toString());
    }

    public void refresh(){
        isCertain = false;
        isCleaning = true;

        targetChoiceBox.getItems().clear();
        valueSpinner.getValueFactory().setValue(0);
        showStepLabel.setText("");

        if (WorldDataManager.currentWorld != null) {
            materialChoiceBox.getItems().clear();
            materialChoiceBox.getItems().addAll(WorldDataManager.currentWorld.allInputs());
        }else {
            materialChoiceBox.getItems().clear();
        }

        isCleaning = false;
    }



}

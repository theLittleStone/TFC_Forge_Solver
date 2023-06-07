package com.github.thelittlestone.gui;

import com.github.thelittlestone.logic.WorldDataManager;
import com.github.thelittlestone.logic.components.ActionCombination;
import com.github.thelittlestone.logic.json.JsonRecipe;
import com.github.thelittlestone.logic.json.JsonRecipeResult;
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
    public ChoiceBox<String> materialChoiceBox;
    @FXML
    public ChoiceBox<String> targetChoiceBox;
    @FXML
    public Spinner<Integer> valueSpinner;
    @FXML
    public Button setButton;
    @FXML
    public Button startButton;
    @FXML
    public Label showStepLabel;

    private boolean isCleaning = false;

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

            String selected = materialChoiceBox.getSelectionModel().getSelectedItem();
            ArrayList<JsonRecipeResult> recipeResults = WorldDataManager.currentWorld.allResults(selected);
            //对targetChoiceBox添加内容
            for (JsonRecipeResult recipeResult : recipeResults) {
                targetChoiceBox.getItems().add(recipeResult.item);
            }
            //其他元素清零
            valueSpinner.getValueFactory().setValue(0);
            showStepLabel.setText("");
            isCleaning = false;
        });

        //为目标选择框添加事件
        targetChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            //防止误判
            if (!isCleaning) {
                String material = materialChoiceBox.getSelectionModel().getSelectedItem();
                String target = targetChoiceBox.getSelectionModel().getSelectedItem();
                JsonRecipe recipe = WorldDataManager.currentWorld.recipe(material, target);
                //对valueSpinner组件设置内容
                valueSpinner.getValueFactory().setValue(recipe.value);
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
        System.out.println(111);
        String material = materialChoiceBox.getSelectionModel().getSelectedItem();
        String target = targetChoiceBox.getSelectionModel().getSelectedItem();
        JsonRecipe recipe = WorldDataManager.currentWorld.recipe(material, target);
        recipe.value = valueSpinner.getValue();
    }

    public void startButtonOnAction(ActionEvent actionEvent) {
        //先保存value值
        setButtonOnAction(new ActionEvent());
        String material = materialChoiceBox.getSelectionModel().getSelectedItem();
        String target = targetChoiceBox.getSelectionModel().getSelectedItem();
        JsonRecipe recipe = WorldDataManager.currentWorld.recipe(material, target);
        ActionCombination actionCombination = recipe.solve();
        showStepLabel.setText(actionCombination.toString());
    }

    public void refresh(){
        if (WorldDataManager.currentWorld != null) {
            materialChoiceBox.getItems().addAll(WorldDataManager.currentWorld.allInputs());
        }else {
            materialChoiceBox.getItems().clear();
        }
        targetChoiceBox.getItems().clear();
        valueSpinner.getValueFactory().setValue(0);
        showStepLabel.setText("");
    }



}

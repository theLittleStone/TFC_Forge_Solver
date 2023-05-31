package com.github.thelittlestone.gui;

import com.github.thelittlestone.config.Config;
import com.github.thelittlestone.config.ConfigLoader;
import com.github.thelittlestone.logic.WorldDataManager;
import com.github.thelittlestone.logic.json.JsonWorldRecipes;
import com.github.thelittlestone.util.ForbiddenChars;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by theLittleStone on 2023/5/28.
 */
public class MainPanelController implements Initializable {
    @FXML
    public SplitPane mainPane;
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


    public boolean isOperating = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //初始化worldList
        ObservableList<String> ol = FXCollections.observableArrayList(getReFormatWorldNameList());
        worldListView.setItems(ol);
        //设置初始右面板世界

        String focusedItem = worldListView.getFocusModel().getFocusedItem();
        WorldDataManager.currentWorld = WorldDataManager.getJsonWorldRecipes(deFormatWorldName(focusedItem));


        //为listView选项改变添加监听器
        worldListView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    //排除系统对列表操作产生的误触发
                    if (!isOperating) {
                        System.out.println(worldListView.getFocusModel().getFocusedItem());
                        String focus = worldListView.getFocusModel().getFocusedItem();
                        WorldDataManager.currentWorld = WorldDataManager.getJsonWorldRecipes(deFormatWorldName(focus));
                    }
                    //添加改变右侧页面的代码
                });
    }

    public void addButtonOnAction(){
        isOperating = true;
        String newWorldName = newWorldTextField.getText();
        if (newWorldName.equals("")){
            Alert emptyAlert = new Alert(Alert.AlertType.ERROR);
            emptyAlert.setX(100);
            emptyAlert.setY(100);
            emptyAlert.setContentText("世界名不能为空");
            emptyAlert.show();
        }else if (ForbiddenChars.hasForbiddenChars(newWorldName)){
            Alert forbiddenAlert = new Alert(Alert.AlertType.ERROR);
            forbiddenAlert.setX(100);
            forbiddenAlert.setY(100);
            forbiddenAlert.setContentText("世界名含有不允许的字符, 建议只使用字母, 数字和'-'");
            forbiddenAlert.show();
        }else {
            try {
                JsonWorldRecipes newWorld = WorldDataManager.createWorldFromRecipe(newWorldName);
                //刷新
                ObservableList<String> ol = FXCollections.observableArrayList(getReFormatWorldNameList());
                int index = ol.indexOf(formatWorldName(newWorld.worldName));
                worldListView.setItems(ol);
//                worldListView.scrollTo(index);
                worldListView.getSelectionModel().select(index);

            } catch (IOException e) {
                Alert unKnownAlert = new Alert(Alert.AlertType.ERROR);
                unKnownAlert.setContentText("无法新建世界");
                unKnownAlert.show();
                e.printStackTrace();
            }
        }

        // 记得添加切换右边窗口的功能


        isOperating = false;
    }

    public void newWorldTextFieldOnAction(){
        addButtonOnAction();
    }

    public void deleteButtonOnAction(){
        isOperating = true;
        String focusedWorld = worldListView.getFocusModel().getFocusedItem();
        if (focusedWorld != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("确定要删除" + focusedWorld + "吗?");
            ButtonType yesButtonType = new ButtonType("确定");
            alert.getButtonTypes().set(0, yesButtonType);

            Optional<ButtonType> result = alert.showAndWait();
            //如果确认删除, 删除这个世界
            if (result.isPresent() && result.get().equals(yesButtonType)){
                try {
                    WorldDataManager.deleteWorldRecipe(deFormatWorldName(focusedWorld));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            //刷新列表
            ObservableList<String> ol = FXCollections.observableArrayList(getReFormatWorldNameList());
            worldListView.setItems(ol);
            // 添加删除右边窗口界面的代码
        }
        isOperating = false;
    }



    private ArrayList<String> getReFormatWorldNameList() {
        ArrayList<String> allWorldName = WorldDataManager.getAllWorldName();
        ArrayList<String> newWorldNameList = new ArrayList<>();
        for (String s : allWorldName) {
            newWorldNameList.add(formatWorldName(s));
        }
        return newWorldNameList;
    }
    private String deFormatWorldName(String name){
        return name.replaceFirst("世界: ", "");
    }
    private String formatWorldName(String name){
        return "世界: " + name;
    }

}

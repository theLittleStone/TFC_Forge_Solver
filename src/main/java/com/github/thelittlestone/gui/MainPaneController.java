package com.github.thelittlestone.gui;

import com.github.thelittlestone.logic.WorldDataManager;
import com.github.thelittlestone.logic.json.JsonWorldRecipes;
import com.github.thelittlestone.util.ForbiddenChars;
import com.github.thelittlestone.util.StageLocation;
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
public class MainPaneController implements Initializable {
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
    @FXML
    public ContextMenu renameMenu;




    public boolean isOperating = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (WorldDataManager.getAllWorldName().isEmpty()) {
            //初始化worldList
            ObservableList<String> ol = FXCollections.observableArrayList();
            worldListView.setItems(ol);

            //不设置初始右面板世界
        }else {
            //初始化worldList
            ObservableList<String> ol = FXCollections.observableArrayList(getReFormatWorldNameList());
            worldListView.setItems(ol);

            //设置初始右面板世界
            String focusedItem = worldListView.getFocusModel().getFocusedItem();
            WorldDataManager.currentWorld = WorldDataManager.getJsonWorldRecipes(deFormatWorldName(focusedItem));
        }


        //添加右键菜单
        MenuItem renameMenuItem = new MenuItem("重命名");
        MenuItem deleteMenuItem = new MenuItem("删除");
        renameMenuItem.setOnAction(e -> {
            isOperating = true;//防止右面板误更新

            TextInputDialog textInputDialog = new TextInputDialog("name");
            textInputDialog.setTitle("重命名");
            textInputDialog.setHeaderText("输入新的世界名");
            //硬编码
            textInputDialog.setX(StageLocation.getStageCenterX() - 260);
            textInputDialog.setY(StageLocation.getStageCenterY() - 150);

            Optional<String> result = textInputDialog.showAndWait();
            if (result.isPresent()){
                String newName = result.get();
                if (WorldDataManager.nameMap.containsKey(newName)) {
                    Alert forbiddenAlert = new Alert(Alert.AlertType.ERROR);
                    forbiddenAlert.setX(StageLocation.getStageCenterX() - 280);
                    forbiddenAlert.setY(StageLocation.getStageCenterY() - 180);
                    forbiddenAlert.setContentText("名称已经存在");
                    forbiddenAlert.show();
                }else if (result.get().isEmpty()){
                    Alert forbiddenAlert = new Alert(Alert.AlertType.ERROR);
                    forbiddenAlert.setX(StageLocation.getStageCenterX() - 280);
                    forbiddenAlert.setY(StageLocation.getStageCenterY() - 180);
                    forbiddenAlert.setContentText("名称不能为空");
                    forbiddenAlert.show();
                }else if (ForbiddenChars.hasForbiddenChars(result.get())){
                    Alert forbiddenAlert = new Alert(Alert.AlertType.ERROR);
                    forbiddenAlert.setX(StageLocation.getStageCenterX() - 280);
                    forbiddenAlert.setY(StageLocation.getStageCenterY() - 180);
                    forbiddenAlert.setContentText("世界名含有非法的字符, 建议只使用字母, 数字和'-'");
                    forbiddenAlert.show();
                }
                else {
                    String oldName = deFormatWorldName(worldListView.getFocusModel().getFocusedItem());

                    String newWorldFileName = WorldDataManager.defaultRecipeFileName(newName);
                    JsonWorldRecipes worldRecipes = WorldDataManager.worldMap.get(oldName);


                    try {
                        worldRecipes.worldName = newName;
                        WorldDataManager.renameWorldRecipeFile(oldName, worldRecipes, newWorldFileName);
                    } catch (IOException ex) {
                        worldRecipes.worldName = oldName;
                        Alert forbiddenAlert = new Alert(Alert.AlertType.ERROR);
                        forbiddenAlert.setX(StageLocation.getStageCenterX() - 280);
                        forbiddenAlert.setY(StageLocation.getStageCenterY() - 180);
                        forbiddenAlert.setContentText("重命名失败, 检查文件问题");
                        forbiddenAlert.show();
                    }

                    WorldDataManager.nameMap.remove(oldName);
                    WorldDataManager.nameMap.put(newName, newWorldFileName);

                    WorldDataManager.worldMap.remove(oldName);
                    WorldDataManager.worldMap.put(newName, worldRecipes);

                    //取消世界选择, 重命名后需要重新选择
                    WorldDataManager.currentWorld = null;

                    //重置世界列表
                    ObservableList<String> ol = FXCollections.observableArrayList(getReFormatWorldNameList());
                    worldListView.setItems(ol);

                    //重置右面板, 不如可能会报玄学错
                    ComponentBoard.middlePaneController.refresh();
                }
            }
            isOperating = false;
        });
        deleteMenuItem.setOnAction(e -> {
            deleteButtonOnAction();
        });

        renameMenu = new ContextMenu(renameMenuItem, deleteMenuItem);
        worldListView.setContextMenu(renameMenu);


        //为listView选项改变添加监听器
        worldListView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    //排除系统对列表操作产生的误触发
                    if (!isOperating) {
                        String focus = worldListView.getFocusModel().getFocusedItem();
                        WorldDataManager.currentWorld = WorldDataManager.getJsonWorldRecipes(deFormatWorldName(focus));
                        ComponentBoard.middlePaneController.refresh();
                    }

                });

        //注册到展板上
        ComponentBoard.mainPaneController = this;

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
            forbiddenAlert.setContentText("世界名含有非法的字符, 建议只使用字母, 数字和'-'");
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

                //刷新右面板
                if (WorldDataManager.currentWorld != null) {
                    WorldDataManager.updateWorld(WorldDataManager.currentWorld);
                }
                WorldDataManager.currentWorld = newWorld;
                ComponentBoard.middlePaneController.refresh();

                //文本框清空
                newWorldTextField.setText("");

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
                    WorldDataManager.deleteWorldRecipeFile(deFormatWorldName(focusedWorld));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            //刷新列表
            ObservableList<String> ol = FXCollections.observableArrayList(getReFormatWorldNameList());
            worldListView.setItems(ol);
            // 添加删除右边窗口界面的代码
            WorldDataManager.currentWorld = null;
            ComponentBoard.middlePaneController.refresh();
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

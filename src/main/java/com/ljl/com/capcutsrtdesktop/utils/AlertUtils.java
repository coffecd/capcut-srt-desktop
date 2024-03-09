package com.ljl.com.capcutsrtdesktop.utils;

import com.ljl.com.capcutsrtdesktop.MainApp;
import com.sun.tools.javac.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author lijilin
 * @date 2023/4/25 17:03
 */
public class AlertUtils
{

    public static void showAlert(String contentText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        // 获取 Stage 对象
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // 添加图标
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/icon/log.png")));
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        // 添加图标
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/icon/log.png")));
        alert.setTitle("");
        alert.setTitle("");
        alert.setHeaderText("");
        alert.setContentText(contentText);
        alert.showAndWait();

    }

    public static void shoeSuccess(String contentText)
    {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) informationAlert.getDialogPane().getScene().getWindow();
        // 添加图标
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/icon/log.png")));
        informationAlert.setTitle("温馨提示");
        informationAlert.setHeaderText("恭喜你！");
        informationAlert.setContentText(contentText);
        informationAlert.showAndWait();
    }


    public static void shoeWarning(String contentText)
    {
        Alert informationAlert = new Alert(Alert.AlertType.WARNING);
        informationAlert.setTitle("温馨提示");
        informationAlert.setHeaderText("需要注意！");
        informationAlert.setContentText(contentText);
        informationAlert.showAndWait();
    }

    public static void shoeErr(String contentText)
    {
        Alert informationAlert = new Alert(Alert.AlertType.ERROR);
        informationAlert.setTitle("温馨提示");
        informationAlert.setHeaderText("非常遗憾！");
        informationAlert.setContentText(contentText);
        informationAlert.showAndWait();
    }

    public static void shoeConfirmation(String contentText)
    {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("温馨提示");
        confirmationAlert.setHeaderText("请确认");
        confirmationAlert.setContentText(contentText);
        confirmationAlert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("User clicked OK"));
    }


}

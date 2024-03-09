package com.ljl.com.capcutsrtdesktop;

import com.ljl.com.capcutsrtdesktop.config.AppConfig;
import com.ljl.com.capcutsrtdesktop.config.WindowsAppConfig;
import com.ljl.com.capcutsrtdesktop.utils.FileUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Application extends javafx.application.Application
{
    double x, y = 0;
    Stage stage;

    @Override
    public void start(Stage stage) throws IOException
    {

        this.stage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));

        Scene scene = new Scene(root);
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.initStyle(StageStyle.TRANSPARENT);
        //鼠标随机移动
        root.setOnMousePressed(evt ->
        {
            x = evt.getSceneX();
            y = evt.getSceneY();
        });
        root.setOnMouseDragged(evt ->
        {
            stage.setX(evt.getScreenX() - x);
            stage.setY(evt.getScreenY() - y);
        });

        stage.setTitle("capcut-srt-desktop");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/log.png")));
        // 禁止窗口最大化
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();

    }

    @Override
    public void init() throws Exception
    {

        AppConfig.getInstance().loadPropertiesFile();

        String windowsConfigFilePath = AppConfig.getInstance().getWindowsConfigFilePath();
        System.out.println("windowsConfigPath:" + windowsConfigFilePath);
        //配置文件夹是否存在 不存在则创建
        FileUtils.createDirectoryIfNotExists(windowsConfigFilePath);
        String windowsConfigPath = AppConfig.getInstance().getWindowsConfigPath();
        //判断配置文件是否存在 不存在则生成一份模版
        if (!FileUtils.fileExists(windowsConfigPath))
        {
            WindowsAppConfig.getInstance().init(windowsConfigPath);
        }
        WindowsAppConfig.getInstance().loadPropertiesFile(windowsConfigPath);




    }

    public static void main(String[] args)
    {
        launch();
    }


}
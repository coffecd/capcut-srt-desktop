package com.ljl.com.capcutsrtdesktop.utils;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @Author LiJiLin
 * @Date 2024/3/9 10:47
 * @Description:
 */
public class FileUtils
{
    public static String selectFilePath(Event event, String title, String path)
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        try
        {

            directoryChooser.setTitle(title);
            if (!path.equals(""))
            {
                directoryChooser.setInitialDirectory(new File(path));
            }
            File file = directoryChooser.showDialog(stage);

            if (file == null)
            {
                return null;
            }
            return file.getPath();
        } catch (Exception e)
        {
            DirectoryChooser directoryChooser1 = new DirectoryChooser();
            File file = directoryChooser1.showDialog(stage);

            if (file == null)
            {
                return null;
            }
            return file.getPath();
        }

    }
}

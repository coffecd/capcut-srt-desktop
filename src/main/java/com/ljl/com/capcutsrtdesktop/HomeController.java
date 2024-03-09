package com.ljl.com.capcutsrtdesktop;

import com.ljl.com.capcutsrtdesktop.utils.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class HomeController implements Initializable
{
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField outPutFilePath;

    @FXML
    private TextField projecteFilePaht;

    @FXML
    private ComboBox<?> selectProject;

    @FXML
    void outPutFilePathBtn(ActionEvent event)
    {
        String path = FileUtils.selectFilePath(event, "STR文件输出路径", outPutFilePath.getText());
        if (path == null)
        {
            return;
        }
        outPutFilePath.setText(path);
//        CustomizedConfig.getCustomizedConfig().setCopyDestDirPath(distDir.getText());
//        CustomizedConfig.getInstance().updateProperties();
    }

    @FXML
    void projecteFilePahtBtn(ActionEvent event)
    {
        String path = FileUtils.selectFilePath(event, " CapCut Projects draft_content.json 的文件存放路径", projecteFilePaht.getText());
        if (path == null)
        {
            return;
        }
        projecteFilePaht.setText(path);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}
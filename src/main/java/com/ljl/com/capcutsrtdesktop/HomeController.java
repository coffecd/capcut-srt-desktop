package com.ljl.com.capcutsrtdesktop;

import com.ljl.com.capcutsrtdesktop.config.WindowsAppConfig;
import com.ljl.com.capcutsrtdesktop.utils.AlertUtils;
import com.ljl.com.capcutsrtdesktop.utils.FileUtils;
import com.ljl.com.capcutsrtdesktop.utils.JsonToStr;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
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
    private ComboBox<ProjectData> selectProject;

    @FXML
    private CheckBox isOpenFile;


    @FXML
    private TextField outPutSrtName;

    @FXML
    void outPutFilePathBtn(ActionEvent event)
    {
        String path = FileUtils.selectFilePath(event, "STR文件输出路径", outPutFilePath.getText());
        if (path == null)
        {
            return;
        }
        outPutFilePath.setText(path);
        WindowsAppConfig.getInstance().setOutPutFilePath(outPutFilePath.getText());
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
        WindowsAppConfig.getInstance().setLveditorDraftFilePath(projecteFilePaht.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        String lveditorDraftFilePath = WindowsAppConfig.getInstance().getLveditorDraftFilePath();
        String outFile = WindowsAppConfig.getInstance().getOutPutFilePath();
        if (lveditorDraftFilePath != null)
        {
            projecteFilePaht.setText(lveditorDraftFilePath);
        }
        if (outFile != null)
        {
            outPutFilePath.setText(outFile);
        }

        isOpenFile.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("CheckBox被选中");
                WindowsAppConfig.getInstance().setOpen(true);
            } else {
                System.out.println("CheckBox未被选中");
                WindowsAppConfig.getInstance().setOpen(false);
            }
        });
        initProject();
    }

    private void initProject()
    {
        selectProject.getItems().clear();
        //获取项目
        List<ProjectData> subdirectories = FileUtils.getSubdirectoriesSort(WindowsAppConfig.getInstance().getLveditorDraftFilePath());
        selectProject.getItems().addAll(subdirectories);
        // 设置默认选中第一个项目
        selectProject.getSelectionModel().select(0);
        if (WindowsAppConfig.getInstance().isOpen())
        {
            isOpenFile.setSelected(true);
        }

    }

    /**
     * 刷新
     */
    @FXML
    void refreshBtn(ActionEvent event)
    {
        initProject();
        AlertUtils.shoeSuccess("刷新完成！");
    }

    /**
     * 生成STR
     */
    @FXML
    void creatStrBtn(ActionEvent event)
    {
        ProjectData selectedItem = selectProject.getSelectionModel().getSelectedItem();
        if (selectedItem == null)
        {
            return;
        }
        String path = selectedItem.getPath();
        System.out.println("生成STR path:" + path);
        String out = outPutFilePath.getText();

        boolean create = JsonToStr.generateStr(path, out, outPutSrtName.getText());
        if (create&&isOpenFile.isSelected())
        {
            FileUtils.openFile(out);
        }else if (create){
            AlertUtils.shoeSuccess("生成成功");
        }

    }

}
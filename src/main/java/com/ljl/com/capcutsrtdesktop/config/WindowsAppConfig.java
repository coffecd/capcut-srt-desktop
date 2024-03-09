package com.ljl.com.capcutsrtdesktop.config;

import com.ljl.com.capcutsrtdesktop.utils.FileUtils;
import lombok.Data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author LiJiLin
 * @Date 2024/3/9 11:14
 * @Description: Windows系统的配置
 */
@Data
public class WindowsAppConfig
{
    /**
     * "C：/Users /user/AppData/Local/CapCut/User Data/Projects/com.lveditor.draft/" 文件地址
     * 如果用户没有收到设置 取默认的
     */
    private String lveditorDraftFilePath;
    /**
     * str 输出地址  如果用户没有指定的话  放在lveditorDraftFilePath 路径下
     */
    private String outPutFilePath;
    /**
     * 生成完成是否打开文件夹
     */
    private boolean isOpen;

    private WindowsAppConfig()
    {

    }

    /**
     * 初始化windows系统的默认配置
     *
     * @param path
     */
    public void init(String path)
    {
        Properties prop = new Properties();
        String userHome = FileUtils.getUserHome();

        prop.setProperty("lveditorDraftFilePath", userHome + "/AppData/Local/CapCut/User Data/Projects/com.lveditor.draft/");
        prop.setProperty("outPutFilePath", userHome + "/AppData/Local/CapCut/User Data/Projects/com.lveditor.draft/");
        FileUtils.writePropertiesToFile(prop, path);
    }

    private static class Holder
    {
        private static final WindowsAppConfig INSTANCE = new WindowsAppConfig();
    }

    public static WindowsAppConfig getInstance()
    {
        return WindowsAppConfig.Holder.INSTANCE;
    }

    /**
     * 加载配置
     *
     * @throws IOException
     */
    public void loadPropertiesFile(String configFilePath)
    {

        try
        {
            Properties properties = new Properties();
            InputStream in = new FileInputStream(configFilePath);
            properties.load(new InputStreamReader(in, "UTF-8"));
            lveditorDraftFilePath = properties.getProperty("lveditorDraftFilePath");
            outPutFilePath = properties.getProperty("outPutFilePath");
            String open = properties.getProperty("isOpen");
            if (open != null && open.equals("true"))
            {
                isOpen = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setLveditorDraftFilePath(String lveditorDraftFilePath)
    {
        this.lveditorDraftFilePath = lveditorDraftFilePath;
        updatePropertiesFile();
    }

    public void setOutPutFilePath(String outPutFilePath)
    {
        this.outPutFilePath = outPutFilePath;
        updatePropertiesFile();
    }

    public void setOpen(boolean open)
    {
        isOpen = open;
        updatePropertiesFile();
    }

    /**
     * 更新配置文件
     */
    public void updatePropertiesFile()
    {
        String windowsConfigPath = AppConfig.getInstance().getWindowsConfigPath();
        //判断配置文件是否存在 不存在则生成一份模版
        if (!FileUtils.fileExists(windowsConfigPath))
        {
            WindowsAppConfig.getInstance().init(windowsConfigPath);
            return;
        }

        Properties properties = new Properties();
        properties.setProperty("lveditorDraftFilePath", lveditorDraftFilePath == null ? "" : lveditorDraftFilePath);
        properties.setProperty("outPutFilePath", outPutFilePath == null ? "" : outPutFilePath);
        properties.setProperty("isOpen", isOpen == true ? "true" : "false");
        FileUtils.writePropertiesToFile(properties, windowsConfigPath);
    }

    @Override
    public String toString()
    {
        return "WindowsAppConfig{" +
                "lveditorDraftFilePath='" + lveditorDraftFilePath + '\'' +
                ", outPutFilePath='" + outPutFilePath + '\'' +
                '}';
    }
}

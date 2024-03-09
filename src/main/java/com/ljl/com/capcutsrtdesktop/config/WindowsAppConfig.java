package com.ljl.com.capcutsrtdesktop.config;

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

    private WindowsAppConfig()
    {

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
        } catch (Exception e)
        {
            e.printStackTrace();""
        }

    }

}

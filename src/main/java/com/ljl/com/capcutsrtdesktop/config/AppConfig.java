package com.ljl.com.capcutsrtdesktop.config;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Author LiJiLin
 * @Date 2024/3/9 11:06
 * @Description:
 */
@Data
public class AppConfig
{
    private String appDataPath;

    private String configName;

    private String windowsCapcutDefaultPath;

    private String macOSCapcutDefaultPath;

    private AppConfig()
    {

    }

    private static class Holder
    {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    public static AppConfig getInstance()
    {
        return Holder.INSTANCE;
    }


    /**
     * 加载配置
     *
     * @throws IOException
     */
    public void loadPropertiesFile()
    {


        try
        {

            Properties properties = new Properties();
            InputStream in = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("application.properties");
            properties.load(new InputStreamReader(in, "UTF-8"));
            appDataPath = properties.getProperty("appDataPath");
            configName = properties.getProperty("configName");
            windowsCapcutDefaultPath = properties.getProperty("windowsCapcutDefaultPath");
            macOSCapcutDefaultPath = properties.getProperty("macOSCapcutDefaultPath");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

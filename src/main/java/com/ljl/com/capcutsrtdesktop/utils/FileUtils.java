package com.ljl.com.capcutsrtdesktop.utils;

import com.ljl.com.capcutsrtdesktop.ProjectData;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.List;


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

    /**
     * 文件夹是否存在 不存在则创建
     *
     * @param folderPath
     */
    public static void createDirectoryIfNotExists(String folderPath)
    {
        Path path = Paths.get(folderPath);

        if (!Files.exists(path))
        {
            try
            {
                Files.createDirectory(path);
                System.out.println("Directory created");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else
        {
            System.out.println("Directory already exists");
        }
    }

    /**
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean fileExists(String filePath)
    {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    /**
     * 将  roperties  写入到文件
     *
     * @param prop
     * @param filePath
     */
    public static void writePropertiesToFile(Properties prop, String filePath)
    {
        try (FileOutputStream output = new FileOutputStream(filePath))
        {
            prop.store(output, "NThis is a sample properties file");
            System.out.println("Properties written to file");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getUserHome()
    {
        String userHome = System.getProperty("user.home");
        return userHome.replace("\\", "/");
    }

    public static String getUserPublic()
    {
        String userHome = "C:\\Users\\Public\\Documents";
        return userHome.replace("\\", "/");
    }

    /**
     * 获取子文件夹路径列表
     *
     * @param directoryPath
     * @return
     */
    public static List<String> getSubdirectories(String directoryPath)
    {
        File directory = new File(directoryPath);
        List<String> subdirectories = new ArrayList<>();

        // 获取所有文件和子目录
        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                // 如果是目录，添加其路径到列表
                if (file.isDirectory())
                {
                    subdirectories.add(file.getAbsolutePath());
                }
            }
        } else
        {
            System.out.println(directoryPath + " 不是一个目录，或者发生了其他错误。");
        }

        return subdirectories;
    }

    /**
     * 获取子文件夹路径列表 按照时间排序
     *
     * @param directoryPath
     * @return
     */
    public static List<ProjectData> getSubdirectoriesSort(String directoryPath)
    {
        File directory = new File(directoryPath);
        List<File> subdirectories = new ArrayList<>();

        // 获取所有文件和子目录
        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                // 如果是目录，添加到列表
                if (file.isDirectory() && !file.isHidden())
                {
                    subdirectories.add(file);
                }
            }
        } else
        {
            System.out.println(directoryPath + " 不是一个目录，或者发生了其他错误。");
        }

        // 按照创建时间排序
        Collections.sort(subdirectories, new Comparator<File>()
        {
            @Override
            public int compare(File f1, File f2)
            {
                try
                {
                    BasicFileAttributes attr1 = Files.readAttributes(f1.toPath(), BasicFileAttributes.class);
                    BasicFileAttributes attr2 = Files.readAttributes(f2.toPath(), BasicFileAttributes.class);
                    return attr2.creationTime().compareTo(attr1.creationTime());
                } catch (IOException e)
                {
                    return 0;
                }
            }
        });

        // 转换为路径列表
        List<ProjectData> paths = new ArrayList<>();
        for (File file : subdirectories)
        {
            ProjectData projectData = new ProjectData();
            projectData.setName(file.getName());
            projectData.setPath(file.getAbsolutePath());
            paths.add(projectData);
        }

        return paths;
    }

    public static String getD()
    {
        return "D:/";
    }

    /**
     * 打开文件夹
     */
    public static void openFile(String filePath)
    {
// 创建文件对象
        File xmlFile = new File(filePath);
        // 检查文件是否存在
        if (xmlFile.exists())
        {
            try
            {
                // 使用桌面类打开文件
                Desktop.getDesktop().open(xmlFile);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else
        {

        }
        // 对行为树进行赋值
    }
}

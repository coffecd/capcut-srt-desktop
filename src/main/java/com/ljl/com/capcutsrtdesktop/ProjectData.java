package com.ljl.com.capcutsrtdesktop;

import lombok.Data;

/**
 * @Author LiJiLin
 * @Date 2024/3/9 13:24
 * @Description: 项目文件夹的封装
 */
@Data
public class ProjectData
{
    private String name;

    private String path;


    @Override
    public String toString()
    {
        return "项目:" + name;
    }
}

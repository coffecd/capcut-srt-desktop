package com.ljl.com.capcutsrtdesktop.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.stream.Collectors;

/**
 * @Author LiJiLin
 * @Date 2024/3/8 13:17
 * @Description:
 */
public class JsonToStr
{

    private final static String jsonName = "draft_content.json";
    private final static String srtName = "draft_content.srt";

    public static void main(String[] args) throws IOException
    {

        String draftFileName = "C:\\Users\\0\\Desktop\\draft_content.json";
        String uptFile = "D:\\";
//        String os = System.getProperty("os.name");
//        switch (os)
//        {
//            case "Windows NT":
//                draftFileName = "draft_content.json";
//                break;
//            case "Mac OS X":
//                draftFileName = "draft_info.json";
//                break;
//            case "Linux":
//                draftFileName = "draft_info.json";
//        }
        System.out.println("file: " + draftFileName);
        generateStr(draftFileName, uptFile);
    }

    /**
     * 生成str
     *
     * @param draftFile  capcut的json文件路径
     * @param outPutFile 输出的文件夹路径
     * @throws IOException
     */
    public static void generateStr(String draftFile, String outPutFile)
    {
        try
        {
            String draftFileName = draftFile+"/" + jsonName;
            String data = new String(Files.readAllBytes(Paths.get(draftFileName)));
            JSONObject jsonData = JSON.parseObject(data);
            JSONArray materials = jsonData.getJSONArray("materials");
            JSONArray tracks = jsonData.getJSONArray("tracks");
            int subTrackNumber = 1;
            JSONArray subTiming = tracks.getJSONObject(subTrackNumber).getJSONArray("segments");
            JSONArray subtitlesInfo = new JSONArray();

            for (Object item : materials)
            {
                JSONObject i = (JSONObject) item;

                JSONArray texts = i.getJSONArray("texts");


                for (Object text : texts)
                {
                    JSONObject itx = (JSONObject) text;
                    String content = itx.getString("content")
                            .replaceAll("<.*?>", "")
                            .replaceAll("\\[|\\]", "");
                    try
                    {
                        JSONObject contentV3 = JSON.parseObject(itx.getString("content"));
                        if (contentV3 != null)
                        {
                            content = contentV3.getString("text");
                        }
                    } catch (Exception error)
                    {
                    }
                    JSONObject subtitle = new JSONObject();
                    subtitle.put("content", content);
                    subtitle.put("id", itx.getString("id"));
                    subtitlesInfo.add(subtitle);
                }

            }

            for (int i = 0; i < subtitlesInfo.size(); i++)
            {
                JSONObject s = subtitlesInfo.getJSONObject(i);
                JSONObject segment = findSegment(subTiming, s.getString("id"));

                while (segment == null)
                {
                    subTrackNumber++;
                    subTiming = tracks.getJSONObject(subTrackNumber).getJSONArray("segments");
                    segment = findSegment(subTiming, s.getString("id"));
                }
                s.put("start", segment.getJSONObject("target_timerange").getLong("start"));
                s.put("end", s.getLong("start") + segment.getJSONObject("target_timerange").getLong("duration"));
                s.put("srtStart", msToSrt(s.getLong("start")));
                s.put("srtEnd", msToSrt(s.getLong("end")));
                s.put("subNumber", i + 1);
                s.put("srtTiming", s.getString("srtStart") + " --> " + s.getString("srtEnd"));
            }


            String srtOut = subtitlesInfo.stream()
                    .map(s ->
                    {
                        JSONObject sub = (JSONObject) s;
                        return String.format("%d\n%s\n%s\n\n", sub.getIntValue("subNumber"), sub.getString("srtTiming"), sub.getString("content"));
                    })
                    .collect(Collectors.joining());
            String copyOut = subtitlesInfo.stream()
                    .map(s -> ((JSONObject) s).getString("content") + "\n")
                    .collect(Collectors.joining());


            writeToFile(srtOut, outPutFile + "/" + srtName);
            System.out.println("Run \"java MainApp --txt\" to get a copy version of the subtitles, courtesy of @dellucanil");
        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private static JSONObject findSegment(JSONArray subTiming, String id)
    {
        for (Object item : subTiming)
        {
            JSONObject i = (JSONObject) item;
            if (i.getString("material_id").equals(id))
            {
                return i;
            }
        }
        return null;
    }

    private static String msToSrt(long timeInMs)
    {
        DecimalFormat format = new DecimalFormat("00");
        long convertMs = timeInMs / 1000;
        long ms = convertMs % 1000;
        long totalSeconds = convertMs / 1000;
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long hour = totalMinutes / 60;
        return String.format("%s:%s:%s,%s",
                format.format(hour),
                format.format(minutes),
                format.format(seconds),
                format.format(ms));
    }

    private static void writeToFile(String data, String filename) throws IOException
    {
        System.out.println("Saving subtitles to file...");
        Files.write(Paths.get(filename), data.getBytes());
        System.out.println("Done!");
    }
}

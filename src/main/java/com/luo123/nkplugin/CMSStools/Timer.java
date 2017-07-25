package com.luo123.nkplugin.CMSStools;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/23.
 */
public class Timer extends PluginTask {
    public Timer(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {

        Config config = new Config(owner.getDataFolder() + "/config.yml");
        int serverid = config.getInt("serverid");
        String result = sendGet("http://localhost:5000/api/tools/whitelist?b=" + serverid);
        Gson gson = new Gson();
        ArrayList<String> userlist = gson.fromJson(result, ArrayList.class);
        for (String user : userlist) {
            owner.getServer().addWhitelist(user);
        }
    }

    private static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

}

package com.gobsidian.nukkitTools;

import cn.nukkit.Player;
import com.google.gson.Gson;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import cn.nukkit.plugin.PluginDescription;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static java.lang.Boolean.parseBoolean;

/**
 * Created by Administrator on 2017/7/23.
 * Gobsidian-Tools-for-Nukkit
 */

public class Timer extends PluginTask {
    private Config config = new Config(owner.getDataFolder() + "/config.yml");
    private int serverID = config.getInt("server-id");
    private String accesskey = (String) config.get("accesskey");
    private String secretkey = (String) config.get("keysecret");
    private String requestUrl = (String) config.get("request-url");
//    private PluginDescription pluginsVersion = new PluginDescription("version");

    @SuppressWarnings("unchecked")
    Timer(Plugin owner) {
        super(owner);
    }

    @Override
    public void onRun(int i) {
        // 发送请求
        owner.getLogger().debug("runnung!");
        sendStatus();
    }

    /*
      功能函数
     */

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

    /*
      API
     */

    public boolean getWhitelist() {
        String result;
        int loop = 0;

        COMM:
        //noinspection LoopStatementThatDoesntLoop
        while (true) {
            // 发送API请求3次，如果均失败就返回错误
            while (loop < 3) {
                try {
                    result = sendGet(this.requestUrl + "/api/tools/whitelist?b=" + this.serverID);
                    break COMM;
                } catch (ArithmeticException ignored) {
                    loop++;
                }
            }
            return false;
        }

        Gson gson = new Gson();
        ArrayList userlist = gson.fromJson(result, ArrayList.class);
        for (Object user : userlist) {
            owner.getServer().addWhitelist((String) user);
        }
        return true;
    }

    private boolean sendStatus() {
        //noinspection unchecked
        ArrayList<String> onlinePlayerList = new ArrayList();
        String result;
        int loop = 0;

        // 获得在线玩家list并转成json
        for (Player player : this.owner.getServer().getOnlinePlayers().values()) {
            onlinePlayerList.add(String.valueOf(player.getName()));
        }
        Gson gson = new Gson();
        String onlineList = gson.toJson(onlinePlayerList, ArrayList.class);


        COMM:
        //noinspection LoopStatementThatDoesntLoop
        while (true) {
            // 发送API请求3次，如果均失败就返回错误
            while (loop < 3) {
                try {
                    result = sendGet(this.requestUrl + "/api/tools/server-status?" +
                            "b=" + this.serverID +
                            "&key=" + this.accesskey +
                            "&secret=" + this.secretkey +
                            "&online-player=" + onlineList +
                            "&online-max=" + "100" +
                            "&tools-version=" + "1.0.0"
                    );
                    break COMM;
                } catch (ArithmeticException ignored) {
                    loop++;
                }
            }
            return false;
        }

        return parseBoolean(result);
    }
}
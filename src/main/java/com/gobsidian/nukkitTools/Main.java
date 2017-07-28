package com.gobsidian.nukkitTools;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.util.Objects;

import static java.lang.Thread.*;


public class Main extends PluginBase {
    public void onEnable() {
        getLogger().info("Tools is running! (http://tools.gobsidian.com) Version:");
        getLogger().info("By: luo123, Hateful_Carre1");

        //配置文件对象
        Config config = new Config(getDataFolder() + "/config.yml");
        String accesskey = (String) config.get("accesskey");
        String secretkey = (String) config.get("keysecret");

        if (Objects.equals(accesskey, "ACCESSKEY")){
            //noinspection InfiniteLoopStatement
            while (true){
                getLogger().error("\r\n" +
                        "==========| Gobsidian Error |==========\r\n" +
                        "* [EN]Error: You have not set up your accesskey, please check it!\r\n" +
//                        "* [CN]错误: 你还没有设置你的AccessKey, 故无法运行。请核实！\r\n" +
                        "***\r\n" +
                        "* [EN]How to setting my Accesskey?\r\n" +
                        "* - Step 1: Visit 'https://www.gobsidian.com/aunt/api/accesskey'to get your key\r\n" +
                        "* - Step 2: Open nukkit base directory, modify the configs file:'plugins/Gobsidian-Tools/config.yml'\r\n" +
                        "* - Step 3: reload server'\r\n" +
                        "* - More: https://blog.gobsidian.com/2017/07/28/%E5%A6%82%E4%BD%95%E5%AE%89%E8%A3%85gobsidiantools/ (chinese)\r\n" +
//                        "***\r\n" +
//                        "* [CN]如何修改Accesskey?\r\n" +
//                        "* - Step 1: 在此网页获得您的Accesskey:'https://www.gobsidian.com/aunt/api/accesskey'\r\n" +
//                        "* - Step 2: 打开Nukkit根目录,找到并修改配置文件:'plugins/Gobsidian-Tools/config.yml'\r\n" +
//                        "* - Step 3: 重启服务器'\r\n" +
//                        "* - 更详细的帮助请访问:https://blog.gobsidian.com/2017/07/28/%E5%A6%82%E4%BD%95%E5%AE%89%E8%A3%85gobsidiantools/\r\n" +
                        "=========================================="
                );
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        saveDefaultConfig();
        getServer().getScheduler().scheduleDelayedRepeatingTask(new Timer(this), 0, 30 * 20);  //30s一次
    }
}

package com.gobsidian.nukkitTools;

import cn.nukkit.plugin.PluginBase;


public class Main extends PluginBase {
    public void onEnable() {
        getLogger().info("Tools is running! (http://tools.gobsidian.com) Version:");
        getLogger().info("By: luo123, Hateful_Carre1");
        saveDefaultConfig();
        getServer().getScheduler().scheduleDelayedRepeatingTask(new Timer(this), 0, 30 * 20);  //30s一次
    }
}

package com.luo123.nkplugin.CMSStools;

import cn.nukkit.plugin.PluginBase;


public class Main extends PluginBase {
    public void onEnable() {
        saveDefaultConfig();
        getServer().getScheduler().scheduleDelayedRepeatingTask(new Timer(this), 0, 30 * 20);  //30s一次
    }


}

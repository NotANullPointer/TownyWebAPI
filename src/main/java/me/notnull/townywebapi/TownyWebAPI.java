package me.notnull.townywebapi;

import me.notnull.townywebapi.server.TownyWebServer;
import org.bukkit.plugin.java.JavaPlugin;

public final class TownyWebAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            new TownyWebServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
    }
}

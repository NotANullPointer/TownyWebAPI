package me.notnull.townywebapi.server;

import com.google.gson.Gson;
import com.palmergames.bukkit.towny.db.TownyDataSource;
import me.notnull.townywebapi.TownyInterface;

public interface TownyRequest {

    TownyDataSource dataSource = new TownyInterface().getTownyDataSource();
    Gson gson = new Gson();

    String response(String... args);

}

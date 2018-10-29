package me.notnull.townywebapi;

import com.palmergames.bukkit.towny.db.TownyDataSource;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class TownyInterface {

    public TownyDataSource getTownyDataSource() {
        return TownyUniverse.getDataSource();
    }

}

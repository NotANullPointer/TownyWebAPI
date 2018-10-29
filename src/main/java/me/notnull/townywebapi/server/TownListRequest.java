package me.notnull.townywebapi.server;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TownListRequest implements TownyRequest {

    public String response(String... args) {
        List<TownListObject> townList =
                dataSource.getTowns().stream()
                        .sorted(Comparator.comparingInt(Town::getNumResidents))
                        .map(TownListObject::new)
                        .collect(Collectors.toList());
        return gson.toJson(new TownList(townList));
    }

    class TownList {
        int townNum;
        List<TownListObject> towns;

        TownList(List<TownListObject> towns) {
            this.towns = towns;
            this.townNum = towns.size();
        }
    }

    class TownListObject {
        String name;
        int residentsNum;
        String nation;

        TownListObject(Town town) {
            this.name = town.getName();
            this.residentsNum = town.getNumResidents();
            try {
                this.nation = town.getNation().getName();
            } catch (NotRegisteredException e) {
                this.nation = "";
            }
        }
    }

}

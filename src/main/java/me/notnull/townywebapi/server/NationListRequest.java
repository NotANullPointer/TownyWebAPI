package me.notnull.townywebapi.server;

import com.palmergames.bukkit.towny.object.Nation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NationListRequest implements TownyRequest {

    public String response(String... args) {
        List<NationListObject> nationList =
                dataSource.getNations().stream()
                        .sorted(Comparator.comparingInt(Nation::getNumTowns))
                        .map(NationListObject::new)
                        .collect(Collectors.toList());
        return gson.toJson(new NationList(nationList));
    }

    class NationList {
        int nationNum;
        List<NationListObject> nations;

        NationList(List<NationListObject> nations) {
            this.nations = nations;
            this.nationNum = nations.size();
        }
    }

    class NationListObject {
        String name;
        int members;

        NationListObject(Nation nation) {
            this.name = nation.getName();
            this.members = nation.getNumTowns();
        }
    }
}

package me.notnull.townywebapi.server;

import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NationDetailsRequest implements TownyRequest {

    private String error = "Can't find that nation";

    public String response(String... args) {
        Nation selected = null;
        try {
            selected = dataSource.getNation(args[0]);
        } catch (NotRegisteredException | IndexOutOfBoundsException e) {

        }
        return gson.toJson(selected != null ? new NationDetails(selected) : error);
    }

    class NationDetails {
        String nation;
        TownList towns;
        String tag;
        double balance;
        double taxes;
        List<BriefResidentDetails> residents;
        String capitalName;

        NationDetails(Nation nation) {
            this.nation = nation.getName();
            this.towns = new TownList(nation.getTowns().stream()
                    .sorted(Comparator.comparingInt(Town::getNumResidents))
                    .map(TownListObject::new)
                    .collect(Collectors.toList()));
            this.tag = nation.getTag();
            try {
                this.balance = nation.getHoldingBalance();
            } catch (EconomyException e) {
                this.balance = 0.0D;
            }
            this.taxes = nation.getTaxes();
            this.residents = nation.getResidents().stream()
                    .map(BriefResidentDetails::new)
                    .collect(Collectors.toList());
            this.capitalName = nation.getCapital().getName();
        }
    }

    class BriefResidentDetails {

        String name;
        String[] ranks;

        BriefResidentDetails(Resident res) {
            this.name = res.getName();
            this.ranks = res.getNationRanks().toArray(new String[0]);
        }
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

        TownListObject(Town town) {
            this.name = town.getName();
            this.residentsNum = town.getNumResidents();
        }
    }
}

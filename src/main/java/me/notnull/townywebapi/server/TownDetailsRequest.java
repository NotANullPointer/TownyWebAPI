package me.notnull.townywebapi.server;

import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import java.util.List;
import java.util.stream.Collectors;

public class TownDetailsRequest implements TownyRequest {

    private String error = "Can't find that town";

    public String response(String... args) {
        Town selected = null;
        try {
            selected = dataSource.getTown(args[0]);
        } catch (NotRegisteredException | IndexOutOfBoundsException e) {

        }
        return gson.toJson(selected != null ? new TownDetails(selected) : error);
    }

    class TownDetails {

        List<BriefResidentDetails> residents;
        double balance;
        double taxes;
        int plotNum;
        int plotNumMax;
        String town;
        String nation;
        int residentsNum;
        String tag;
        String board;
        String mayor;

        TownDetails(Town town) {
            this.residents =
                    town.getResidents().stream()
                            .map(BriefResidentDetails::new)
                            .collect(Collectors.toList());
            try {
                this.balance = town.getHoldingBalance();
            } catch (EconomyException e) {
                this.balance = 0.0D;
            }
            this.mayor = town.getMayor().getName();
            this.town = town.getName();
            this.taxes = town.getTaxes();
            this.plotNum = town.getTownBlocks().size();
            this.plotNumMax = town.getTotalBlocks();
            this.residentsNum = town.getNumResidents();
            this.tag = town.getTag();
            this.board = town.getTownBoard();
            try {
                this.nation = town.getNation().getName();
            } catch (NotRegisteredException e) {
                this.nation = "";
            }
        }

    }

    class BriefResidentDetails {

        String name;
        String[] ranks;

        BriefResidentDetails(Resident res) {
            this.name = res.getName();
            this.ranks = res.getTownRanks().toArray(new String[0]);
        }
    }

}

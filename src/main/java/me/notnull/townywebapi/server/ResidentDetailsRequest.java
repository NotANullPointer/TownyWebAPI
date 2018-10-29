package me.notnull.townywebapi.server;

import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;

public class ResidentDetailsRequest implements TownyRequest {

    private String error = "{\"error\": \"Can't find that nation\"}";

    public String response(String... args) {
        Resident selected = null;
        try {
            selected = dataSource.getResident(args[0]);
        } catch (NotRegisteredException | IndexOutOfBoundsException e) {

        }
        return gson.toJson(selected != null ? new ResidentDetails(selected) : error);
    }

    class ResidentDetails {
        String name;
        double balance;
        String townName;

        ResidentDetails(Resident res) {
            this.name = res.getName();
            try {
                this.balance = res.getHoldingBalance();
            } catch (EconomyException e) {
                this.balance = 0.0D;
            }
            try {
                this.townName = res.getTown().getName();
            } catch (NotRegisteredException e) {
                this.townName = "";
            }
        }
    }

}

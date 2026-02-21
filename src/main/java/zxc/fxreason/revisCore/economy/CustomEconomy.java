package zxc.fxreason.revisCore.economy;

/*
 * By fxreason
 * 21.02.2026
 **/

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomEconomy {

    private final Map<UUID, Double> balances = new HashMap<>();

    public double getBalance(UUID playerID) {
        return balances.getOrDefault(playerID, 0.0);
    }

    public boolean hasBalance(UUID playerID, double amount) {
        return getBalance(playerID) >= amount;
    }

    public void withdraw(UUID playerID, double amount) {
        double current = getBalance(playerID);
        balances.put(playerID, current - amount);
    }

    public void deposit(UUID playerID, double amount) {
        double current = getBalance(playerID);
        balances.put(playerID, current + amount);
    }

    public String format(Number amount) {
        return String.format("$%.2f", amount);
    }

    public String getCurrencyName() {
        return "RevisEconomy";
    }

}

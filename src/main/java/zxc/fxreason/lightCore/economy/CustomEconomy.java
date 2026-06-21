package zxc.fxreason.lightCore.economy;

/*
 * By fxreason
 * 21.02.2026
 **/

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CustomEconomy {

    private CustomEcoLogic customEcoLogic;

    private String currencySingular = "$";
    private String currencyPlural = "$";

    public CustomEconomy(CustomEcoLogic customEcoLogic) {
        this.customEcoLogic = customEcoLogic;
    }

    public void createAccount(UUID uuid) {
        if (!customEcoLogic.hasAccount(uuid)) {
            customEcoLogic.getBalances().put(uuid, customEcoLogic.getDefaultBalance().setScale(2, RoundingMode.HALF_EVEN));
        }
    }

    public BigDecimal getBalance(UUID uuid) {
        return customEcoLogic.getBalances().getOrDefault(uuid, BigDecimal.ZERO);
    }

    public boolean hasEnough(UUID uuid, BigDecimal amount) {
        return getBalance(uuid).compareTo(amount) >= 0;
    }

    public void deposit(UUID uuid, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) return;
        customEcoLogic.getBalances().merge(uuid, amount, BigDecimal::add);
        customEcoLogic.savePlayerData(uuid);
    }

    public boolean withdraw(UUID uuid, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) return false;
        BigDecimal current = getBalance(uuid);
        if (current.compareTo(amount) < 0) return false;

        customEcoLogic.getBalances().put(uuid, current.subtract(amount));
        customEcoLogic.savePlayerData(uuid);
        return true;
    }

    public boolean transfer(UUID from, UUID to, BigDecimal amount) {
        if (withdraw(from, amount)) {
            deposit(to, amount);
            return true;
        }
        return false;
    }

    public String format(BigDecimal amount) {
        double doubleAmount = amount.doubleValue();
        if (doubleAmount == 1.0) {
            return doubleAmount + " " + currencySingular;
        } else {
            return doubleAmount + " " + currencyPlural;
        }
    }

    public LinkedHashMap<UUID, BigDecimal> getTopBalances(int limit) {
        List<Map.Entry<UUID, BigDecimal>> list = new ArrayList<>(customEcoLogic.getBalances().entrySet());
        list.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        LinkedHashMap<UUID, BigDecimal> result = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(limit, list.size()); i++) {
            result.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return result;
    }

    public boolean hasAccount(UUID uuid) {
        return customEcoLogic.hasAccount(uuid);
    }

    public String getCurrencyPlural() {
        return currencyPlural;
    }

    public String getCurrencySingular() {
        return currencySingular;
    }
}

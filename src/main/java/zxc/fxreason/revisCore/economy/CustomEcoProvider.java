package zxc.fxreason.revisCore.economy;

/*
 * By fxreason
 * 21.02.2026
 **/

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.List;

public class CustomEcoProvider implements Economy {

    private final CustomEconomy economy;

    public CustomEcoProvider(CustomEconomy economy) {
        this.economy = economy;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "RevisEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return economy.format(BigDecimal.valueOf(amount));
    }

    @Override
    public String currencyNamePlural() {
        return economy.getCurrencyPlural();
    }

    @Override
    public String currencyNameSingular() {
        return economy.getCurrencySingular();
    }

    @Override
    public boolean hasAccount(String playerName) {
        return economy.hasAccount(Bukkit.getPlayerUniqueId(playerName));
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return economy.hasAccount(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return economy.hasAccount(Bukkit.getPlayerUniqueId(playerName));
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return economy.hasAccount(player.getUniqueId());
    }

    @Override
    public double getBalance(String playerName) {
        return economy.getBalance(Bukkit.getPlayerUniqueId(playerName)).doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return economy.getBalance(player.getUniqueId()).doubleValue();
    }

    @Override
    public double getBalance(String playerName, String world) {
        return economy.getBalance(Bukkit.getPlayerUniqueId(playerName)).doubleValue();
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return economy.getBalance(player.getUniqueId()).doubleValue();
    }

    @Override
    public boolean has(String playerName, double amount) {
        return economy.hasEnough(Bukkit.getPlayerUniqueId(playerName), BigDecimal.valueOf(amount));
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return economy.hasEnough(player.getUniqueId(), BigDecimal.valueOf(amount));
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Невозможно вывести отрицательную сумму");
        }

        if (!has(player, amount)) {
            return new EconomyResponse(0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Недостаточно средств");
        }

        BigDecimal bigAmount = BigDecimal.valueOf(amount);
        economy.withdraw(player.getUniqueId(), bigAmount);

        double newBalance = getBalance(player);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Deprecated
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Deprecated
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return depositPlayer(Bukkit.getPlayer(playerName), amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Пополнение не может быть отрицательным");
        }

        BigDecimal bigAmount = BigDecimal.valueOf(amount);
        economy.deposit(player.getUniqueId(), bigAmount);

        double newBalance = getBalance(player);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Deprecated
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Deprecated
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if (!hasAccount(player)) {
            economy.createAccount(player.getUniqueId());
            return true;
        }
        return false;
    }

    @Deprecated
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }

}
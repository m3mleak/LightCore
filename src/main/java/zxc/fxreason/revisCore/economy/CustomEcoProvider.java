package zxc.fxreason.revisCore.economy;

/*
 * By fxreason
 * 21.02.2026
 **/

/*import net.thenextlvl.service.api.economy.Account;
import net.thenextlvl.service.api.economy.EconomyController;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CustomEcoProvider implements EconomyController {

    private CustomEconomy customEconomy;

    public CustomEcoProvider(CustomEconomy customEconomy) {
        this.customEconomy = customEconomy;
    }

    @Override
    public String format(Number amount) {
        return "$";
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String getCurrencyNamePlural(Locale locale) {
        return "";
    }

    @Override
    public String getCurrencyNameSingular(Locale locale) {
        return "";
    }

    @Override
    public String getCurrencySymbol() {
        return "";
    }

    @Override
    public CompletableFuture<@Unmodifiable Set<Account>> loadAccounts() {
        return null;
    }

    @Override
    public @Unmodifiable Set<Account> getAccounts() {
        return Set.of();
    }

    @Override
    public Optional<Account> getAccount(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> getAccount(UUID uuid, World world) {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<Account> createAccount(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<Account> createAccount(UUID uuid, World world) {
        return null;
    }

    @Override
    public CompletableFuture<Optional<Account>> loadAccount(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<Optional<Account>> loadAccount(UUID uuid, World world) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteAccount(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> deleteAccount(UUID uuid, World world) {
        return null;
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
*/
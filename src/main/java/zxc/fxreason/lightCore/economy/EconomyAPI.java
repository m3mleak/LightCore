package zxc.fxreason.lightCore.economy;

/*
 * By fxreason
 * 23.02.2026
 **/

import java.util.UUID;

public interface EconomyAPI {

    public void loadPlayerData(UUID uuid);

    public void savePlayerData(UUID uuid);

    public void saveAllData();

    public boolean hasAccount(UUID uuid);

}

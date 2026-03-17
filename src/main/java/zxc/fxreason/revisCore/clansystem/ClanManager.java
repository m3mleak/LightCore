package zxc.fxreason.revisCore.clansystem;

import java.text.SimpleDateFormat;
import java.util.*;

public class ClanManager {

    private static Map<String, Clan> clans = new HashMap<>();
    private static Map<UUID, Clan> playerClans = new HashMap<>();

    public static boolean createClan(String name, String tag, UUID owner) {

        if (name == null || name.trim().isEmpty() || tag == null || owner == null) {
            return false;
        }

        if (clans.containsKey(name)) {
            return false;
        }

        if (playerClans.containsKey(owner)) {
            return false;
        }

        Clan clan = new Clan(name, tag, getCurrentDate(), owner);
        clans.put(name, clan);
        playerClans.put(owner, clan);

        return true;
    }

    public static boolean deleteClan(String name) {
        Clan clan = clans.remove(name);
        if (clan != null) {
            for (UUID memberId : clan.getMembers().keySet()) {
                playerClans.remove(memberId);
            }
            return true;
        }
        return false;
    }

    public static Clan getClan(String name) {
        return clans.get(name);
    }

    public static Clan getPlayerClan(UUID playerId) {
        return playerClans.get(playerId);
    }

    public static Collection<Clan> getAllClans() {
        return clans.values();
    }

    private static String getCurrentDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }


}

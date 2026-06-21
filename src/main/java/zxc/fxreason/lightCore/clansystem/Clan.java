package zxc.fxreason.lightCore.clansystem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Clan {

    private String name, tag, date;
    private UUID owner;
    private float balance;

    private final Map<UUID, String> members = new HashMap<>();

    public Clan(String name, String tag, String date, UUID owner) {
        this.name = name;
        this.tag = tag;
        this.date = date;
        this.owner = owner;
        this.balance = 0.0f;
        this.members.put(owner, "OWNER");
    }

    public void addMember(UUID playerID, String rank) {
        members.put(playerID, rank);
    }

    public void removeMember(UUID playerID) {
        members.remove(playerID);
    }

    // getters
    public UUID getOwner() {
        return owner;
    }

    public Map<UUID, String> getMembers() {
        return members;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public float getBalance() {
        return balance;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}

package de.minestar.Webpanel.web.resources;

public class KickJSON {

    private String playerName;
    private String reason;

    public KickJSON() {
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public String getPlayerName() {
        return playerName;
    }
}

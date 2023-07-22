package cloud.mateusz;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import cloud.mateusz.api.API;

public class PAPI extends PlaceholderExpansion {

    private final Main plugin;

    private final API api;

    public PAPI(Main plugin, API api) {
        this.plugin = plugin;
        this.api = api;
    }

    @Override
    public String getAuthor() {
        return "mateusz";
    }

    @Override
    public String getIdentifier() {
        return "vishop";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("top1")) {
            String dataFromAPI = api.getDataFromAPI();
            if (dataFromAPI == null) {
                return "&cWystąpił błąd, zajrzyj w console!";
            }

            String[] lines = dataFromAPI.split("\n");
            if (lines.length > 0) {
                return lines[0];
            } else {
                return "&cBrak danych.";
            }
        }
        if (params.equalsIgnoreCase("top2")) {
            String dataFromAPI = api.getDataFromAPI();
            if (dataFromAPI == null) {
                return "&cWystąpił błąd, zajrzyj w console!";
            }

            String[] lines = dataFromAPI.split("\n");
            if (lines.length > 1) {
                return lines[1];
            } else {
                return "&cBrak danych.";
            }
        }
        if (params.equalsIgnoreCase("top3")) {
            String dataFromAPI = api.getDataFromAPI();
            if (dataFromAPI == null) {
                return "&cWystąpił błąd, zajrzyj w console!";
            }

            String[] lines = dataFromAPI.split("\n");
            if (lines.length > 2) {
                return lines[2];
            } else {
                return "&cBrak danych.";
            }
        }
        return null;
    }
}

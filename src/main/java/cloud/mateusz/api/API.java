package cloud.mateusz.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cloud.mateusz.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API {

    private final Main plugin;
    private final FileConfiguration config;

    public API(Main plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
    }

    public String getDataFromAPI() {
        String serverID = config.getString("serverID", "");
        String messageTop = config.getString("messageTop", "");

        String apiUrl = "https://dev123.vishop.pl/panel/shops/" + serverID + "/richest_player/?amount=3";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                if (conn.getResponseCode() == 404) {
                    plugin.getLogger().severe("API zwróciło kod 404 (Not Found). Serwer API nie został odnaleziony. Sprawdź poprawność adresu API w ustawieniach vishop. Zmien serwerID w config.yml");
                } else if(conn.getResponseCode() == 403) {
                    plugin.getLogger().severe("API zwróciło kod 403 (Forbidden). Sprawdź poprawność widgetu w ustawieniach vishop.");
                } else {
                    plugin.getLogger().severe("Wystąpił problem z zapytaniem do API. Kod odpowiedzi: " + conn.getResponseCode());
                }
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONArray jsonArray = new JSONArray(response.toString());

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject playerData = jsonArray.getJSONObject(i);
                String playerName = playerData.getString("player");
                double playerSpend = playerData.getDouble("spend");

                result.append(messageTop.replace("{NICK}", playerName).replace("{KWOTA}", String.format("%.2f", playerSpend)) + "\n");
            }

            conn.disconnect();
            return result.toString();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

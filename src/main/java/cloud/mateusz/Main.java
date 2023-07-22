package cloud.mateusz;

import cloud.mateusz.api.API;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        String serverID = getConfig().getString("serverID", "");
        if (serverID.isEmpty()) {
            getLogger().severe("Brak uzupełnionego serverID w config.yml! Plugin zostanie wyłączony.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        API api = new API(this, getConfig());

        PAPI papi = new PAPI(this, api);

        papi.register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

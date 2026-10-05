package gay.vexruna.hostname;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RequireMinecraftHostname extends JavaPlugin implements Listener {

    private static final String REQUIRED_HOST = "mc.vexruna.gay";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("Hostname protection enabled.");
        getLogger().info("Required hostname: " + REQUIRED_HOST);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String hostname = event.getHostname();

        // Paper reports the hostname as "hostname:port".
        String host = hostname;

        int colon = hostname.lastIndexOf(':');
        if (colon > 0) {
            host = hostname.substring(0, colon);
        }

        getLogger().info(
                "Connection from " + event.getPlayer().getName()
                        + " using hostname: " + hostname
        );

        if (!REQUIRED_HOST.equalsIgnoreCase(host)) {
            event.disallow(
                    PlayerLoginEvent.Result.KICK_OTHER,
                    Component.text("Please connect using mc.vexruna.gay")
            );

            getLogger().info(
                    "Rejected connection from "
                            + event.getPlayer().getName()
                            + " using hostname: "
                            + hostname
            );
        }
    }
}

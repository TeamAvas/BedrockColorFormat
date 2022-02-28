package skh6075.bedrockcolorformat

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
import skh6075.bedrockcolorformat.utils.Colorize

class BedrockColorFormat : JavaPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerChatEvent(event: PlayerChatEvent) {
        event.format = Colorize.colorize(event.player, event.format)
    }
}
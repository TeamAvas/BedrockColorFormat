package skh6075.bedrockcolorformat.utils

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissionDefault
import java.util.regex.Pattern

internal object Colorize {
    private val colorMapOf = listOf(
            "&1", "&2", "&3", "&4", "&5", "&6", "&7",
            "&8", "&9", "&a", "&b", "&c", "&d", "&e",
            "&f", "&k", "&r", "&l", "&n"
    )

    fun colorize(player: Player, text: String): String {
        if (!text.contains("&")) {
            return defaultColor(player, text)
        }
        if (isOperator(player)) {
            return rgbHex(player, text)
        }
        return defaultColor(player, text)
    }

    /**
     * Delete all color codes of text
     * kor. 텍스트에 포함된 모든 색코드를 삭제한다.
     */
    fun defaultColor(player: Player, text: String): String {
        var new = text
        fun colorHandle(color: String) {
            new = if (isOperator(player)) {
                new.replace(color, color.replace("&", "§"))
            } else {
                new.replace(color, "")
            }
        }
        colorMapOf.forEach {
            colorHandle(it)
        }
        return new
    }

    /**
     * Changes the & sign to a color code.
     * kor. & 기호를 색코드로 바꿔준다.
     */
    fun rgbHex(player: Player, text: String): String {
        try {
            var string = text
            val pattern = Pattern.compile("#[a-fA-F0-9]{6}")
            var matcher = pattern.matcher(string)
            while (matcher.find()) {
                val color = string.substring(matcher.start(), matcher.end())
                string = string.replace(
                        color,
                        ChatColor.valueOf(color).toString() + ""
                )
                matcher = pattern.matcher(string)
            }
            string = ChatColor.translateAlternateColorCodes('&', string)
            return string
        } catch (e: ClassNotFoundException) {
            return defaultColor(player, text)
        } catch (e: NoClassDefFoundError) {
            return defaultColor(player, text)
        } catch (e: NoSuchMethodError) {
            return defaultColor(player, text)
        } catch (e: Throwable) {
            return defaultColor(player, text)
        }
    }

    private fun isOperator(player: Player): Boolean {
        return player.hasPermission(PermissionDefault.OP.toString())
    }
}
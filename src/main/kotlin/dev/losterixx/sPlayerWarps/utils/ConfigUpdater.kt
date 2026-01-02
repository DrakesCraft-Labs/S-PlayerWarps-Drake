package dev.losterixx.sPlayerWarps.utils

import dev.losterixx.sapi.utils.config.ConfigManager
import dev.losterixx.sPlayerWarps.Main

object ConfigUpdater {

    private val main = Main.instance

    private val configVersions = mapOf(
        "english" to 2,
        "german" to 2,
        "config" to 1,
        "mainMenu" to 1,
        "ownWarpsMenu" to 1,
        "editWarpMenu" to 1
    )

    fun updateConfigs() {
        configVersions.forEach { (configName, targetVersion) ->
            updateConfig(configName, targetVersion)
        }
    }

    private fun updateConfig(configName: String, targetVersion: Int) {
        val config = ConfigManager.getConfig(configName)
        val currentVersion = config.getInt("file-version", 1)

        if (currentVersion >= targetVersion) return

        main.logger.info("Updating $configName.yml from version $currentVersion to $targetVersion...")

        for (version in (currentVersion + 1)..targetVersion) {
            applyUpdate(configName, version)
        }

        config.set("file-version", targetVersion)
        config.save()
        main.logger.info("Successfully updated $configName.yml to version $targetVersion!")
    }

    private fun applyUpdate(configName: String, toVersion: Int) {
        when (configName) {
            "english", "german" -> updateLanguageFile(configName, toVersion)
            "config" -> updateConfigFile(toVersion)
            "mainMenu" -> updateMainMenuFile(toVersion)
            "ownWarpsMenu" -> updateOwnWarpsMenuFile(toVersion)
            "editWarpMenu" -> updateEditWarpMenuFile(toVersion)
        }
    }

    private fun updateLanguageFile(langName: String, toVersion: Int) {
        val config = ConfigManager.getConfig(langName)

        when (toVersion) {
            2 -> {
                val isGerman = langName == "german"

                if (!config.contains("commands.playerwarp.delete")) {
                    when {
                        isGerman -> {
                            config.set("commands.playerwarp.delete.success", "<gray>Der Spieler-Warp <white>%warp% <gray>wurde <green>erfolgreich gelöscht<gray>!")
                            config.set("commands.playerwarp.delete.notFound", "<gray>Der Spieler-Warp <white>%warp% <gray>konnte <red>nicht gefunden<gray> werden!")
                            config.set("commands.playerwarp.delete.notOwner", "<gray>Du <red>besitzt nicht <gray>den Spieler-Warp <white>%warp%<gray>!")
                        }
                        else -> {
                            config.set("commands.playerwarp.delete.success", "<gray>The player warp <white>%warp% <gray>has been <green>successfully deleted<gray>!")
                            config.set("commands.playerwarp.delete.notFound", "<gray>The player warp <white>%warp% <gray>could <red>not be found<gray>!")
                            config.set("commands.playerwarp.delete.notOwner", "<gray>You <red>do not own <gray>the player warp <white>%warp%<gray>!")
                        }
                    }
                }

                if (!config.contains("commands.playerwarp.edit")) {
                    when {
                        isGerman -> {
                            config.set("commands.playerwarp.edit.notFound", "<gray>Der Spieler-Warp <white>%warp% <gray>konnte <red>nicht gefunden<gray> werden!")
                            config.set("commands.playerwarp.edit.notOwner", "<gray>Du <red>besitzt nicht <gray>den Spieler-Warp <white>%warp%<gray>!")
                            config.set("commands.playerwarp.edit.displayNameSuccess", "<gray>Der Anzeigename des Spieler-Warps <white>%warp% <gray>wurde zu <white>%displayname% <reset><gray>geändert!")
                            config.set("commands.playerwarp.edit.displayNameTooLong", "<gray>Der Anzeigename ist <red>zu lang<gray>! Die maximale Länge ist <red>%max% <gray>Zeichen.")
                            config.set("commands.playerwarp.edit.iconSuccess", "<gray>Das Icon des Spieler-Warps <white>%warp% <gray>wurde zu <white>%material% <gray>geändert!")
                            config.set("commands.playerwarp.edit.invalidMaterial", "<gray>Das Material <white>%material% <gray>ist <red>ungültig<gray>! Bitte nutze einen gültigen Minecraft-Material-Namen.")
                        }
                        else -> {
                            config.set("commands.playerwarp.edit.notFound", "<gray>The player warp <white>%warp% <gray>could <red>not be found<gray>!")
                            config.set("commands.playerwarp.edit.notOwner", "<gray>You <red>do not own <gray>the player warp <white>%warp%<gray>!")
                            config.set("commands.playerwarp.edit.displayNameSuccess", "<gray>The display name of player warp <white>%warp% <gray>has been changed to <white>%displayname%<reset><gray>!")
                            config.set("commands.playerwarp.edit.displayNameTooLong", "<gray>The display name is <red>too long<gray>! Maximum length is <red>%max% <gray>characters.")
                            config.set("commands.playerwarp.edit.iconSuccess", "<gray>The icon of player warp <white>%warp% <gray>has been changed to <white>%material%<gray>!")
                            config.set("commands.playerwarp.edit.invalidMaterial", "<gray>The material <white>%material% <gray>is <red>invalid<gray>! Please use a valid Minecraft material name.")
                        }
                    }
                }

                when {
                    isGerman -> config.set("commands.playerwarp.usage", "<gray>Bitte nutze <red>/playerwarp <create/teleport/delete/edit> <Identifier><gray>!")
                    else -> config.set("commands.playerwarp.usage", "<gray>Please use <red>/playerwarp <create/teleport/delete/edit> <identifier><gray>!")
                }
            }
        }
    }

    private fun updateConfigFile(@Suppress("UNUSED_PARAMETER") toVersion: Int) {

    }

    private fun updateMainMenuFile(@Suppress("UNUSED_PARAMETER") toVersion: Int) {

    }

    private fun updateOwnWarpsMenuFile(@Suppress("UNUSED_PARAMETER") toVersion: Int) {

    }

    private fun updateEditWarpMenuFile(@Suppress("UNUSED_PARAMETER") toVersion: Int) {

    }
}


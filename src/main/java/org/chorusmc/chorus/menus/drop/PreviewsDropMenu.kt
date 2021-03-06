package org.chorusmc.chorus.menus.drop

import org.chorusmc.chorus.menus.drop.actions.previews.*
import org.chorusmc.chorus.util.translate

const val PREVIEWS_DROP_MENU_TYPE = "previews"

/**
 * @author Giorgio Garofalo
 */
class PreviewsDropMenu : DropMenu(PREVIEWS_DROP_MENU_TYPE) {

    override fun getButtons(): MutableList<DropMenuButton> = arrayListOf(
            DropMenuButton(translate("preview.chat"), ChatPreview()),
            DropMenuButton(translate("preview.title"), TitlePreview()),
            DropMenuButton(translate("preview.scoreboard"), ScoreboardPreview()),
            DropMenuButton(translate("preview.lore"), LorePreview()),
            DropMenuButton(translate("preview.gui"), GUIPreview()),
            DropMenuButton(translate("preview.sign"), SignPreview()),
            DropMenuButton(translate("preview.actionbar"), ActionBarPreview()),
            DropMenuButton(translate("preview.bossbar"), BossBarPreview()),
            DropMenuButton(translate("preview.mobbar"), MobBarPreview()),
            DropMenuButton(translate("preview.armor"), ArmorPreview()),
            DropMenuButton(translate("preview.motd"), MOTDPreview()),
            DropMenuButton(translate("preview.ban"), BanPreview()),
            DropMenuButton(translate("preview.animated_text"), AnimatedTextPreview())
    )
}
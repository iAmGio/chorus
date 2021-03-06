package org.chorusmc.chorus.minecraft.item

import javafx.application.Platform
import javafx.scene.image.Image
import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.infobox.fetchingText
import org.chorusmc.chorus.util.area
import org.chorusmc.chorus.util.makeFormal
import kotlin.concurrent.thread

/**
 * @author Giorgio Garofalo
 */
class ItemInformationBox(val image: Image?, private val item: Item) : InformationBox(InformationHead(image)) {

    init {
        val area = area!!
        val title = item.name.makeFormal()
        val subtitle = if(area.selectedText.contains(":") && area.selectedText.split(":")[1] != "0")
            area.selectedText.split(":")[1] else ""
        body = InformationBody(title, subtitle, fetchingText, item.connection.url)
    }

    override fun after() {
        thread {
            item.description.let { Platform.runLater { body.text = it } }
        }
    }
}
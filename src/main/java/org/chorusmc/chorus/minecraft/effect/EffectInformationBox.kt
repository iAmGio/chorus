package org.chorusmc.chorus.minecraft.effect

import javafx.application.Platform
import javafx.scene.image.Image
import org.chorusmc.chorus.infobox.InformationBody
import org.chorusmc.chorus.infobox.InformationBox
import org.chorusmc.chorus.infobox.InformationHead
import org.chorusmc.chorus.infobox.fetchingText
import org.chorusmc.chorus.util.makeFormal
import kotlin.concurrent.thread

/**
 * @author Giorgio Garofalo
 */
class EffectInformationBox(val image: Image, private val effect: Effect) : InformationBox(InformationHead(image)) {

    init {
        val title = effect.name.makeFormal()
        val subtitle = effect.id.toString()
        body = InformationBody(title, subtitle, fetchingText, effect.connection.url)
    }

    override fun after() {
        thread {
            effect.description.let { Platform.runLater { body.text = it } }
        }
    }
}
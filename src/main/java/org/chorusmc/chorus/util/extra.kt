package org.chorusmc.chorus.util

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Label
import javafx.scene.text.TextFlow
import org.chorusmc.chorus.menus.coloredtextpreview.FlowList

fun <T> List<T>.toObservableList(): ObservableList<T> = FXCollections.observableList(this)
fun <T : TextFlow> List<T>.toFlowList(): FlowList {
    val flowList = FlowList()
    flowList.addAll(this)
    return flowList
}

fun String.makeFormal(): String = toLowerCase().replace("_", " ").capitalize()

fun String.maxTo(max: Int): String = if(length > max) substring(0, max) else this

fun Class<out Enum<*>>.valueOf(string: String): Enum<*> = enumConstants.first {it.toString() == string}

val TextFlow.text: String
    get() {
        var s = ""
        children.forEach {
            if(it is Label) s += it.text
        }
        return s
    }

/*
Allows to add style classes inside of constructors
 */
fun TextFlow.withStyleClass(styleClass: String): TextFlow {
    this.styleClass += styleClass
    return this
}
package org.chorusmc.chorus.menus.drop.actions.previews

import javafx.scene.control.Spinner
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.layout.Region
import org.chorusmc.chorus.Chorus
import org.chorusmc.chorus.editor.EditorArea
import org.chorusmc.chorus.menus.coloredtextpreview.ColoredTextPreviewMenu
import org.chorusmc.chorus.menus.coloredtextpreview.previews.ColoredTextPreviewImage
import org.chorusmc.chorus.menus.coloredtextpreview.previews.GUIPreviewImage
import org.chorusmc.chorus.menus.drop.actions.DropMenuAction
import org.chorusmc.chorus.menus.insert.InsertMenu
import org.chorusmc.chorus.minecraft.chat.ChatParser
import org.chorusmc.chorus.minecraft.item.Item
import org.chorusmc.chorus.nodes.popup.LocalTextPopup
import org.chorusmc.chorus.util.colorPrefix
import org.chorusmc.chorus.util.makeFormal

/**
 * @author Gio
 */
class GUIPreview : DropMenuAction() {

    override fun onAction(area: EditorArea, x: Double, y: Double) {
        val textfield = TextField(
                if(area.selectedText.startsWith(colorPrefix)) area.selectedText else colorPrefix + "8" + area.selectedText
        )
        textfield.promptText = "Title"
        val rows = Spinner<Int>(1, 6, 1)
        val image = GUIPreviewImage(textfield.text, rows.value)
        val grid = Grid()
        updateMembers(grid, rows.value, image)
        val menu = ColoredTextPreviewMenu("GUI preview", image, listOf(textfield, rows))
        textfield.textProperty().addListener {_ ->
            menu.image.flows[0] = ChatParser(textfield.text, true).toTextFlow()
            updateMembers(grid, rows.value, image)
        }
        rows.valueProperty().addListener {_ ->
            updateMembers(grid, rows.value, image)
            menu.image.background.image = Image(Chorus::class.java.getResourceAsStream("/assets/minecraft/previews/gui-${rows.value}.png"))
        }
        menu.layoutX = x
        menu.layoutY = y
        menu.show()
    }
}

private fun updateMembers(grid: Grid, rows: Int, image: ColoredTextPreviewImage) {
    image.children.removeAll(grid.members)
    grid.rows = rows
    grid.updateMembers()
    image.children.addAll(grid.members)
}

private class Grid {

    var members = mutableListOf<GridMember>()

    var rows = 1
    private val columns = 9

    fun updateMembers() {
        members.clear()
        var n = 0
        val pass = 36.0
        var y = 26.0
        for(i in 0 until rows) {
            var x = 8.0
            for(j in 0 until columns) {
                val member = GridMember(n, j, i)
                member.layoutX = x
                member.layoutY = y
                members.add(member)
                x += pass
                n++
            }
            y += pass
        }
    }
}

private class GridMember(n: Int, x: Int, y: Int) : Region() {

    private val centerX: Double
        get() = layoutX + prefWidth / 2

    private val centerY: Double
        get() = layoutY + prefHeight / 2

    var item: Item? = null

    init {
        prefWidth = 34.0
        prefHeight = 34.0
        val popup = LocalTextPopup("Slot: $n, X:$x, Y:$y")
        popup.layoutX = centerX - prefWidth
        popup.layoutY = centerY - 35
        setOnMouseEntered {
            children += popup
            style = "-fx-background-color: rgba(255, 255, 255, .2)"
        }
        setOnMouseExited {
            children -= popup
            style = ""
        }
        setOnMouseClicked {
            showingMenu?.hide()
            if(it.button == MouseButton.PRIMARY) {
                @Suppress("UNCHECKED_CAST")
                val menu = InsertMenu(Item::class.java as Class<Enum<*>>)
                if(item != null) {
                    menu.textField.text = item!!.name.makeFormal()
                }
                menu.layoutX = layoutX + 40
                menu.layoutY = layoutY
                menu.setOnSelect {
                    children.removeAll(children.filterIsInstance<ImageView>())
                    item = Item.valueOf(menu.selected.toUpperCase().replace(" ", "_"))
                    children += ImageView(item!!.icons[0])
                }
                menu.show()
                showingMenu = menu
            } else {
                item = null
                children.removeAll(children.filterIsInstance<ImageView>())
            }
        }
    }

    private companion object {
        var showingMenu: InsertMenu? = null
    }
}
package org.chorusmc.chorus.file

import com.jcraft.jsch.ChannelSftp
import org.chorusmc.chorus.connection.SFTPRemoteConnection
import org.chorusmc.chorus.util.getText
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * @author Giorgio Garofalo
 */
class SFTPFile(private val connection: SFTPRemoteConnection, private val path: String) : ChorusFile {

    private val channel = connection.channel as ChannelSftp
    private val file = channel[path]

    override val type = "SFTP"

    override val name: String
        get() = path.split("/").last()

    override val absolutePath: String
        get() = path

    override val parentName: String
        get() {
            val parts = path.split("/")
            return if(parts.size >= 2) parts[parts.size - 2] else ""
        }

    override val text: String
        get() = getText(file)

    override val updatedFile: ChorusFile
        get() = SFTPFile(connection, path)

    override var closed: Boolean = false

    override fun save(text: String): Boolean {
        return try {
            val stream = ByteArrayOutputStream()
            stream.write(text.toByteArray())
            channel.put(ByteArrayInputStream(stream.toByteArray()), path, ChannelSftp.OVERWRITE)
            true
        } catch(e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun close() {
        closed = true
        file.close()
        connection.session.disconnect()
    }
}
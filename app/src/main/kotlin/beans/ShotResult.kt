package beans

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ShotResult(
    val x: Float?,
    val y: Float?,
    val r: Float?,
    val isHit: Boolean?,
    @Transient var timestamp: LocalDateTime = LocalDateTime.now()
): Serializable {

    private fun writeObject(out: ObjectOutputStream) {
        out.defaultWriteObject()
        val timestampString = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        out.writeUTF(timestampString)
    }

    private fun readObject(`in`: ObjectInputStream) {
        `in`.defaultReadObject()
        val timestampString = `in`.readUTF()
        timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    fun getFormattedTimestamp(): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return timestamp.format(formatter)
    }
}
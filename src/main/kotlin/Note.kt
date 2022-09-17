import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val ownerId: Int,
    val title: String,
    val text: String,
    val privacy: Byte = 0,
    val privacyComment: Byte = 0,
    val privacyView: String = "all",
    val privacyViewComment: String = "all"
) : NotesCrud<Note> {
    private var id: Int
    private var date: Int
    var comments: Int = 0
    var readComments: Int = 0

    init {
        Enumerator.countNotes += 1
        id = Enumerator.countNotes
        sleep(kotlin.random.Random.nextInt(5000).toLong())
        date = (System.currentTimeMillis() / 1000).toInt()
        comments = 0
        readComments = 0
    }

    fun fillOutOf(noteChanged: Note): Note {
        val noteAfter: Note = noteChanged
        noteAfter.date = this.date
        noteAfter.id = this.id
        noteAfter.comments = this.comments
        noteAfter.readComments = this.readComments
        return noteAfter
    }

    fun getDate(): String =
        SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(date.toLong() * 1000))

    fun getDateUnixTime() = date
    fun getId() = id
    fun output(): String {
        return "ID: $id, Date:${getDate()}, " + this.toString() + ". Comment: $comments ($readComments прочитано)"
    }
}
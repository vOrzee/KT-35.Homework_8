import java.text.SimpleDateFormat
import java.util.*

data class CommentNote(
    val fromId: Int,
    val noteId: Int,
    var text: String,
    val answerTo: Long = -1, //Ссылка на ID комментария, которому отвечают
    val isDeleted: Boolean = false
) {
    private val id: Long
    private var date: Int? = null

    companion object {
        private var count: Long = 0
    }

    init {
        count += 1
        id = count
        date = (System.currentTimeMillis() / 1000).toInt()
    }

    fun getDate(): String =
        if (date != null) SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(((date ?: 0) * 1000).toLong()))
        else "Запись ещё не опубликована"

    fun getID() = id
    fun getDateUnixTime() = date
}

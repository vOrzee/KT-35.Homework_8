import attachments.Attachment
import java.text.SimpleDateFormat
import java.util.*

data class CommentBoard(
    val fromID: Int,
    var text: String,
    var attachments: Attachment? = null,
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

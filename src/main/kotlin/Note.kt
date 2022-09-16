import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val ownerId:Int,
    var tittle:String,
    var text:String,
    var comments:Int = 0,
    var readComments:Int = 0,
    val viewUrl:String = "BK.KOM/NOTES",
    var privacyView:Array<String> = arrayOf("Public"),
    var privacyComment:Array<String> = arrayOf("Public"),
    var canComment:Boolean = true,
    val textWiki:String = "LINK"
){
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
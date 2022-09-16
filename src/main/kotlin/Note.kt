package ru.netology.notes
import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val ownerId:Int,
    var tittle:String,
    var text:String,
    val viewUrl:String = "BK.KOM/NOTES",
    var privacyView:String = "Public",
    var canComment:Boolean = true,
    val textWiki:String = "LINK"
){
    private val id: Int
    private var date: Int? = null
    private var comments:Int = 0
    private var readComments:Int = 0

    companion object {
        private var count: Int = 0
    }

    init {
        count += 1
        id = count
        date = (System.currentTimeMillis() / 1000).toInt()
        comments = 0
        readComments = 0
    }

    fun getDate(): String =
        if (date != null) SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(((date ?: 0) * 1000).toLong()))
        else "Запись ещё не опубликована"

    fun getID() = id
    fun getDateUnixTime() = date
}
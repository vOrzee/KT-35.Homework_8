package ru.netology.notes

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
    val id: Int
    private val date: Int
    var comments: Int = 0
    var readComments: Int = 0

    companion object {
        private var count: Int = 0
    }

    init {
        count += 1
        id = count
        sleep(kotlin.random.Random.nextInt(5000).toLong())
        date = (System.currentTimeMillis() / 1000).toInt()
        comments = 0
        readComments = 0
    }

    fun getDate(): String =
        SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(date.toLong() * 1000))

    fun getDateUnixTime() = date

    fun output():String{
        return "ID: $id, Date:${getDate()}, " + this.toString() + ". Comment: $comments ($readComments прочитано)"
    }
}
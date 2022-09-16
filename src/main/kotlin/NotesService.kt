import ru.netology.notes.*

object NotesService {
    var storage:MutableMap<Note,MutableList<CommentNote>> = mutableMapOf()
    val ownerId:Int = 734

    fun add(title:String,
            text:String,
            privacy:Byte = 0,
            commentPrivacy:Byte = 0,
            privacyView:String = "all",
            privacyComment:String = "all"
    ):Int {
        val note = Note(this.ownerId,title,text,privacy,commentPrivacy,privacyView,privacyComment,false)
        note.add(note, storage.keys)
        return storage.keys.find { it==note }?.getID() ?: throw NotFoundException()
    }

    fun createComment(
        noteId:Int,
        message:String,
        ownerId:Int = this.ownerId,
        replyTo:Long = -1,
        guid:String = System.currentTimeMillis().toString()
    ):Long{
        val note = storage.keys.find { it.getID()==noteId } ?: throw NotFoundException()
        val comment = CommentNote(ownerId,noteId,message,replyTo)
        storage[note]?.add(comment)
        return comment.getID()
    }

//    delete
//    Удаляет заметку текущего пользователя.

//    deleteComment
//    Удаляет комментарий к заметке.

//    edit
//    Редактирует заметку текущего пользователя.

//    editComment
//    Редактирует указанный комментарий у заметки.

//    get
//    Возвращает список заметок, созданных пользователем.

//    getById
//    Возвращает заметку по её id.

//    getComments
//    Возвращает список комментариев к заметке.

//    getFriendsNotes
//    Возвращает список заметок друзей пользователя.

//    restoreComment
//    Восстанавливает удалённый комментарий.
}
object NotesService {
    var storage: MutableMap<Note, MutableList<CommentNote>> = mutableMapOf()
    var thisUserId: Int = 734 //Значение по умолчанию

    fun add(
        title: String,
        text: String,
        ownerId: Int = thisUserId,
        privacy: Byte = 0,
        commentPrivacy: Byte = 0,
        privacyView: String = "all",
        privacyComment: String = "all"
    ): Int { // Создает новую заметку у текущего пользователя.
        val note = Note(ownerId, title, text, privacy, commentPrivacy, privacyView, privacyComment)
        storage[note] = mutableListOf()
        return storage.keys.find { it == note }?.getId() ?: throw NotFoundException()
    }

    fun createComment(
        noteId: Int,
        message: String,
        ownerId: Int = thisUserId,
        replyTo: Long = -1
    ): Long { // Добавляет новый комментарий к заметке.
        // Параметр guid: String не используется
        val note = storage.keys.find { it.getId() == noteId } ?: throw NotFoundException()
        val comment = CommentNote(ownerId, noteId, message, replyTo)
        storage[note]?.add(comment)
        return comment.id
    }

    fun delete(noteId: Int): Int { // Удаляет заметку текущего пользователя.
        val note = storage.keys.find { it.getId() == noteId } ?: return 0
        if (note.ownerId != thisUserId) throw AccessDeniedException("Access denied")
        note.delete(note, storage.keys)
        if (storage.containsKey(note)) return 0
        return 1
    }

    fun deleteComment(commentId: Long, ownerId: Int = thisUserId): Int { // Удаляет комментарий к заметке.
        storage.values.forEach { commentsForNote ->
            val s = commentsForNote.find { it.id == commentId }
            if (s != null) {
                commentsForNote.find { it.id == commentId }?.also {
                    if (it.fromId == ownerId) it.isDeleted = true
                    else throw AccessDeniedException("Это не Ваш комментарий")
                }
                return 1
            }
        }
        return 0
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacy: Byte = 0,
        commentPrivacy: Byte = 0,
        privacyView: String = "all",
        privacyComment: String = "all"
    ): Int { // Редактирует заметку текущего пользователя.
        val oldContent = storage.keys.find { it.getId() == noteId } ?: return 0
        val comments = storage[oldContent]
        val newContent = oldContent.fillOutOf(
            Note(
                ownerId = thisUserId,
                title = title,
                text = text,
                privacy = privacy,
                privacyComment = commentPrivacy,
                privacyView = privacyView,
                privacyViewComment = privacyComment
            )
        )
        storage.remove(oldContent)
        storage[newContent] = comments ?: mutableListOf()
        return 1
    }

    fun editComment(
        commentId: Long,
        message: String,
        ownerId: Int = thisUserId
    ): Int { // Редактирует указанный комментарий у заметки.
        storage.values.forEach { commentsForNote ->
            val s = commentsForNote.find { it.id == commentId }
            if (s != null) {
                commentsForNote.find { it.id == commentId }?.also {
                    if (it.fromId == ownerId) it.text = message
                    else throw AccessDeniedException("Это не Ваш комментарий")
                }
                return 1
            }
        }
        return 0
    }

    fun get(
        userId: Int? = null,
        offset: Int = 0,
        count: Int = 50,
        isAscendingSort: Boolean = true,
        vararg noteIds: Int? = emptyArray(),
    ): List<Note> { // Возвращает список заметок, созданных пользователем.
        var result = storage.keys
        if (noteIds.isEmpty() && userId == null) return emptyList()
        if (userId != null) {
            result = result.filter { it.ownerId == userId }.toMutableSet()
        }
        if (noteIds.isNotEmpty()) {
            result = result.filter { noteIds.contains(it.getId()) }.toMutableSet()
        }
        if (!isAscendingSort) result.sortedByDescending { it.getDateUnixTime() }
        else result.sortedBy { it.getDateUnixTime() }
        return result.filterIndexed { index, _ -> index < count + offset }
    }

    fun getById(noteId: Int, ownerId: Int): Note { // Возвращает заметку по её id
        val result = storage.keys.find { it.getId() == noteId } ?: throw NotFoundException()
        return if (result.privacyView == "all" || result.ownerId == ownerId) result
        else throw AccessDeniedException("Access denied")
    }

    fun getComments(
        noteId: Int,
        isAscendingSort: Boolean = true,
        offset: Int = 0,
        count: Int = 50
    ): Array<CommentNote> { // Возвращает список комментариев к заметке.
        //Идентификатор ownerId здесь опущен, т.к. его можно взять из заметки
        val note = storage.keys.find { it.getId() == noteId } ?: throw NotFoundException("Заметка не найдена")
        val comments = storage[note] ?: return emptyArray()
        if (!isAscendingSort) comments.sortedByDescending { it.getDateUnixTime() }
        else comments.sortedBy { it.getDateUnixTime() }
        return comments.filterIndexed { index, _ -> index < count + offset }.toTypedArray()
    }


    /* Следующий метод устарел:
        fun getFriendsNotes(offset:UInt, count:UInt) // Возвращает список заметок друзей пользователя.
    */

    fun restoreComment(commentId: Long, ownerId: Int = thisUserId): Int { // Восстанавливает удалённый комментарий.
        storage.values.forEach { commentsForNote ->
            val s = commentsForNote.find { it.id == commentId }
            if (s != null) {
                commentsForNote.find { it.id == commentId }?.also {
                    if (it.fromId == ownerId) it.isDeleted = false
                    else throw AccessDeniedException("Это не Ваш комментарий")
                }
                return 1
            }
        }
        return 0
    }

    fun clean() {
        storage.clear() // Каждый раз пересоздавать "тяжеловато" на тестах
        thisUserId = 734 // Значение по умолчанию
    }
}
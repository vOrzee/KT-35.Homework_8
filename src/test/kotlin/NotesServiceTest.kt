import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

class NotesServiceTest {
    var thisUserId: Int = NotesService.thisUserId
    var otherOwnerId: Int = NotesService.thisUserId


    @Before
    fun launch() {
        NotesService.clean()
        Enumerator.clear()
        while (otherOwnerId == thisUserId) {
            otherOwnerId = Random.nextInt(1000)
        }
        NotesService.add("Первая заметка", "Текст в заметке №1")
        NotesService.add("Вторая заметка", "Текст в заметке №2")
        NotesService.add("Третья заметка", "Текст в заметке №3")
        NotesService.add("Четвёртая заметка", "Текст в заметке №4", otherOwnerId)
        NotesService.add("Пятая заметка", "Текст в заметке №5", otherOwnerId, privacyView = "not for all")

        NotesService.createComment(1, "1-ый комментарий для 1-го поста")
        NotesService.createComment(1, "2-ой комментарий для 1-го поста")
        NotesService.createComment(3, "1-Ый комментарий для 3-го поста", otherOwnerId)
    }

    @Test
    fun addSuccess() {
        assertEquals(6, NotesService.add("Шестая заметка", "Текст в заметке №7", otherOwnerId))
    }

    @Test
    fun createCommentSuccess() {
        assertEquals(4, NotesService.createComment(2, "Комментарий ко 2-ой заметке"))
    }

    @Test(expected = NotFoundException::class)
    fun createCommentFailed() {
        NotesService.createComment(6, "Комментарий ко 2-ой заметке")
    }

    @Test
    fun deleteSuccess() {
        assertEquals(1, NotesService.delete(2))
    }

    @Test
    fun deleteFailed() {
        assertEquals(0, NotesService.delete(6))
    }

    @Test(expected = AccessDeniedException::class)
    fun deleteFailedAccess() {
        NotesService.thisUserId = 5
        NotesService.delete(2)
    }

    @Test
    fun deleteCommentSuccess() {
        assertEquals(1, NotesService.deleteComment(3))
    }

    @Test
    fun deleteCommentFailed() {
        assertEquals(0, NotesService.deleteComment(4))
    }

    @Test(expected = AccessDeniedException::class)
    fun deleteCommentFailedAccess() {
        NotesService.deleteComment(2, otherOwnerId)
    }

    @Test
    fun editSuccess() {
        NotesService.edit(3, "Новый заголовок 3 заметки", "Текст третьей заметки")
        val notes = NotesService.get(noteIds = arrayOf(3))
        assertEquals("Текст третьей заметки", notes[0].text)
    }

    @Test
    fun editFailed() {
        assertEquals(0, NotesService.edit(8, "Новый заголовок 3 заметки", "Текст третьей заметки"))
    }

    @Test
    fun editCommentSuccess() {
        NotesService.createComment(4, "Обычный комментарий")
        assertEquals(1, NotesService.editComment(4, "Изменённый комментарий"))
    }

    @Test
    fun editCommentFailed() {
        assertEquals(0, NotesService.editComment(4, "Изменённый комментарий"))
    }

    @Test(expected = AccessDeniedException::class)
    fun editCommentFailedAccess() {
        NotesService.createComment(4, "Обычный комментарий", otherOwnerId)
        NotesService.editComment(4, "Изменённый комментарий")
    }

    @Test
    fun getSuccess() {
        assertEquals("Первая заметка", NotesService.get(noteIds = arrayOf(1))[0].title)
    }

    @Test
    fun getSuccessForUser() {
        assertTrue(NotesService.get(thisUserId).isNotEmpty())
    }

    @Test
    fun getFailed() {
        assertEquals(0, NotesService.get(noteIds = arrayOf(15, 89)).size)
    }

    @Test
    fun getByIdSuccess() {
        assertEquals("Вторая заметка", NotesService.getById(2, thisUserId).title)
    }

    @Test(expected = NotFoundException::class)
    fun getByIdFailedNF() {
        NotesService.getById(15, thisUserId)
    }

    @Test(expected = AccessDeniedException::class)
    fun getByIdFailedAD() {
        NotesService.getById(5, thisUserId)
    }


    @Test
    fun getCommentsSuccess(){
        assertEquals(2,NotesService.getComments(1).size)
    }
    @Test
    fun getCommentsSuccessIsEmpty(){
        assertEquals(0,NotesService.getComments(2).size)
    }
    @Test
    fun getCommentsSuccessIsFullStack(){
        for (i in 1..60){
            NotesService.createComment(2,"Комментарий №$i")
        }
        assertEquals(50, NotesService.getComments(2).size)
    }
    @Test(expected = NotFoundException::class)
    fun getCommentsFailedNotFound(){
        NotesService.getComments(8)
    }
    @Test
    fun restoreCommentSuccess() {
        NotesService.deleteComment(2)
        assertTrue(NotesService.getComments(1).find { it.id==2L }?.isDeleted ?: false)
        NotesService.restoreComment(2)
        val check = NotesService.getComments(1).find { it.id==2L }?.isDeleted ?: true
        assertTrue(!check)
    }

    @Test
    fun restoreCommentFailed() {
        assertEquals(0, NotesService.restoreComment(10))
    }

    @Test(expected = AccessDeniedException::class)
    fun restoreCommentFailedAccess() {
        NotesService.restoreComment(3)
    }
    @Test
    fun cleaning() {
        NotesService.clean()
        Enumerator.clear()
    }

}
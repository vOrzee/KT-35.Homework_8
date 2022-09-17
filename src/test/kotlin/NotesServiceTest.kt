import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import kotlin.random.Random

class NotesServiceTest {
    private val thisUserId: Int = NotesService.thisUserId
    var otherOwnerId: Int = NotesService.thisUserId

    @Before
    fun prepare() {
        NotesService.clean()
        while (otherOwnerId == thisUserId) {
            otherOwnerId = Random.nextInt(1000)
        }
        NotesService.add("Первая заметка", "Текст в заметке №1")
        NotesService.add("Вторая заметка", "Текст в заметке №2")
        NotesService.add("Третья заметка", "Текст в заметке №3")
        NotesService.add("Четвёртая заметка", "Текст в заметке №4", otherOwnerId)
        NotesService.add("Пятая заметка", "Текст в заметке №5", otherOwnerId)

        NotesService.createComment(1, "1-ый комментарий для 1-го поста")
        NotesService.createComment(1, "2-ой комментарий для 1-го поста")
        NotesService.createComment(3, "1-Ый комментарий для 3-го поста")
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
    fun cleaning() {
        NotesService.clean()
    }
}
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class NotesServiceTest {
    private val thisUserId: Int = NotesService.THIS_USER_ID
    var otherOwnerId: Int = NotesService.THIS_USER_ID

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
    fun addSuccess(){
        assertEquals(6,NotesService.add("Шестая заметка", "Текст в заметке №7", otherOwnerId))
    }

    @Test
    fun createCommentSuccess(){
        assertEquals(4,NotesService.createComment(2,"Комментарий ко 2-ой заметке"))
    }

    @Test (expected = NotFoundException::class)
    fun createCommentFailed(){
        assertEquals(4,NotesService.createComment(6,"Комментарий ко 2-ой заметке"))
    }


    @Test
    fun cleaning() {
        NotesService.clean()
    }
}
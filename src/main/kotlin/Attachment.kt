package attachments
import java.util.Objects

sealed interface Attachment {
    val type: String
    fun showAttachmentInPage() {
        println("Type attachment: $type")
    }
}

class PhotoAttachment(
    override val type: String = "photo",
    val photo: Photo
) : Attachment {
    override fun showAttachmentInPage() {
        super.showAttachmentInPage()
        println(photo)
    }
}

data class Photo(
    val id: Int,
    val albumId: Int,
    val ownerId: Int,
    val userId: Int,
    val text: String,
    val date: Int,
    val sizes: Array<SizesPhoto>
)

data class SizesPhoto(val type: String, val url: String, val width: Int, val height: Int)

class AudioAttachment(
    override val type: String = "audio",
    val audio: Audio
) : Attachment {
    override fun showAttachmentInPage() {
        super.showAttachmentInPage()
        println(audio)
    }
}

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val tittle: String,
    val duration: Int,
    val url: String,
    val lyricsId: Int?,
    val albumId: Int?,
    var genreId: Int,
    val date: Int,
    val noSearch: Boolean,
    val isHq: Boolean
)


class GraffitiAttachment(
    override val type: String = "graffiti",
    val graffiti: Graffiti
) : Attachment {
    override fun showAttachmentInPage() {
        super.showAttachmentInPage()
        println(graffiti)
    }
}

data class Graffiti(
    val id: Int,
    val ownerId: Int,
    val photo130: String,
    val photo604: String
)

class LinkAttachment(
    override val type: String = "link",
    val link: Link
) : Attachment {
    override fun showAttachmentInPage() {
        super.showAttachmentInPage()
        println(link)
    }
}

data class Link(
    val url: String,
    val title: String,
    val caption: String?,
    val description: String,
    val photo: Photo?,
    val product: Any?,
    val button: Any?,
    val previewPage: String,
    val previewUrl: String,
)

class AlbumAttachment(
    override val type: String = "document",
    val album: Album
) : Attachment {
    override fun showAttachmentInPage() {
        super.showAttachmentInPage()
        println(album)
    }
}

data class Album(
    val id: Int,
    val thumb: Photo,
    val ownerId: Int,
    val title: String,
    val description: String,
    val created: Int,
    val updated: Int,
    val size: Int
)

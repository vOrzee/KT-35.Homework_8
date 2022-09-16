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
    val text_wiki:String = "LINK"
)
object Enumerator {
    var countNotes: Int = 0
    var countCommentsNote: Long = 0
    fun clear() {
        /* Основной задачей счётчика является обеспечение уникальности значения,
            поэтому его сброс нежелателен в реальных условиях
         */
        countNotes = 0
        countCommentsNote = 0
    }
}
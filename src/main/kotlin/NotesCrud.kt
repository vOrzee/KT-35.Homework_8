package ru.netology.notes
import NotFoundException
import PublishedBeforeException

sealed interface NotesCrud<T>{

    fun add(content: T, collection: MutableList<T>): T {
        if(collection.contains(content)) throw PublishedBeforeException()
        collection.add(content)
        return collection.last()
    }
    fun get(collection: MutableList<T>): MutableList<T> {
        return collection
    }
    fun edit(content: T, collection: MutableList<T>): Boolean {
        if(!collection.contains(content)) return false
        var indexOf:Int? = null
        collection.forEachIndexed { index, t ->
            if (t==content) indexOf = index
        }
        collection[indexOf ?: return false] = content
        return true
    }
    fun delete(content: T, collection: MutableList<T>): Boolean {
        if(!collection.contains(content)) return false
        collection.remove(content)
        return true
    }
}
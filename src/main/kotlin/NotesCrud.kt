package ru.netology.notes
import NotFoundException
import PublishedBeforeException

sealed interface NotesCrud<T>{

    fun add(content: T, collection: MutableCollection<T>): Boolean {
        if(collection.contains(content)) return false
        collection.add(content)
        return true
    }
    fun get(collection: MutableCollection<T>): MutableCollection<T> {
        return collection
    }
    fun edit(oldContent: T, newContent:T, collection: MutableCollection<T>): Boolean {
        if(!collection.contains(oldContent)) return false
        collection.remove(oldContent)
        collection.add(newContent)
        return true
    }
    fun delete(content: T, collection: MutableCollection<T>): Boolean {
        if(!collection.contains(content)) return false
        collection.remove(content)
        return true
    }

}
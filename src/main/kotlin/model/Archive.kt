package model

data class Archive(
    val name: String,
    val notesMap: MutableMap<Int, Note>? = mutableMapOf()
)

import model.Archive
import model.Note
import java.lang.Exception
import java.util.*

class Screen {
    private var scanner = Scanner(System.`in`)
    private var CounterArchives: Int = 0
    private var CounterNotes: Int = 0
    private var archivesMap: MutableMap<Int, Archive> = mutableMapOf()
    private var archiveVariable: Int = 0
    private var noteVariable: Int = 0

    fun showMenu() {
        println("0. Создать архив")
        println("1. Выбрать архив")
        println("2. Выход")
        when(checkInput(2)){
            0 -> CurrentState.state = Command.ARCHIVE_CREATE
            1 -> CurrentState.state = Command.ARCHIVE_CHOOSE
            2 -> CurrentState.state = Command.EXIT
        }
    }

    fun createArchive() {
        println("Введите название нового архива:")
        val name = readInput()
        CounterArchives += 1
        archivesMap[CounterArchives] = Archive(name, mutableMapOf())
        println("Создан архив: $CounterArchives. ${archivesMap[CounterArchives]?.name}")
        CurrentState.state = Command.APP_START
    }

    fun chooseArchive() {
        println("0. Назад в меню")
        archivesMap.forEach { (id, archive) ->
            println(
                "$id. Выбрать архив ${archive.name}" +
                        " (количество заметок: ${archive.notesMap?.size})"
            )
        }
        when (val archiveIds = checkInput(archivesMap.size)) {
            0 -> CurrentState.state = Command.APP_START
            in 1..archivesMap.size -> {
                archiveVariable = archiveIds
                CurrentState.state = Command.ARCHIVE_OPEN
            }
        }
    }

    fun openArchive() {
        println("0. Создать заметку")
        val notes: MutableMap<Int, Note> = archivesMap[archiveVariable]?.notesMap ?: mutableMapOf()
        notes.forEach { (id, note) -> println("$id. Выбрать заметку ${note.name}") }
        println("${notes.size + 1}. Назад в меню")
        when (val noteIds = checkInput(notes.size + 1)) {
            0 -> CurrentState.state = Command.NOTE_CREATE
            in 1..notes.size -> {
                noteVariable = noteIds
                CurrentState.state = Command.NOTE_OPEN
            }
            else -> CurrentState.state = Command.ARCHIVE_CHOOSE
        }
    }

    fun openNote() {
        println("Заметка ${archivesMap[archiveVariable]?.notesMap?.get(noteVariable)?.name}: " +
                "${archivesMap[archiveVariable]?.notesMap?.get(noteVariable)?.text}")
        println("Введите цифру 1 для выхода")
        when (scanner.nextLine()) {
            "1" -> CurrentState.state = Command.ARCHIVE_OPEN
            else -> println("Такой команды нет")
        }
    }

    fun createNote() {
        println("Введите название заметки:")
        val name = readInput()
        println("Введите текст заметки:")
        val text = readInput()
        CounterNotes += 1
        val id = CounterNotes
        archivesMap[archiveVariable]?.notesMap?.put(id, Note(name, text, archiveVariable))
        CurrentState.state = Command.ARCHIVE_OPEN
    }

    private fun checkInput(sizeListOfCommands: Int) : Int {
        var result = checkingIdentificator(sizeListOfCommands)
        while (result == -1) {
            result = checkingIdentificator(sizeListOfCommands)
        }
        return result
    }

    private fun checkingIdentificator(sizeListOfCommands: Int) : Int { // проверка ввода пользователя
        val input = scanner.nextLine()
        try{
        if (input.toIntOrNull() == null) { // проверка числа на null
            println("Неверный формат индентификатора. Попробуйте снова.")
            return -1
        }
        if (input.toInt() > sizeListOfCommands) { // введено число за пределами идентификаторов команд
            println("Команды с таким идентификатором нет в списке. Попробуйте снова.")
            return -1
        }
            return input.toInt()
        }catch (e: Exception){
            println(e.message)
            return -1
        }
    }

    private fun readInput() : String {
        return scanner.nextLine().replaceFirstChar { it.titlecase(Locale.getDefault()) }
    }
}
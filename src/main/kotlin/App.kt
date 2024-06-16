class App {

    fun start() {
        println("Приложение 'Заметки' запущено, выберите команду:")
        val screen = Screen()
        while (true) {
            when (screen.getCurrentState()) {
                Command.APP_START -> screen.showMenu()
                Command.ARCHIVE_CHOOSE -> screen.chooseArchive()
                Command.ARCHIVE_OPEN -> screen.openArchive()
                Command.ARCHIVE_CREATE -> screen.createArchive()
                Command.NOTE_OPEN -> screen.openNote()
                Command.NOTE_CREATE -> screen.createNote()
                Command.EXIT -> break
            }
        }
    }
}
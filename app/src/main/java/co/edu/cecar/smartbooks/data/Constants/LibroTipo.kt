package co.edu.cecar.smartbooks.data.Constants
object LibroTipo {

    const val WORKBOOK = 1
    const val STUDENTS_BOOK = 2


    fun desdeTexto(
        texto: String
    ): Int {

        return when (texto) {

            "Workbook" -> WORKBOOK

            "StudentsBook" -> STUDENTS_BOOK

            else -> WORKBOOK
        }
    }

    fun aTexto(
        tipo: Int
    ): String {

        return when (tipo) {

            WORKBOOK -> "Workbook"

            STUDENTS_BOOK -> "StudentsBook"

            else -> "Workbook"
        }
    }
}
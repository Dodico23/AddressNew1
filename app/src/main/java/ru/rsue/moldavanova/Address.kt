package ru.rsue.moldavanova

import java.util.*

class Address {

    var id: UUID
        private set
    var person = ""
    var street = ""
    var building = ""
    var office = ""
    //Генерирование уникального идентификатора
    init {
        id = UUID.randomUUID()
    }
}


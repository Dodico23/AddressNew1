package ru.rsue.moldavanova

import android.content.Context
import java.util.*

class AddressLab private constructor(context: Context) {
    var addresses = mutableListOf<Address>()
    companion object {
        private var INSTANCE: AddressLab? = null

        fun get(context: Context): AddressLab { if (INSTANCE == null)
            INSTANCE = AddressLab(context)
            return INSTANCE!!
        }



    }

    fun getAddress(id: UUID): Address? { for (address in addresses) {
        if (address.id == id) { return address}
    }


        return null
    }

    fun addAddress(address: Address){
        addresses.add(address)
    }
}

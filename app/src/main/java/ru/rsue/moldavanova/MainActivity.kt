package ru.rsue.moldavanova


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*

class MainActivity : SingleFragmentActivity()  {
    //override fun createFragment(): Fragment = AddressFragment()
    companion object {
       private val EXTRA_ADDRESS_ID = "ru.rsue.moldavanova.address_id"

        fun newIntent(packageContext: Context?, addressId: UUID?): Intent? {
            val intent = Intent(packageContext, MainActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS_ID, addressId)
            return intent
        }
    }
    override fun createFragment() : AddressFragment{
        val addressId = intent
        .getSerializableExtra(EXTRA_ADDRESS_ID) as UUID?
        return AddressFragment.newInstance(addressId)
    }

}
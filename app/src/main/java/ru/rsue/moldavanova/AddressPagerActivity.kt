package ru.rsue.moldavanova

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class AddressPagerActivity: AppCompatActivity()  {

    private lateinit var viewPager: ViewPager2
    private lateinit var addresses: List<Address>


    companion object {
        private const val EXTRA_ADDRESS_ID = "ru.rsue.moldavanova.address_id"

        fun newIntent(packageContext: Context?, addressId: UUID?) = Intent( packageContext,
           AddressPagerActivity::class.java
        ).apply {
            putExtra(EXTRA_ADDRESS_ID, addressId)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_pager)
        val addressId = intent
            .getSerializableExtra(EXTRA_ADDRESS_ID) as UUID?

        viewPager = findViewById(R.id.activity_address_pager_view_pager)
        viewPager.adapter = ViewPagerAdapter(this)

        addresses = AddressLab.get(this).addresses
        for (i in addresses.indices)
            if (addresses[i].id == addressId) {
                viewPager.currentItem = i
                break
            }

    }
    private class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        private val addresses: List<Address> =
            AddressLab.get(fragmentActivity).addresses
        override fun getItemCount() = addresses.size

        override fun createFragment(position: Int) = AddressFragment.newInstance(addresses[position].id)
    }

}
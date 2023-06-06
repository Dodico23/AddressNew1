package ru.rsue.moldavanova

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

import ru.rsue.moldavanova.api.ApiFactory

class AddressListFragment : Fragment(), CoroutineScope {

    private var adapter: AddressAdapter? = null
    private var addressRecyclerView: RecyclerView? = null

    private lateinit var progressBar: ProgressBar
    private val service = ApiFactory.bookApi
    private var loadDataFinished = false

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main


    private var subtitleVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    companion object {
        private val SAVED_SUBTITLE_VISIBLE = "subtitle"
        private val LOAD_DATA = "loadDataFinished"
        private val TAG = "BookListFragment"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_address_list, container,
            false
        )
        if (savedInstanceState != null) { subtitleVisible =
            savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE)
            loadDataFinished = savedInstanceState.getBoolean(LOAD_DATA, false)
        }
        addressRecyclerView = view.findViewById(R.id.address_recycler_view)
        addressRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        progressBar = view.findViewById(R.id.progressBar)

        if (loadDataFinished)
            updateUI()
        else
            loadData()
        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) { super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleVisible)
        outState.putBoolean(LOAD_DATA, loadDataFinished)
    }
    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }


    private class AddressHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener  {
        private var personTextView: TextView =
            itemView!!.findViewById(R.id.list_item_address_person_text_view)
        private var streetTextView: TextView =
            itemView!!.findViewById(R.id.list_item_address_street_text_view)
        private var buildingTextView: TextView =
            itemView!!.findViewById(R.id.list_item_address_building_text_view)
        private var officeTextView: TextView =
            itemView!!.findViewById(R.id.list_item_address_office_text_view)



        private lateinit var address: Address
        fun bindAddress(address: Address) {
            this.address = address
            personTextView.text = address.person
            streetTextView.text = address.street
            buildingTextView.text = address.building
            officeTextView.text = address.office
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) { val context = v!!.context
            val intent = AddressPagerActivity.newIntent(context, address.id)
            context.startActivity(intent)

        }
    }




    private class AddressAdapter(addresses: List<Address>?) :
        RecyclerView.Adapter<AddressHolder?>() {
        private var addresses: List<Address>? = null

        init {
            this.addresses = addresses
        }

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): AddressHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.list_item_address,
                    parent, false)

            return AddressHolder(view)

        }

        override fun onBindViewHolder(holder: AddressHolder,
                                      position: Int) {
            val address = addresses!![position]
            holder.bindAddress(address)
        }

        override fun getItemCount(): Int {
            return addresses!!.size
        }

    }

    private fun updateUI() {
        val addressLab = AddressLab.get(requireActivity())
        val addresses = addressLab.addresses
        if (adapter == null) {
            adapter = AddressAdapter(addresses)
            addressRecyclerView!!.adapter = adapter
        }
        else
            adapter!!.notifyDataSetChanged()


        updateSubtitle()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_address_list, menu)

        val subtitleItem = menu.findItem(R.id.menu_item_show_subtitle)
        if (subtitleVisible)
            subtitleItem.setTitle(R.string.hide_subtitle)

        else




    subtitleItem.setTitle(R.string.show_subtitle)

}
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
        R.id.menu_item_new_address -> {
            val address = Address()
            AddressLab.get(requireActivity()).addAddress(address)
            val intent =
                AddressPagerActivity.newIntent(requireActivity(),
                    address.id)
            startActivity(intent)
            true
        }

            R.id.menu_item_show_subtitle -> {
                subtitleVisible = !subtitleVisible
                requireActivity().invalidateOptionsMenu()
                updateSubtitle()
                true
            }

            else -> super.onOptionsItemSelected(item)
    }
    private fun updateSubtitle() {
        val addressCount = AddressLab.get(requireActivity()).addresses.size
        var subtitle: String? = getString(R.string.subtitle_format, addressCount)
        if (!subtitleVisible)
            subtitle = null
        val activity = activity as AppCompatActivity?
        activity!!.supportActionBar!!.subtitle = subtitle
    }
    private fun loadData() = runBlocking {	// (1)
         val job = launch {	// (2)
        progressBar.visibility = View.VISIBLE
        val addressLab = AddressLab.get(requireActivity())
             val postRequest = service.getAddressesAsync()
             try {
            val response = postRequest.await()	// (3)
                  if (response.isSuccessful) {
            addressLab.addresses = response.body() as MutableList<Address>
        }
    } catch (e: Exception) {
        Log.d(TAG, e.message.toString())
    } finally {
        updateUI()

        progressBar.visibility = View.GONE
    }
}
job.join()	// (4)
loadDataFinished = true
}

}

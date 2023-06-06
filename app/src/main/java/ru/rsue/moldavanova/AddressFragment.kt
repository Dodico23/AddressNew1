package ru.rsue.moldavanova

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.*

class AddressFragment: Fragment() {
    companion object {
        private const val ARG_ADDRESS_ID = "address_id"
        fun newInstance(addressId: UUID?) =
            AddressFragment().apply {
                arguments = Bundle().apply { putSerializable(ARG_ADDRESS_ID, addressId)
                }
            }
    }

    private var address: Address? = null
    // private lateinit var address: Address
    private lateinit var personField: EditText
    private lateinit var streetField: EditText
    private lateinit var buildingField: EditText
    private lateinit var officeField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addressId = requireArguments().getSerializable(ARG_ADDRESS_ID) as UUID?
        address = AddressLab.get(requireActivity()).getAddress(addressId as UUID)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container:
        ViewGroup?, savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_address, container, false)
        personField = v.findViewById(R.id.address_person)
        personField.setText(address?.person)
        personField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                address!!.person = s.toString()
            }

            override fun afterTextChanged(c: Editable) {

            }
        })

        streetField = v.findViewById(R.id.address_street)
        streetField.setText(address?.street)
        streetField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                address!!.street = s.toString()
            }

            override fun afterTextChanged(c: Editable) {

            }
        })

        buildingField = v.findViewById(R.id.address_building)
        buildingField.setText(address?.building)
        buildingField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                address!!.building = s.toString()
            }

            override fun afterTextChanged(c: Editable) {

            }
        })

        officeField = v.findViewById(R.id.address_office)
        officeField.setText(address?.office)
        officeField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                address!!.office = s.toString()
            }

            override fun afterTextChanged(c: Editable) {

            }
        })
        return v
    }
}


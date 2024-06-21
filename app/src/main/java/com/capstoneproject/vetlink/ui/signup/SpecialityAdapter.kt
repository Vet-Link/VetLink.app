package com.capstoneproject.vetlink.ui.signup

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpecialityAdapter(context: Context, private val specialities: Array<String>) :
    ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, specialities) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = specialities[position]

        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = specialities[position]

        return view
    }
}

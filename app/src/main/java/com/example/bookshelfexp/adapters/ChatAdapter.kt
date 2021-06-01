package com.example.bookshelfexp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.bookshelfexp.R

/**
 * Created by SARATH on 01-04-2021
 */
class ChatAdapter(var Ctx : Context, var resources : Int , var items : List<String>) : ArrayAdapter<String>(Ctx,resources,items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater = LayoutInflater.from(Ctx)
        val view:View = layoutInflater.inflate(resources, null)


        val titleTextView: TextView = view.findViewById(R.id.text1)

        var mItem: String = items[position]
        titleTextView.text = mItem

        return super.getView(position, convertView, parent)
    }
}
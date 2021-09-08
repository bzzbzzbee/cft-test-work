package com.example.cft_test_work.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_test_work.R
import com.example.cft_test_work.data.entities.Currency
import kotlin.properties.Delegates

class CurrencyAdapter(private val dataSet: Array<Currency>) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.currencyNameTextView)
        val chCodeText: TextView = view.findViewById(R.id.сharCodeTextView)
        var id: Int = -1

        init {
            view.setOnClickListener {
                Log.e("My id is : ", id.toString())
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycle_view_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameText.text = dataSet[position].name
        viewHolder.chCodeText.text = dataSet[position].charCode
        viewHolder.id = dataSet[position].cId
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
package com.example.cft_test_work.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_test_work.R
import com.example.cft_test_work.data.entities.Currency

class CurrencyAdapter(private val onClick: (Currency) -> Unit) :
    ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback) {

    class CurrencyViewHolder(view: View, private val click: (Currency) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.currencyNameTextView)
        val chCodeText: TextView = view.findViewById(R.id.сharCodeTextView)
        var id: Int = -1
        var currentCurrency: Currency? = null

        init {
            currentCurrency?.let {
                click
            }
        }

        fun bind(currency: Currency) {
            currentCurrency = currency

            nameText.text = currency.name
            chCodeText.text = currency.charCode
            id = currency.cId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_view_item, parent, false)
        return CurrencyViewHolder(view, onClick)
    }

    override fun onBindViewHolder(viewHolder: CurrencyViewHolder, position: Int) {
        val currency = getItem(position)
        viewHolder.bind(currency)
    }

    object CurrencyDiffCallback : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
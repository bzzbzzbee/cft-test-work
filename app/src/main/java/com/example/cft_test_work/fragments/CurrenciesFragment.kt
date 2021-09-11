package com.example.cft_test_work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cft_test_work.data.entities.Currency
import com.example.cft_test_work.databinding.CurrenciesScreenBinding
import com.example.cft_test_work.ui.CurrenciesFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.R

import android.text.Editable

import android.text.TextWatcher


@AndroidEntryPoint
class CurrenciesFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: CurrenciesScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrenciesFragmentViewModel by viewModels()

    private var selectedCurrency: String = String()
    private val currenciesList: MutableList<Currency> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CurrenciesScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            mutableListOf<String>()
        ).also {
            it.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        }

        viewModel.getCurrencies().observe(viewLifecycleOwner, { currencies ->
            adapter.addAll(currencies.map { it.charCode })
            currenciesList.addAll(currencies)
        })

        binding.currenciesSpinner.onItemSelectedListener = this
        binding.currenciesSpinner.adapter = adapter

        binding.editTextNumberDecimal.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty())
                    binding.resultText.text = calculate(s.toString().toInt()).toString()
            }
        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val charCode = parent?.getItemAtPosition(position).toString()
        selectedCurrency = charCode
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun calculate(num: Int): Double {
        val currency = currenciesList.find { it.charCode == selectedCurrency }
        return (num * currency?.value!!) / currency.nominal
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
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
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import java.math.RoundingMode


@AndroidEntryPoint
class CurrenciesFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: CurrenciesScreenBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: CurrenciesFragmentViewModel by viewModels()

    private var selectedCurrency: String = String()
    private val currenciesList: MutableList<Currency> = mutableListOf()

    private val timerHandler: Handler = Handler(Looper.getMainLooper())
    private val timerRunnable: Runnable by lazy {
        object : Runnable {
            override fun run() {
                viewModel.update()
                timerHandler.postDelayed(this, 30000)
            }
        }
    }


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
            android.R.layout.simple_spinner_item,
            mutableListOf<String>()
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        viewModel.getCurrencies().observe(viewLifecycleOwner, { currencies ->
            adapter.addAll(currencies.extractCharCodeSorted())
            currenciesList.addAll(currencies)
            currenciesList.sortBy { it.charCode }
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
                val text = "${calculate(s)} ₽"
                binding.resultText.text = text
            }
        })

        binding.updateBtn.setOnClickListener {
            viewModel.update()
        }

        timerHandler.postDelayed(timerRunnable, 0)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val charCode = parent?.getItemAtPosition(position).toString()
        selectedCurrency = charCode
        val currency = currenciesList.find { it.charCode == selectedCurrency }

        currency.let {
            val text = "Текущий курс ${it?.name} к рублю: ${
                (it?.value!! / it.nominal)
                    .toBigDecimal()
                    .setScale(3, RoundingMode.HALF_EVEN)
            }"
            binding.ratioTextView.text = text
        }

        binding.resultText.text = "0 ₽"
        binding.editTextNumberDecimal.text.clear()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun calculate(num: CharSequence): String {
        return if (num.isNotEmpty()) {
            val number = num.toString().toInt()
            val currency = currenciesList.find { it.charCode == selectedCurrency }

            if (currency != null) {
                ((number * currency.value) / currency.nominal)
                    .toBigDecimal()
                    .setScale(2, RoundingMode.HALF_EVEN)
                    .toString()
            } else "0"

        } else "0"
    }

    override fun onPause() {
        super.onPause()
        timerHandler.removeCallbacks(timerRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun List<Currency>.extractCharCodeSorted(): List<String> = map { it.charCode }.sorted()
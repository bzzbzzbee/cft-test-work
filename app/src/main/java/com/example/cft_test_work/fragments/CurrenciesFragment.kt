package com.example.cft_test_work.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cft_test_work.adapters.CurrencyAdapter
import com.example.cft_test_work.data.entities.Currency
import com.example.cft_test_work.databinding.CurrenciesScreenBinding
import com.example.cft_test_work.ui.CurrenciesFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : Fragment() {
    private var _binding: CurrenciesScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrenciesFragmentViewModel by viewModels()

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

        binding.recyclerview.layoutManager = LinearLayoutManager(this.context)

        viewModel.getCurrencies().observe(viewLifecycleOwner, { currencies ->
            binding.recyclerview.adapter = CurrencyAdapter(currencies.toTypedArray())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
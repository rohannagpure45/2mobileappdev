package com.neu.mobileapplicationdevelopment202430.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.neu.mobileapplicationdevelopment202430.R
import com.neu.mobileapplicationdevelopment202430.databinding.FragmentProductListBinding
import com.neu.mobileapplicationdevelopment202430.repository.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private var _b: FragmentProductListBinding? = null
    private val b get() = _b!!
    private val vm: ProductViewModel by viewModels()
    private val adapter = ProductAdapter()

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        _b = FragmentProductListBinding.bind(v)

        b.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        b.recycler.adapter = adapter

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_options,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            b.spinnerSort.adapter = it
        }
        b.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, v: View?, pos: Int, id: Long) {
                vm.setSort(
                    when (pos) {
                        0 -> SortOrder.NAME_ASC
                        1 -> SortOrder.NAME_DESC
                        2 -> SortOrder.PRICE_ASC
                        else -> SortOrder.PRICE_DESC
                    }
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collect { res ->
                when (res) {
                    is Resource.Loading -> {
                        b.progressBar.visibility = View.VISIBLE
                        b.recycler.visibility = View.GONE
                        b.tvEmpty.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        b.progressBar.visibility = View.GONE
                        val list = res.data
                        if (list.isEmpty()) {
                            b.tvEmpty.visibility = View.VISIBLE
                            b.recycler.visibility = View.GONE
                        } else {
                            adapter.updateProducts(list)
                            b.recycler.visibility = View.VISIBLE
                            b.tvEmpty.visibility = View.GONE
                        }
                    }
                    is Resource.Error -> {
                        b.progressBar.visibility = View.GONE
                        b.tvEmpty.text = "Failed to load products"
                        b.tvEmpty.visibility = View.VISIBLE
                        b.recycler.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}

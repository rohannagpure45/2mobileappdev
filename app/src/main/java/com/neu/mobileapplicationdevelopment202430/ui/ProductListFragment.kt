package com.neu.mobileapplicationdevelopment202430.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.neu.mobileapplicationdevelopment202430.R
import com.neu.mobileapplicationdevelopment202430.data.ProductDatabase
import com.neu.mobileapplicationdevelopment202430.databinding.FragmentProductListBinding
import com.neu.mobileapplicationdevelopment202430.network.ProductApiService
import com.neu.mobileapplicationdevelopment202430.repository.ProductRepository
import com.neu.mobileapplicationdevelopment202430.repository.Resource
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListFragment : Fragment(R.layout.fragment_product_list) {

    private var _b: FragmentProductListBinding? = null
    private val b get() = _b!!
    private lateinit var adapter: ProductAdapter

    private val vm: ProductViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val dao = ProductDatabase.get(requireContext()).productDao()
                val api = Retrofit.Builder()
                    .baseUrl("https://mobileapplicationdevelopment.pythonanywhere.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ProductApiService::class.java)
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(ProductRepository(dao, api)) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentProductListBinding.bind(view)

        adapter = ProductAdapter(mutableListOf())
        b.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        b.recycler.adapter = adapter

        ArrayAdapter.createFromResource(
            requireContext(), R.array.sort_options, android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            b.spinnerSort.adapter = it
        }
        b.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                vm.sortOrder.value = when (pos) {
                    0 -> SortOrder.NAME_ASC
                    1 -> SortOrder.PRICE_ASC
                    else -> SortOrder.PRICE_DESC
                }
                render()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.state.collect { render() }
        }
        vm.load()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sort, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_sort) {
            showSortPopup(requireActivity().findViewById(item.itemId))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSortPopup(anchor: View) {
        PopupMenu(requireContext(), anchor).apply {
            menu.add(0, 1, 1, "A → Z")
            menu.add(0, 2, 2, "Price: Low → High")
            menu.add(0, 3, 3, "Price: High → Low")
            setOnMenuItemClickListener {
                vm.sortOrder.value = when (it.itemId) {
                    1 -> SortOrder.NAME_ASC
                    2 -> SortOrder.PRICE_ASC
                    else -> SortOrder.PRICE_DESC
                }
                render()
                true
            }
            show()
        }
    }


    private fun numericPrice(p: String) =
        p.filter { it.isDigit() || it == '.' }.toDoubleOrNull() ?: 0.0

    private fun sortList(list: List<com.neu.mobileapplicationdevelopment202430.model.Product>) =
        when (vm.sortOrder.value) {
            SortOrder.NAME_ASC  -> list.sortedBy { it.name.lowercase() }
            SortOrder.PRICE_ASC -> list.sortedBy { numericPrice(it.price) }
            SortOrder.PRICE_DESC -> list.sortedByDescending { numericPrice(it.price) }
        }

    private fun render() {
        when (val s = vm.state.value) {
            is Resource.Loading -> {
                b.progressBar.visibility = View.VISIBLE
                b.tvEmpty.visibility = View.GONE
            }
            is Resource.Error -> {
                b.progressBar.visibility = View.GONE
                b.tvEmpty.text = getString(R.string.load_error)
                b.tvEmpty.visibility = View.VISIBLE
                Snackbar.make(b.root, s.throwable.message ?: "Error", Snackbar.LENGTH_SHORT).show()
            }
            is Resource.Success -> {
                b.progressBar.visibility = View.GONE
                val data = sortList(s.data)
                if (data.isEmpty()) {
                    b.tvEmpty.text = getString(R.string.no_products)
                    b.tvEmpty.visibility = View.VISIBLE
                } else {
                    b.tvEmpty.visibility = View.GONE
                    adapter.updateData(data)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}

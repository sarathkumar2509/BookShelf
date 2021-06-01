package com.example.bookshelfexp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookshelfexp.R
//import com.example.bookshelfexp.adapters.AllCategoryAdapter
import com.example.bookshelfexp.adapters.BookAdapter
import com.example.bookshelfexp.adapters.HomeCategoryAdapter
import com.example.bookshelfexp.databinding.FragmentHomeBinding
import com.example.bookshelfexp.listeners.OnItemClickListener
import com.example.bookshelfexp.main.MainViewModel
import com.example.bookshelfexp.models.BookResponseItem
import kotlinx.coroutines.flow.collect

class HomeFragment : Fragment(R.layout.fragment_home) {
    
    private lateinit var binding : FragmentHomeBinding

    private lateinit var viewModel: MainViewModel

    lateinit var homeCategoryAdapter : HomeCategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentHomeBinding.bind(view)

        Log.d("Main","Inside")

        binding.fabHomeFragment.setOnClickListener{
            findNavController().navigate(
                    R.id.action_homeFragment_to_categoryListFragment
            )
        }

        viewModel.getBooks()

        viewModel.getCategoryCount()

        setupCategoryRecyclerView()




        lifecycleScope.launchWhenCreated {
            viewModel.books.collect { event ->
                when(event){
                    is MainViewModel.BooksEvent.Success -> {
                        binding.pbHomeFragment.isVisible = false
                        var result = event.result
                        homeCategoryAdapter.homeCategoryResponse = event.result.toMutableList()
                        Log.d("HomeFragment","$result")
                    }
                    is MainViewModel.BooksEvent.Failure->{

                    }
                    is MainViewModel.BooksEvent.Loading->{
                        binding.pbHomeFragment.isVisible = true

                    }
                    else -> Unit
                }
            }
        }

    }

//    override fun onDestroy() {
//        super.onDestroy()
//        viewModel.clearDatabase()
//    }


    private fun setupCategoryRecyclerView() = binding.rvHomeFragment.apply{

        homeCategoryAdapter = HomeCategoryAdapter(context , object : OnItemClickListener{
            override fun onClick(bookResponseItem: BookResponseItem) {

                val bundle = Bundle().apply {
                    putSerializable("bookresponseitem",bookResponseItem)
                }
                findNavController().navigate(
                        R.id.action_homeFragment_to_bookFragment,
                        bundle
                )
            }

        })
        adapter = homeCategoryAdapter
        homeCategoryAdapter.notifyDataSetChanged()
        layoutManager = GridLayoutManager(context,2)

    }

}
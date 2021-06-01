package com.example.bookshelfexp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelfexp.R
import com.example.bookshelfexp.adapters.FavouriteBooksAdapter
import com.example.bookshelfexp.databinding.FavouriteFragmentBinding
import com.example.bookshelfexp.listeners.OnItemClickListener
import com.example.bookshelfexp.main.MainViewModel
import com.example.bookshelfexp.models.BookResponseItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect

class FavouriteFragment : Fragment(R.layout.favourite_fragment) {

    private lateinit var binding : FavouriteFragmentBinding

    private lateinit var viewModel: MainViewModel

    lateinit var favoriteAdapter: FavouriteBooksAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FavouriteFragmentBinding.bind(view)



        setupRecyclerView()

        viewModel.getFavouriteBooks()




        lifecycleScope.launchWhenCreated {
            viewModel.favourite.collect { event ->
                when(event){
                    is MainViewModel.FavouriteEvent.Success -> {
                        binding.pbFavourites.isVisible = false
                        var result = event.result
                        Log.d("FAVOURITEFRAGMENT","$result")
                        favoriteAdapter.favouriteBooksResponse = event.result.toMutableList()

                    }
                    is MainViewModel.FavouriteEvent.Failure->{

                    }
                    is MainViewModel.FavouriteEvent.Loading->{
                        binding.pbFavourites.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
//                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ){
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                return true
//            }

//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                val bookResponseItem = favoriteAdapter.differ.currentList[position]
////                viewModel.deleteBook(bookResponseItem)
//                Snackbar.make(view,"Book Removed From Favourites",Snackbar.LENGTH_LONG).apply {
//                    setAction("UNDO"){
//                        viewModel.saveFavouriteBooks(bookResponseItem)
//                    }
//                    show()
//                }
//            }
//
//        }
//
//        ItemTouchHelper(itemTouchHelperCallback).apply {
//            attachToRecyclerView(binding.rvFavorites)
//        }



    }


    private fun setupRecyclerView() = binding.rvFavorites.apply {

        favoriteAdapter = FavouriteBooksAdapter(requireContext(), object : OnItemClickListener{
            override fun onClick(bookResponseItem: BookResponseItem) {
                val bundle = Bundle().apply {
                    putSerializable("bookresponseitem",bookResponseItem)
                }
                findNavController().navigate(
                        R.id.action_favouriteFragment_to_bookFragment,
                        bundle
                )
            }

        })

            adapter = favoriteAdapter
            favoriteAdapter.notifyDataSetChanged()
            layoutManager = LinearLayoutManager(activity)
        }
    }

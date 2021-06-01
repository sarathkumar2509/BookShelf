package com.example.bookshelfexp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.bookshelfexp.BaseActivity
import com.example.bookshelfexp.R
import com.example.bookshelfexp.databinding.FragmentBookBinding
import com.example.bookshelfexp.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class BookFragment : Fragment(R.layout.fragment_book) {

    private lateinit var binding : FragmentBookBinding

    private lateinit var viewModel: MainViewModel

    private val args : BookFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentBookBinding.bind(view)


        val book = args.bookresponseitem

        if (book.favourite){
            binding.ivFavBookFragment.setImageResource(R.drawable.star)
        }else{
            binding.ivFavBookFragment.setImageResource(R.drawable.staroutline)
        }

        var author = ""
        for (i in book.authors){
            author = "$i $author "
        }
        var category = ""
        for (i in book.categories){
            category = "$i $category "
        }


        Glide.with(requireContext()).load(book.thumbnailUrl).placeholder(R.drawable.bookplaceholder).into(binding.ivFavImage)
        binding.tvTitleBooks.text = book.title
        binding.tvAuthorBooks.text = author
        binding.tvPageCount.text = book.pageCount.toString()
        binding.tvPublishDate.text = book.publishedDate?.`$date`?.subSequence(0,10)
        binding.tvCategory.text = category

        if (book.pageCount==0){
            binding.longDescription.text = "No Long Description Available"
        }else{
            binding.longDescription.text = book.longDescription
        }


            binding.ivFavBookFragment.setOnClickListener{
                if(!book.favourite){
                    lifecycleScope.launch {
                        viewModel.saveFavouriteBooks(book)
                        binding.ivFavBookFragment.setImageResource(R.drawable.star)
                        Snackbar.make(view,"Book Saved to Favourites", Snackbar.LENGTH_SHORT).show()
                    }

                }else{
                    lifecycleScope.launch {
                        viewModel.removeFromFavourite(book)
                        binding.ivFavBookFragment.setImageResource(R.drawable.staroutline)
                        Snackbar.make(view,"Removed From Favourites", Snackbar.LENGTH_SHORT).show()
                    }

                }

            }

    }
}
package com.example.bookshelfexp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.bookshelfexp.R
import com.example.bookshelfexp.databinding.FragmentBookListBinding
import com.example.bookshelfexp.main.MainViewModel

class BookListFragment : Fragment(R.layout.fragment_book_list) {

    private lateinit var binding : FragmentBookListBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentBookListBinding.bind(view)
    }
}
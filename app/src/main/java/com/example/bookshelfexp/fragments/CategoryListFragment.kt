package com.example.bookshelfexp.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookshelfexp.R
//import com.example.bookshelfexp.adapters.AllCategoryAdapter
import com.example.bookshelfexp.adapters.CategoryListAdapter
import com.example.bookshelfexp.databinding.FragmentCategoryListBinding
import com.example.bookshelfexp.listeners.OnItemCategoryCheckChangedListener
import com.example.bookshelfexp.main.MainViewModel
import com.example.bookshelfexp.models.CategoryCount
import kotlinx.coroutines.flow.collect

class CategoryListFragment : Fragment()  {

    private lateinit var binding : FragmentCategoryListBinding

    private lateinit var viewModel: MainViewModel

    lateinit var result:List<CategoryCount>

    lateinit var listAdapter : CategoryListAdapter


    val TAG = "CategoryListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProviders.of(this)[MainViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val activity = activity as Context

        var listView:ListView = view.findViewById(R.id.lvCategoryList)

        binding= FragmentCategoryListBinding.bind(view)


        lifecycleScope.launchWhenCreated {
            viewModel.categoryCount.collect { event ->
                when(event){
                    is MainViewModel.CategoryCountEvent.Success -> {
                        binding.progressBarAllCategoryList.isVisible = false
                        result = event.result

                        for (i in result){
                            Log.d("InsideList","$i")
                        }

                    }
                    is MainViewModel.CategoryCountEvent.Failure->{
                        Log.d("InsideList","faiure")
                    }
                    is MainViewModel.CategoryCountEvent.Loading->{
                        binding.progressBarAllCategoryList.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

        listAdapter = CategoryListAdapter(activity as Context, R.layout.item_category, result, object : OnItemCategoryCheckChangedListener {
            override fun onClick(categoryName: List<String>, state: Boolean) {
                Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show()
            }
        })
        listView.adapter = listAdapter
        listAdapter.notifyDataSetChanged()




    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_done ->{
                Toast.makeText(context,"inside",Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                        R.id.action_categoryListFragment_to_homeFragment
                )
            }
            else -> true
        }
        return super.onOptionsItemSelected(item)

    }






//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//        binding = FragmentCategoryListBinding.bind(view)
//
//        var listView:ListView = view.findViewById(R.id.lvCategoryList)
////        viewModel.getCategoryCount()
//
//        //setupRecyclerView()
//
//
////        lifecycleScope.launchWhenCreated {
////            viewModel.categoryCount.collect { event ->
////                when(event){
////                    is MainViewModel.CategoryCountEvent.Success -> {
////                        binding.progressBarAllCategoryList.isVisible = false
////                         result = event.result
////                        //allCategoryAdapter.allCategoryResponse = event.result.toMutableList()
////                        for (i in result){
////                            Log.d("InsideList","$i")
////                        }
////
////                    }
////                    is MainViewModel.CategoryCountEvent.Failure->{
////
////                    }
////                    is MainViewModel.CategoryCountEvent.Loading->{
////                        binding.progressBarAllCategoryList.isVisible = true
////                    }
////                    else -> Unit
////                }
////            }
////        }
//
//
////        for (i in result){
////            Log.d("listAdapter","$i")
////        }
//
//
//        listAdapter = ArrayAdapter(activity as Context, android.R.layout.simple_list_item_1,arrayList)
////        listAdapter = CategoryListAdapter(activity as Context, R.layout.item_category,result)
////        listView.adapter = listAdapter
//        listAdapter.notifyDataSetChanged()
//
//
//
//
//    }




//    private fun setupRecyclerView() = binding.rvAllCategoryList.apply{
//
//        allCategoryAdapter = AllCategoryAdapter(context , object : OnCategoryItemClick{
//            override fun onClick(categoryCount: CategoryCount) {
//
//                val query = categoryCount.categories
//
//                Log.d("Index","$query")
//                //viewModel.getBookAndUpdate(query)
//
//            }
//
//        })
//        adapter = allCategoryAdapter
//        allCategoryAdapter.notifyDataSetChanged()
//        layoutManager = LinearLayoutManager(context)
//
//    }
}
package com.example.bookshelfexp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelfexp.R
import com.example.bookshelfexp.databinding.HomeCategoryItemBinding
import com.example.bookshelfexp.databinding.ItemCategoryBinding
import com.example.bookshelfexp.listeners.OnItemClickListener
import com.example.bookshelfexp.models.BookResponseItem
import com.example.bookshelfexp.models.CategoryCount

/**
 * Created by SARATH on 30-03-2021
 */

class HomeCategoryAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder>() {

    inner class HomeCategoryViewHolder(val binding : HomeCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<BookResponseItem>(){
        override fun areItemsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem == newItem
        }

    }


    private val differ = AsyncListDiffer(this,diffCallback)
    var homeCategoryResponse : MutableList<BookResponseItem>

        get()=differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryAdapter.HomeCategoryViewHolder {
        return HomeCategoryViewHolder(HomeCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: HomeCategoryAdapter.HomeCategoryViewHolder, position: Int) {
        val bookResponseItem  = homeCategoryResponse[position]


        holder.binding.apply {

//            var category = ""
//            for (i in categoryCount.categories){
//                category = i
//            }

            Glide.with(context).load(bookResponseItem.thumbnailUrl).placeholder(R.drawable.bookplaceholder).centerCrop().into(ivHomeCategory)

            tvHomeCategory.text = bookResponseItem.title
        }
        holder.itemView.setOnClickListener {
                    Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show()
            onItemClickListener.onClick(bookResponseItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
//
//
//    private suspend fun insertData(bookResponseItem: BookResponseItem){
//        bookDao.upsert(bookResponseItem)
//    }
//
//    private var onItemClickListener : ((BookResponseItem) -> Unit)? = null
//
//    fun setOnItemClickListener(listener : (BookResponseItem) -> Unit){
//        onItemClickListener = listener
//        Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show()
//    }

}
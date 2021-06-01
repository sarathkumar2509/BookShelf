package com.example.bookshelfexp.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelfexp.R
import com.example.bookshelfexp.models.BookResponseItem

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)


    private val diffCallback = object : DiffUtil.ItemCallback<BookResponseItem>(){
        override fun areItemsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem == newItem
        }

    }


    val differ = AsyncListDiffer(this,diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        return BookViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_book,parent,false
                )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        val book = differ.currentList[position]
        var author = ""
        holder.itemView.apply {
            //Glide.with(this).load(book.thumbnailUrl).into(ivBookImage)
//            Log.d("ITEMS", book.title)
//            Log.d("ITEMS","${book.authors}")
//            Log.d("ITEMS", "${book.thumbnailUrl}")
//            Log.d("ITEMS","${book.pageCount}")

            var author=""
            for(i in book.authors){
                author = "$i $author "
            }



        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

//
//    private var onItemClickListener : ((BookResponseItem) -> Unit)? = null
//
//    fun setOnItemClickListener(listener : (BookResponseItem) -> Unit){
//        onItemClickListener = listener
//    }

}
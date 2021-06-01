package com.example.bookshelfexp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookshelfexp.R
import com.example.bookshelfexp.databinding.ItemBookBinding
import com.example.bookshelfexp.databinding.ItemCategoryBinding
import com.example.bookshelfexp.listeners.OnCategoryItemClick
import com.example.bookshelfexp.listeners.OnItemClickListener
import com.example.bookshelfexp.models.BookResponseItem
import com.example.bookshelfexp.models.CategoryCount


class FavouriteBooksAdapter(private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<FavouriteBooksAdapter.FavouriteBooksViewHolder>() {

    inner class FavouriteBooksViewHolder(val binding : ItemBookBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffCallback = object : DiffUtil.ItemCallback<BookResponseItem>(){
        override fun areItemsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(oldItem: BookResponseItem, newItem: BookResponseItem): Boolean {
            return oldItem == newItem
        }

    }


     val differ = AsyncListDiffer(this,diffCallback)

    var favouriteBooksResponse : MutableList<BookResponseItem>

        get()=differ.currentList
        set(value) {differ.submitList(value)}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteBooksAdapter.FavouriteBooksViewHolder {
        return FavouriteBooksViewHolder(ItemBookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: FavouriteBooksAdapter.FavouriteBooksViewHolder, position: Int) {
        val favouriteBook  = favouriteBooksResponse[position]


        holder.binding.apply {

            var category = ""
            var author = ""
            var date = ""
            for (i in favouriteBook.categories){
                category = "$i $category "
            }
            for (i in favouriteBook.authors){
                author = "$i $author "
            }

            tvTitle.text = favouriteBook.title
            tvAuthor.text = author
            tvPageCount.text = favouriteBook.pageCount.toString()
            tvPublishedAt.text = favouriteBook.publishedDate?.`$date`?.subSequence(0,10) ?: "-"
            Glide.with(context).load(favouriteBook.thumbnailUrl).placeholder(R.drawable.bookplaceholder).into(ivBookImage)
        }

        holder.itemView.setOnClickListener {
            Toast.makeText(context,"${favouriteBook.categories}", Toast.LENGTH_SHORT).show()
            onItemClickListener.onClick(favouriteBook)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


//    private suspend fun insertData(bookResponseItem: BookResponseItem){
//        bookDao.upsert(bookResponseItem)
//    }

//    private var onItemClickListener : ((BookResponseItem) -> Unit)? = null
//
//    fun setOnItemClickListener(listener : (BookResponseItem) -> Unit){
//        onItemClickListener = listener
//    }

}
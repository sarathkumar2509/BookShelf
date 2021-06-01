//package com.example.bookshelfexp.adapters
//
//import android.content.Context
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.lifecycle.LifecycleCoroutineScope
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.bookshelfexp.R
//import com.example.bookshelfexp.databinding.FragmentCategoryListBinding
//import com.example.bookshelfexp.databinding.ItemCategoryBinding
//import com.example.bookshelfexp.db.BookDao
//import com.example.bookshelfexp.db.BookDatabase
//import com.example.bookshelfexp.listeners.OnCategoryItemClick
//import com.example.bookshelfexp.listeners.OnItemClickListener
//import com.example.bookshelfexp.models.BookResponseItem
//import com.example.bookshelfexp.models.CategoryCount
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import okhttp3.internal.trimSubstring
//
//class AllCategoryAdapter(private val context: Context , private val onCategoryItemClick: OnCategoryItemClick) : RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder>() {
//
//    inner class AllCategoryViewHolder(val binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)
//
//
//    private val diffCallback = object : DiffUtil.ItemCallback<CategoryCount>(){
//        override fun areItemsTheSame(oldItem: CategoryCount, newItem: CategoryCount): Boolean {
//            return oldItem.categories == newItem.categories
//        }
//
//        override fun areContentsTheSame(oldItem: CategoryCount, newItem: CategoryCount): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//
//    private val differ = AsyncListDiffer(this,diffCallback)
//
//    var allCategoryResponse : MutableList<CategoryCount>
//
//        get()=differ.currentList
//        set(value) {differ.submitList(value)}
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryAdapter.AllCategoryViewHolder {
//        return AllCategoryViewHolder(ItemCategoryBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//        ))
//    }
//
//    override fun onBindViewHolder(holder: AllCategoryAdapter.AllCategoryViewHolder, position: Int) {
//        val categoryCount  = allCategoryResponse[position]
//
//
//        holder.binding.apply {
//
//            var category = ""
//            for (i in categoryCount.categories){
//                category = "$i $category"
////            }
////            rvAllCategoriesTitle.text = category
////            tvAllCategoryTitle.text = categoryCount.num.toString()
////           // ivAllCategoryFirstText.isChecked = categoryCount.categoryState
////            Log.d("checked" , "${cbAllCategory.isChecked}")
//
//            cbAllCategory.setOnClickListener {
//                Toast.makeText(context,"${categoryCount.categories}", Toast.LENGTH_SHORT).show()
//                onCategoryItemClick.onClick(categoryCount)
//            }
//        }
//
////       // holder.itemView.setOnClickListener {
////        holder.binding.ivAllCategoryFirstText.setOnClickListener {
////            //holder.binding.ivAllCategoryFirstText.isChecked = true
////            Toast.makeText(context,"${categoryCount.categories}", Toast.LENGTH_SHORT).show()
////            onCategoryItemClick.onClick(categoryCount)
////        }
//    }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//
////    private suspend fun insertData(bookResponseItem: BookResponseItem){
////        bookDao.upsert(bookResponseItem)
////    }
//
////    private var onItemClickListener : ((BookResponseItem) -> Unit)? = null
////
////    fun setOnItemClickListener(listener : (BookResponseItem) -> Unit){
////        onItemClickListener = listener
////    }
//
//}
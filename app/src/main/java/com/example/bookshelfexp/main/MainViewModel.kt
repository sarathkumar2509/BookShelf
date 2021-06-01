package com.example.bookshelfexp.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelfexp.db.BookDatabase
import com.example.bookshelfexp.models.BookResponseItem
import com.example.bookshelfexp.models.CategoryCount
import com.example.bookshelfexp.util.DispatcherProvider
import com.example.bookshelfexp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainViewModel @ViewModelInject constructor(
    private val repository: MainRepository,
    private val dispatchers : DispatcherProvider,
    private val db : BookDatabase
) : ViewModel() {

    val TAG = "MAINVIEWMODEL"


    sealed class BooksEvent {
        class Success(val result: List<BookResponseItem>) : BooksEvent()
        class Failure(val error: String) : BooksEvent()
        object Loading : BooksEvent()
        object Empty : BooksEvent()
    }

//    sealed class CategoryEvent {
//        class Success(val result: List<BookResponseItem>) : CategoryEvent()
//        class Failure(val error: String) : CategoryEvent()
//        object Loading : CategoryEvent()
//        object Empty : CategoryEvent()
//    }

    sealed class InsertEvent {
        class Success(val result: Long) : InsertEvent()
        class Failure(val error: String) : InsertEvent()
        object Loading : InsertEvent()
        object Empty : InsertEvent()
    }
    sealed class CategoryCountEvent {
        class Success(val result: List<CategoryCount>) : CategoryCountEvent()
        class Failure(val error: String) : CategoryCountEvent()
        object Loading : CategoryCountEvent()
        object Empty : CategoryCountEvent()
    }

    sealed class FavouriteEvent {
        class Success(val result: List<BookResponseItem>) : FavouriteEvent()
        class Failure(val error: String) : FavouriteEvent()
        object Loading : FavouriteEvent()
        object Empty : FavouriteEvent()
    }


    private val _books = MutableStateFlow<BooksEvent>(BooksEvent.Empty)
    val books: StateFlow<BooksEvent> = _books

    private val _categoryCount = MutableStateFlow<CategoryCountEvent>(CategoryCountEvent.Empty)
    val categoryCount: StateFlow<CategoryCountEvent> = _categoryCount


//    private val _category = MutableStateFlow<CategoryEvent>(CategoryEvent.Empty)
//    val category: StateFlow<CategoryEvent> = _category
//
    private val _insert = MutableStateFlow<InsertEvent>(InsertEvent.Empty)
    val insert: StateFlow<InsertEvent> = _insert

    private val _favorite = MutableStateFlow<FavouriteEvent>(FavouriteEvent.Empty)
    val favourite: StateFlow<FavouriteEvent> = _favorite


    fun getBooks() {
        viewModelScope.launch(dispatchers.io) {
            _books.value = BooksEvent.Loading


            repository.searchBookItem("Java")
            val checkDbSize =  repository.getAllBooksInDb()
            if (checkDbSize.data?.size == 0){

            when (val bookResponse = repository.getAllBooks()) {
                is Resource.Error -> {
                    _books.value = BooksEvent.Failure(bookResponse.message!!)
                    Log.d(TAG, "${bookResponse.message}")
                    Log.d(TAG, "Failure")
                }
                is Resource.Success -> {
                    val data = bookResponse.data
                    if (data != null) {
                        for (i in data) {
                            db.getBookDao().insert(i)
                            Log.d("insert ${i.isbn}", "$i")
                        }

                    }
                    if (data == null) {
                        _books.value = BooksEvent.Failure("UnExpected Error")
                        Log.d(TAG, "$data")
                    } else {
                        _books.value = BooksEvent.Success(data)
                        Log.d(TAG, "$data")
                    }
                }
            }
        }else{
                when (val bookResponse = repository.getAllBooksInDb()) {
                    is Resource.Error -> {
                        _books.value = BooksEvent.Failure(bookResponse.message!!)
                        Log.d(TAG, "${bookResponse.message}")
                        Log.d(TAG, "Failure")
                    }
                    is Resource.Success -> {
                        val data = bookResponse.data
//                        if (data != null) {
//                            for (i in data) {
//                                db.getBookDao().insert(i)
//                                Log.d("insert ${i.isbn}", "$i")
//                            }
//
//                        }
                        if (data == null) {
                            _books.value = BooksEvent.Failure("UnExpected Error")
                            Log.d(TAG, "$data")
                        } else {
                            _books.value = BooksEvent.Success(data)
                            Log.d(TAG, "$data")
                        }
                    }
                }
        }
        }
    }

    fun getCategoryCount(){
        viewModelScope.launch (dispatchers.io){
            _categoryCount.value=CategoryCountEvent.Loading
            when(val categoryCountData = repository.getDistinctCategory()){
                is Resource.Error -> {
                    _categoryCount.value = CategoryCountEvent.Failure(categoryCountData.message!!)
                }
                is Resource.Success->{
                    val data = categoryCountData.data
                    if(data==null){
                        _categoryCount.value=CategoryCountEvent.Failure("Unexpected Error ")
                    }
                    else{
                        _categoryCount.value=CategoryCountEvent.Success(data)
                        Log.d(TAG,"$data")
                    }
                }
            }
        }

    }

    fun getBookAndUpdate(query : List<String>){

        viewModelScope.launch(dispatchers.io) {

            val response = repository.getBookAndUpdate(query)
            Log.d("QUERYRESPONSE","$response")
        }




    }

    fun clearDatabase(){

        viewModelScope.launch(dispatchers.io) {
            val size = db.getBookDao().getAllBooks().size
            if (size!=0){
                db.clearAllTables()
            }
            Log.d("size","$size")
        }
    }

    suspend fun saveFavouriteBooks(bookResponseItem: BookResponseItem){
        viewModelScope.launch(dispatchers.io) {
            repository.saveFavouriteBooks(bookResponseItem)
        }
    }


    suspend fun removeFromFavourite(bookResponseItem: BookResponseItem){
        viewModelScope.launch(dispatchers.io) {
            repository.deleteBook(bookResponseItem)
        }
    }

    fun getFavouriteBooks(){

        viewModelScope.launch (dispatchers.io){
            _favorite.value=FavouriteEvent.Loading
            when(val favouriteData = repository.getFavouriteBooks()){
                is Resource.Error -> {
                    _favorite.value = FavouriteEvent.Failure(favouriteData.message!!)
                }
                is Resource.Success->{
                    val data = favouriteData.data
                    if(data==null){
                        _favorite.value=FavouriteEvent.Failure("Unexpected Error ")
                    }
                    else{
                        _favorite.value=FavouriteEvent.Success(data)
                        Log.d("getFavouriteBooks","$data")
                    }
                }
            }
        }
    }




//    fun insertAllBooks() {
//        viewModelScope.launch(dispatchers.io) {
//            _insert.value = InsertEvent.Loading
//            when (val insertResponse = repository.insertAllBooks() {
//                is Resource.Error -> {
//                    _insert.value = InsertEvent.Failure(insertResponse.message!!)
//                    Log.d(TAG, "Failure")
//                }
//                is Resource.Success -> {
//                    val data = insertResponse.data
//
//                    if (data == null) {
//                        _insert.value = InsertEvent.Failure("UnExpected Error")
//                        Log.d(TAG, "$data")
//                    } else {
//                        _insert.value = InsertEvent.Success(data)
//                        Log.d(TAG, "$data")
//                    }
//                }
//            }
//        }
//    }


}
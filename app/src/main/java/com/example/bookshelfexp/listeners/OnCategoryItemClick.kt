package com.example.bookshelfexp.listeners

import com.example.bookshelfexp.models.BookResponseItem
import com.example.bookshelfexp.models.CategoryCount

/**
 * Created by SARATH on 31-03-2021
 */
interface OnCategoryItemClick {

    fun onClick(categoryCount: CategoryCount)

}
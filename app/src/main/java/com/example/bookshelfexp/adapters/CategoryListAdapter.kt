package com.example.bookshelfexp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelfexp.R
import com.example.bookshelfexp.databinding.ActivityBaseBinding.inflate
import com.example.bookshelfexp.listeners.OnCategoryItemClick
import com.example.bookshelfexp.listeners.OnItemCategoryCheckChangedListener
import com.example.bookshelfexp.models.CategoryCount


/**
 * Created by SARATH on 01-04-2021
 */
class CategoryListAdapter(var cxt: Context, private val resource: Int, val data: List<CategoryCount>, private val onCategoryCheckChangedListener: OnItemCategoryCheckChangedListener) : BaseAdapter()  {



    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        if (convertView==null){
            view = LayoutInflater.from(cxt).inflate(resource,parent,false)
        }

//        TextView moviesName=(TextView)rowView.findViewById(R.id.movieNameTv);
//        CheckBox checkBox=(CheckBox)rowView.findViewById(R.id.checkbox);

        val bookCount : TextView = view!!.findViewById(R.id.tvBookCount)

        val categoryName : TextView = view!!.findViewById(R.id.tvCategoryName)

        val checkBox : CheckBox = view.findViewById(R.id.cbAllCategory)

        checkBox.isChecked = data[position].categoryState

        checkBox.tag = position

        checkBox.setOnClickListener {

            fun onClick(view: View){
                var curPos = view.tag
                var isChecked = false

                if (!data[position].categoryState){
                    isChecked = true
                }
                data[curPos as Int].categoryState = isChecked
                notifyDataSetChanged()

            }

        }


        return view!!
    }




    }


//        val viewHolder: ViewHolder
//        var rowView:View?
//        if(convertView==null){
//            val inflater = LayoutInflater.from(cxt)
//            rowView = inflater.inflate(resource, null)
//            viewHolder= ViewHolder(rowView)
//            rowView.tag=viewHolder
//        }
//        else{
//            rowView=convertView
//            viewHolder=rowView.tag as ViewHolder
//        }
//
//
//
//        val bookCount : TextView = rowView!!.findViewById(R.id.tvBookCount)
//
//        val categoryName : TextView = rowView!!.findViewById(R.id.tvCategoryName)
//
//        val checkBox : CheckBox = rowView.findViewById(R.id.cbAllCategory)
//
//
//
//        var categoryCountPosition = data[position]
//
//        var category = ""
//        for (i in data[position].categories){
//            category = "$i $category"
//        }
//
//        viewHolder.catTitle.text = category
//
//
//
//
//
//        viewHolder.checkBox.setOnClickListener { it as CheckBox
//            onCategoryCheckChangedListener.onClick(data[position].categories,it.isChecked)
//        }
//
//
//
//
//        return rowView!!

 //   }



//    private class ViewHolder(view: View?) {
//        //    val catName = view?.findViewById(R.id.rvAllCategoriesTitle) as  TextView
//        val catTitle = view?.findViewById(R.id.tvCategoryName) as TextView
//        val checkBox=view?.findViewById(R.id.cbAllCategory) as CheckBox
//
//
//    }

//}



//    fun onclick(view: View){
//                var currentPosition = view.tag
//                var isChecked = false
//
//
//            }

//bookCount.text=data[position].num.toString()
//
//        checkBox.isChecked = false
//
//        checkBox.tag = position
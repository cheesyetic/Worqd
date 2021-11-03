package com.coding.projectkuliah

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListViewCategoryFragment : Fragment() {
    var barTitle : String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listview = view.findViewById<ListView>(R.id.lvCategoryListView)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.lvCategoryToolbar)
        val barTitles = view.findViewById<TextView>(R.id.flvTitle)

        barTitle = arguments?.getString("barTitle")
        barTitles.text = barTitle

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val names = ArrayList<String>()
        val nameList = arrayOf("Izdeveloper", "Whoami", "Mynameiz", "Hadouken", "Tatsumaki", "Senpuyaku")

        for (i in 0..nameList.size-1){
            names.add(nameList[i])
        }

        val adapter = MyCustomAdapter(requireActivity(), names)

        listview.adapter = adapter
    }

    class MyCustomAdapter(context : Context, nameList: ArrayList<String> ): BaseAdapter() {

        private val mContext: Context
        var names = nameList

        init {
            mContext = context
        }

        override fun getCount(): Int {
            return names.size
        }

        override fun getItem(p0: Int): Any {
            return "TRY STRING"
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowLvCategory = layoutInflater.inflate(R.layout.lvcategory_row, p2, false)

            //setting up name text
            val categoriesTextView = rowLvCategory.findViewById<TextView>(R.id.lvCategoryName)
            categoriesTextView.text = names.get(p0)

            return rowLvCategory

        }
    }
}
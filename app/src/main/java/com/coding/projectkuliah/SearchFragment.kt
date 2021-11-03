package com.coding.projectkuliah

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList

class SearchFragment : Fragment() {
    lateinit var myCustomAdapter : MyCustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listview = view.findViewById<ListView>(R.id.searchlistview)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.searchToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val categories = ArrayList<String>()
        val categoriesList = arrayOf(
            "Graphics and Design", "Digital Marketing", "Writing & Translation", "Video & Animation",
            "Programming & Tech", "Data", "Business", "Lifestyle"
        )
        val details = ArrayList<String>()
        val detailList = arrayOf(
            "Logo and Brand Identity, Art and Illustration", "Social Media Marketing, Social Media Advertising",
            "Article and Blog Posts, Translation", "Whiteboard and Animation Explainers, Video Editing",
            "Wordpress, Webiste Builder and CMS", "Database, Data Processing", "Virtual Assistant, Market Research",
            "Online Tutoring, Gaming"
        )

        for(i in 0..categoriesList.size-1){
            categories.add(categoriesList[i])
            details.add(detailList[i])
        }

        myCustomAdapter = MyCustomAdapter(requireActivity(), categories, details)
        listview.adapter = myCustomAdapter
        listview.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, l ->
            val selectedCategory = categories[position]
            val bundle = Bundle()
            bundle.putString("barTitle", selectedCategory)
            val listViewCategoryFragment = ListViewCategoryFragment()
            listViewCategoryFragment.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, listViewCategoryFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.categories_menu, menu)
        val menuItem = menu.findItem(R.id.categories_search)

        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                myCustomAdapter.filter(newText)
                return false
            }

        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    class MyCustomAdapter(context : Context, categories: ArrayList<String>, details: ArrayList<String> ): BaseAdapter(){

        private val mContext : Context
        var category = categories
        var detail = details
        var tempCategory = ArrayList(category)
        var tempDetail = ArrayList(detail)

        init{
            mContext = context
        }

        override fun getCount(): Int {
            return category.size
        }

        override fun getItem(p0: Int): Any {
            return "TRY STRING"
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowSearch = layoutInflater.inflate(R.layout.search_row, p2, false)

            //setting up category text
            val categoriesTextView = rowSearch.findViewById<TextView>(R.id.tvCategory)
            categoriesTextView.text = category.get(p0)
            //setting up detail text
            val detailTextView = rowSearch.findViewById<TextView>(R.id.tvDetail)
            detailTextView.text = detail.get(p0)

            return rowSearch

        }

        fun filter(text: String?){
            val text = text!!.toLowerCase(Locale.getDefault())
            category.clear()
            detail.clear()
            if(text.length == 0){
                category.addAll(tempCategory)
                detail.addAll(tempDetail)
            } else{
                for(i in 0..tempCategory.size-1){
                    if(tempCategory.get(i)!!.toLowerCase(Locale.getDefault()).contains(text)){
                        category.add(tempCategory.get(i))
                        detail.add(tempDetail.get(i))
                    }
                }
            }

            notifyDataSetChanged()

        }
    }
}
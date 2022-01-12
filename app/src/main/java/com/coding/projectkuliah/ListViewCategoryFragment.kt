package com.coding.projectkuliah

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.coding.projectkuliah.model.ProductModel
import de.hdodenhof.circleimageview.CircleImageView

class ListViewCategoryFragment : Fragment() {
    var barTitle : String? = ""
    var idCategory: String? = ""

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

        idCategory = arguments?.getString("idCat")

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        //creating the instance of DatabaseHandler class
        val databaseHandler: DBHelper = DBHelper(requireActivity())
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<ProductModel> = databaseHandler.viewProduct(idCategory!!)

        val adapter = MyCustomAdapter(requireActivity(), R.layout.lvcategory_row, emp)

        listview.adapter = adapter
    }

    class MyCustomAdapter(var mCtx: Context , var resource:Int, var items:List<ProductModel>)
        : ArrayAdapter<ProductModel>( mCtx , resource , items ){

        private var images = arrayOf(R.drawable.messageprofile1, R.drawable.messageprofile2, R.drawable.messageprofile2, R.drawable.messageprofile2)
        private var profiles = arrayOf(R.drawable.messageprofile1, R.drawable.messageprofile2, R.drawable.messageprofile2, R.drawable.messageprofile2)

        override fun getCount(): Int {
            return items.size
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
            val view: View = layoutInflater.inflate(resource, null)

            var item: ProductModel = items[position]
            val productImage = view.findViewById<ImageView>(R.id.lvCategoryImage)
            val profile = view.findViewById<CircleImageView>(R.id.lvCategoryProfile)
            val name = view.findViewById<TextView>(R.id.lvCategoryName)
            val price = view.findViewById<TextView>(R.id.lvCategoryPrice)
            val description = view.findViewById<TextView>(R.id.lvCategoryDescription)

            productImage.setImageResource(images.get(position))
            profile.setImageResource(profiles.get(position))
            name.text = item.name
            price.text = item.price
            description.text = item.detail

            return view
        }
    }
}
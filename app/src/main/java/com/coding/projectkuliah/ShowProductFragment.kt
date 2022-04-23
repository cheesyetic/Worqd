package com.coding.projectkuliah

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.coding.projectkuliah.model.ProductModel
import de.hdodenhof.circleimageview.CircleImageView


class ShowProductFragment : Fragment() {
    lateinit var DBHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg1 = arguments?.getString("barTitle")
        val arg2 = arguments?.getString("idProduct")
        val arg3 = arguments?.getByteArray("productimage")
        val arg4 = arguments?.getByteArray("profileimage")
        arguments?.clear()

        val idProduct = arg2!!.toInt()

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.productToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        DBHelper = DBHelper(requireActivity())

        val showTitle = view.findViewById<TextView>(R.id.showTitle)
        val showName = view.findViewById<TextView>(R.id.showProfileName)
        val showProfileImage = view.findViewById<CircleImageView>(R.id.showProfileImage)
        val showProductImage = view.findViewById<ImageView>(R.id.showImage)
        val showPrice = view.findViewById<TextView>(R.id.showPrice)
        val showDescription = view.findViewById<TextView>(R.id.showDescription)
        val showEmail = view.findViewById<TextView>(R.id.showEmail)
        val showPhone = view.findViewById<TextView>(R.id.showWhatsapp)

        val itemProduct: List<ProductModel> = DBHelper.viewProductDetail(idProduct)

        //info product
        var title = arg1
        var name = "x"
        var profileImg = arg4
        var productImg = arg3
        var price = "x"
        var description = "x"
        var email = "x"
        var phone = "x"
        var long = "x"
        var lat = "x"

        //retrieving data
        for(e in itemProduct){
            name = e.name
            price = e.price
            description = e.detail
            email = e.email
            phone = e.phone
            long = e.longitude
            lat = e.latitude
        }

        //converting image
        val bitmapProfileImage: Bitmap = BitmapFactory.decodeByteArray(profileImg, 0, profileImg!!.size)
        val bitmapProductImage: Bitmap = BitmapFactory.decodeByteArray(productImg, 0, productImg!!.size)

        //setting up variable
        showTitle.text = title
        showName.text = name
        showPrice.text = price
        showProfileImage.setImageBitmap(bitmapProfileImage)
        showProductImage.setImageBitmap(bitmapProductImage)
        showDescription.text = description
        showEmail.text = email
        showPhone.text = phone

        val maps = view.findViewById<Button>(R.id.showMap)
        maps.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireActivity(), MapActivity::class.java)
            val bundle = Bundle()
            bundle.putString("x", long)
            bundle.putString("y", lat)
            intent.putExtras(bundle)
            startActivity(intent)
        })

        showPhone.setOnClickListener(View.OnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel: $phone")
            startActivity(dialIntent)
        })
    }

}
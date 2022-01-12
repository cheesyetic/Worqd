package com.coding.projectkuliah

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.coding.projectkuliah.model.UserModel
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.IOException

class SettingProfileFragment : Fragment() {

    lateinit var profilephoto: CircleImageView
    lateinit var bitmap: Bitmap
    var id_user: Int = -1
    lateinit var DBHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.settingProfileToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        DBHelper = DBHelper(requireActivity())

        //hooks
        profilephoto = view.findViewById<CircleImageView>(R.id.profilePhoto)
        val profilename = view.findViewById<EditText>(R.id.editName)
        val profileusername = view.findViewById<EditText>(R.id.editUsername)
        val profilephone = view.findViewById<EditText>(R.id.editPhone)
        val profileemail = view.findViewById<EditText>(R.id.editEmail)

        id_user = DBHelper.checkAccount()
        val itemUser: List<UserModel> = DBHelper.viewUserDetail(id_user)

        //info user
        var user_name = "x"
        var username = "x"
        var password = "x"
        var phone = "x"
        var email = "x"
        var image:ByteArray

        //collecting data from db
        val items: UserModel = itemUser[0]
        image = items.image
        for(e in itemUser){
            user_name = e.user_name
            username = e.username
            password = e.password
            phone = e.phone
            email = e.email
            image = e.image
        }

        //converting image
        val bitmapImage: Bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)

        //setting up the variable
        profilephoto.setImageBitmap(bitmapImage)
        profileusername.setText(username)
        profilename.setText(user_name)
        profilephone.setText(phone)
        profileemail.setText(email)

        //updating variable
        val updateBtn = view.findViewById<Button>(R.id.updateBtn)
        updateBtn.setOnClickListener(View.OnClickListener {
            DBHelper.updateUserDetail(id_user, profilename.text.toString(), profileusername.text.toString(),
                profileemail.text.toString(), profilephone.text.toString())
            Toast.makeText(requireActivity(), "Update Successful!", Toast.LENGTH_LONG).show()
        })

        val editPhoto = view.findViewById<TextView>(R.id.changeProfilePhoto)
        editPhoto.setOnClickListener(View.OnClickListener {
            chooseFile()
        })
    }
    //updatingimages
    fun chooseFile() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select your picture"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val FilePath = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, FilePath)
                profilephoto.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            uploadPhoto(id_user, getStringImage(bitmap))
            Toast.makeText(requireActivity(), "Update photo successful!", Toast.LENGTH_LONG).show()
        }
    }

    fun getStringImage(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream =
            ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageByteArray = byteArrayOutputStream.toByteArray()
        return imageByteArray
    }

    fun uploadPhoto(id:Int?, bitmap: ByteArray){
        DBHelper.UpdateProfilePhoto(id, bitmap)
    }
}
package com.coding.projectkuliah

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coding.projectkuliah.model.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    lateinit var DBHelper: DBHelper
    val user_id: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DBHelper = DBHelper(requireActivity())
        val user_id = DBHelper.checkAccount()

        //hooks
        val setting = view.findViewById<TextView>(R.id.settings)
        val logout = view.findViewById<TextView>(R.id.logout)
        val profilename = view.findViewById<TextView>(R.id.profileUsername)
        val profilephoto = view.findViewById<CircleImageView>(R.id.profileImage)
        val itemUser: List<UserModel> = DBHelper.viewUserDetail(user_id)

        //infouser
        var username = "x"
        var photoprofile: ByteArray

        //collecting info from db
        val items: UserModel = itemUser[0]
        photoprofile = items.image
        for(e in itemUser){
            username = e.username
            photoprofile = e.image
        }
        val bitmapImage: Bitmap = BitmapFactory.decodeByteArray(photoprofile, 0, photoprofile.size)

        //setup variable
        profilename.text = username
        profilephoto.setImageBitmap(bitmapImage)

        setting.setOnClickListener(View.OnClickListener {
            val settingProfileFragments = SettingProfileFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, settingProfileFragments)
            transaction.addToBackStack(null)
            transaction.commit()
        })

        logout.setOnClickListener(View.OnClickListener {
            DBHelper.logoutAccount(user_id)
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        })
    }
}
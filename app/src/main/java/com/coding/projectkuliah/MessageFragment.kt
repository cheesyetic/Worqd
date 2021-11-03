package com.coding.projectkuliah

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class MessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listview = view.findViewById<ListView>(R.id.messagelistview)
        listview.adapter = MyCustomAdapter(requireActivity())
    }

    private class MyCustomAdapter(context : Context): BaseAdapter(){

        private val mContext : Context
        private var images = arrayOf(R.drawable.messageprofile1, R.drawable.messageprofile2)
        private val name = arrayListOf<String>(
            "Izdeveloper", "Imcodeexpert"
        )

        init{
            mContext = context
        }

        override fun getCount(): Int {
            return images.size
        }

        override fun getItem(p0: Int): Any {
            return "TRY STRING"
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMessage = layoutInflater.inflate(R.layout.message_row, p2, false)

            //setting up profileimage
            val messageImage = rowMessage.findViewById<ImageView>(R.id.messageImage)
            messageImage.setImageResource(images.get(p0))
            //setting up name text
            val nameTextView = rowMessage.findViewById<TextView>(R.id.tvName)
            nameTextView.text = name.get(p0)

            return rowMessage

        }
    }
}
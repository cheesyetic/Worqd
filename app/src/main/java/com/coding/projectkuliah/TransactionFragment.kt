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

class TransactionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listview = view.findViewById<ListView>(R.id.transactionlistview)
        listview.adapter = MyCustomAdapter(requireActivity())
    }

    private class MyCustomAdapter(context : Context): BaseAdapter(){

        private val mContext : Context
        private val work = arrayListOf<String>(
            "App Design", "UI Design"
        )
        private val status = arrayListOf<String>(
            "On progress", "Reviewing"
        )

        init{
            mContext = context
        }

        override fun getCount(): Int {
            return work.size
        }

        override fun getItem(p0: Int): Any {
            return "TRY STRING"
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowTransaction = layoutInflater.inflate(R.layout.transaction_row, p2, false)

            //setting up work text
            val workTextView = rowTransaction.findViewById<TextView>(R.id.tvWork)
            workTextView.text = work.get(p0)
            //setting up status text
            val statusTextView = rowTransaction.findViewById<TextView>(R.id.tvStatus)
            statusTextView.text = status.get(p0)

            return rowTransaction

        }
    }
}
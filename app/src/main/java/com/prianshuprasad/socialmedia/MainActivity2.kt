package com.prianshuprasad.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    private lateinit var adapter: postadapter
    private lateinit var postdaoi: postdao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        setuprecyclerview()//recyclerview()


    }

//    fun setuprecyclerview(){
////
//     postdaoi= postdao()
//
//        val postcollection= postdaoi.postcollection
//        val query= postcollection.orderBy("time", Query.Direction.DESCENDING)
//        val recyclerViewoptions= FirestoreRecyclerOptions.Builder<postdata>().setQuery(query,postdata::class.java).build()
//        adapter= postadapter(recyclerViewoptions,this)
//        rctemp.adapter= adapter
//
//
//        rctemp.layoutManager= LinearLayoutManager(this)
//
//
//    }
//    override fun onStart()
//    {
//        super.onStart()
//        adapter.startListening()
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }
//
//
//
//
}
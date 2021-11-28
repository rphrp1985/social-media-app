package com.prianshuprasad.socialmedia

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class postdao {

    val db= FirebaseFirestore.getInstance()
    val postcollection= db.collection("posts");
  val auth= Firebase.auth;


    fun addpost(postdata:String){




        val userid= auth.currentUser!!.uid

        GlobalScope.launch {

            val userdaoi = userdao()

            val user = userdaoi.getuserbyid(userid).await().toObject(user::class.java)!!


            val currenttime= System.currentTimeMillis()
//            val temp=
        val post= postdata(postdata,user,currenttime)
           postcollection.document().set(post);
        }



    }


    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return postcollection.document(postId).get()
    }

    fun updatelike(postid:String) {

    var re:Int=0;

        val currentuser= auth.currentUser!!.uid

        GlobalScope.launch {
           val postd = getPostById(postid).await().toObject(postdata::class.java)!!

//            val arr= post?.likedby


                Log.d("rppp","rpppp");
                if(postd.likedby.contains(currentuser)) {
                    re=0;
                    postd.likedby.remove(currentuser)
                }
                else {
                    postd.likedby.add(currentuser);
                    re=1;
                }
                postcollection.document(postid).set(postd);

            val x=re;


        }

    }




}
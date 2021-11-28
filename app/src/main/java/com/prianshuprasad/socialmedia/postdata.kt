package com.prianshuprasad.socialmedia

data class postdata(

    val posttext:String="",

    val owner: user=user() ,
    val time:Long=0,
    val likedby: ArrayList<String> = ArrayList()

                    )
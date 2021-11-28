package com.prianshuprasad.socialmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post.*

class post : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
    }

    fun postnow(view: android.view.View) {
       val text= posttext.text.toString()

        if( !text.isEmpty() ) {
            val postdao = postdao()

            postdao.addpost(text)
            finish()


        }





    }
}
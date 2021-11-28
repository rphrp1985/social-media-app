package com.prianshuprasad.socialmedia

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseuser: FirebaseAuth
private lateinit var adapter: postadapter
    private lateinit var drawerl: DrawerLayout
    private lateinit var  actionbartoggle: ActionBarDrawerToggle
   private lateinit var navview:NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
//        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//     pgbar.visibility= View.GONE

        if(Firebase.auth.currentUser==null)
        {
            startActivity(Intent(this,signin::class.java))
        }

   drawerl= findViewById(R.id.my_drawer_layout)
        actionbartoggle= ActionBarDrawerToggle(this,drawerl,R.string.nav_open,R.string.nav_close)

        drawerl.addDrawerListener(actionbartoggle)
        actionbartoggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

   navview= findViewById<NavigationView>(R.id.navview)
        navview.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener(){

//            Toast.makeText(this,"rpiiii",Toast.LENGTH_LONG).show()

            val id = it.itemId
            val idi = R.id.signoutvutton

//            Toast.makeText(this,"rp",Toast.LENGTH_LONG).show()
            if(id==idi)
            {

                Firebase.auth.signOut()
//                pgbar.visibility= View.VISIBLE

//                Toast.makeText(this,"Sign out suceessful",Toast.LENGTH_LONG).show()
                val inent = Intent(this, signin::class.java)
                startActivity(intent)



                 }






            return@OnNavigationItemSelectedListener false
        });




//        var acclogo = R.id.acclogo
//        var accname:TextView= findViewById(R.id.accname)

         var navv= navview.getHeaderView(0)
        var accname:TextView= navv.findViewById(R.id.accname)
   var acclogo:ImageView= navv.findViewById(R.id.acclogo)
//        accname.setText("rp")
//         acclogo.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        Glide.with( acclogo.context ).load( Firebase.auth.currentUser!!.photoUrl ).circleCrop().into(acclogo)
        GlobalScope.launch {
            accname.setText(Firebase.auth.currentUser!!.displayName.toString())

        }

//        val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.music)
//
//        mediaPlayer.start()



        setuprecyclerview()

//        val intenti= Intent(this,MainActivity2::class.java)
//        startActivity(intenti);


    }

    fun setuprecyclerview()
    {

        val postdao= postdao()

        val postcollection= postdao.postcollection
        val query= postcollection.orderBy("time", Query.Direction.DESCENDING)
        val recyclerViewoptions= FirestoreRecyclerOptions.Builder<postdata>().setQuery(query,postdata::class.java).build()
        adapter= postadapter(recyclerViewoptions,this)
        recyclerviewid.adapter= adapter
        recyclerviewid.layoutManager= LinearLayoutManager(this)




    }
    override fun onStart()
    {
        super.onStart()
        adapter.startListening()

    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menubar, menu);
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        val idi = R.id.signoutvutton

        if(id==idi)
        {

            Firebase.auth.signOut()
//                pgbar.visibility= View.VISIBLE

//                Toast.makeText(this,"Sign out suceessful",Toast.LENGTH_LONG).show()
            val inent = Intent(this, signin::class.java)
            startActivity(intent)



        }
        if (actionbartoggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    fun fabbutton(view: android.view.View) {
        val intent= Intent(this,post::class.java);
        startActivity(intent)


    }

   fun onLikeClicked(id: String): Int {

//       Log.d("main","rpii");

  val pd= postdao()
       var re:Int=1;

//       val postcollection= pd.postcollection
//
val currentuser= Firebase.auth.currentUser!!.uid.toString()
//var posti:postdata= postdata();
//
//
//      GlobalScope.launch {
//          posti = pd.getPostById(id).await().toObject(postdata::class.java)!!
//
////          if(posti.likedby.contains(currentuser))
////         posti.likedby.add(currentuser);
//
//          if(posti.likedby.contains(currentuser)) {
//              re=0;
//              posti.likedby.remove(currentuser)
//          }
//          else {
//              posti.likedby.add(currentuser);
//              re=1;
//          }
//          postcollection.document(id).set(posti);
//
//
//
//      }
//
//    val holder= Handler()
//
//
//
//       holder.postDelayed({
//           Toast.makeText(this, "rp ${posti.posttext}", Toast.LENGTH_LONG).show()
//
//
//       },5000
//
//
//       )
//
//
//
//

       val mediaPlayer: MediaPlayer = MediaPlayer.create( applicationContext , R.raw.music)

       mediaPlayer.start()

       GlobalScope.launch {

           pd.updatelike(id);
          val posti = pd.getPostById(id).await().toObject(postdata::class.java)!!

           if(posti.likedby.contains(currentuser))
               re=1;
           else
               re=0;


       }


return re;


   }


}
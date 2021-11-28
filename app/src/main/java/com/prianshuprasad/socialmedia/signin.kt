package com.prianshuprasad.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class signin : AppCompatActivity() {
     var RC_SIGN_IN: Int=123
    var TAG="1"
    private lateinit var googleSignInClient: GoogleSignInClient
       private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_signin)


      Toast.makeText(this,"Please Login",Toast.LENGTH_LONG).show()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id ))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


  auth= Firebase.auth

        signinbutton.setOnClickListener{
            signIn()
        }





    }
    override fun onStart() {
        super.onStart()


        pgibar.visibility = View.VISIBLE

        val handler = Handler()

        handler.postDelayed({
            pgibar.visibility = View.GONE
            loadingimag.visibility= View.GONE

            updateUI(Firebase.auth.currentUser)

        },3000)









    }

//    fun signIN(view: View) {
//        Toast.makeText(this,"rp",Toast.LENGTH_LONG).show()
////        signinbutton.visibility= View.GONE
//
//        signIn();
//    }signIn

    private fun signIn() {
        signinbutton.setVisibility(View.GONE)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                 handlesignin(task)
        }
    }

    private fun handlesignin(task: Task<GoogleSignInAccount>?) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task?.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e)

    }


}

    private fun firebaseAuthWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        progessbar.visibility= View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            val auth= auth.signInWithCredential(credential).await()
            val firebaseUser= auth.user
            withContext(Dispatchers.Main){
                updateUI(firebaseUser)
            }
        }



    }

    private fun updateUI(firebaseUser: FirebaseUser?) {

             progessbar.setVisibility(View.GONE)


             signinbutton.setVisibility(View.GONE)

        if(firebaseUser!=null)
        {
            val useri = user(firebaseUser.displayName.toString(),firebaseUser.uid,firebaseUser.photoUrl.toString() )

            val dao= userdao()

            dao.adduser(useri)






            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        else
        {

         signinbutton.setVisibility(View.VISIBLE)
            progessbar.setVisibility(View.GONE)

        }

    }


}
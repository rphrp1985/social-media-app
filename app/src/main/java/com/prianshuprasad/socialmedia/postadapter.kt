package com.prianshuprasad.socialmedia

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.Utils
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class postadapter(options: FirestoreRecyclerOptions<postdata>, val listener: MainActivity) :
    FirestoreRecyclerAdapter<postdata,viewholder>(options) {

    val currentuser= Firebase.auth.currentUser!!.uid



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

        val viewholderi= viewholder(LayoutInflater.from(parent.context).inflate(R.layout.postitem,parent,false))


 var x:Int=1




        viewholderi.likebutton.setOnClickListener{

            x=listener.onLikeClicked(snapshots.getSnapshot(viewholderi.adapterPosition).id)







            val postdaou= postdao()
//             val currenuser= Firebase.auth.currentUser!!.uid
            GlobalScope.launch {
                val postii = postdaou.getPostById(snapshots.getSnapshot(viewholderi.adapterPosition).id).await().toObject(postdata::class.java)!!


                if(postii.likedby.contains(currentuser))
                    x=1
                else
                    x=0;

                if (x == 1)
                    viewholderi.likebutton.setImageResource(R.drawable.ic_baseline_thumb_up_24_blue)
                else
                    viewholderi.likebutton.setImageResource(R.drawable.ic_baseline_thumb_up_24)
            }


        }




        return viewholderi


    }

    override fun onBindViewHolder(holder: viewholder, position: Int, model: postdata) {
     holder.profilename.text= model.owner.username.toString()
        holder.posttext.text= model.posttext.toString()
        holder.likecount.text= model.likedby.size.toString()
        Glide.with(holder.profilepic.context).load(model.owner.photourl).circleCrop().into(holder.profilepic)

        val utils= Utils()

        holder.timestamp.text= utils.getTimeAgo(model.time)


var x:Int
        val postdaou= postdao()
//             val currenuser= Firebase.auth.currentUser!!.uid
        GlobalScope.launch {
            val postii = postdaou.getPostById(snapshots.getSnapshot(holder.adapterPosition).id).await().toObject(postdata::class.java)!!

            if(postii.likedby.contains(currentuser))
                x=1
            else
                x=0;

            if (x == 1)
                holder.likebutton.setImageResource(R.drawable.ic_baseline_thumb_up_24_blue)
            else
                holder.likebutton.setImageResource(R.drawable.ic_baseline_thumb_up_24)
        }




    }
    interface IPostAdapter {
        fun onLikeClicked(postId: String)


    }
}

class viewholder(item: View):RecyclerView.ViewHolder(item){

    val profilepic:ImageView= item.findViewById(R.id.profilepic)
    val profilename:TextView=item.findViewById(R.id.profilename)
    val posttext:TextView= item.findViewById(R.id.posttext)
    val likecount:TextView=item.findViewById(R.id.likecount)
  val likebutton:ImageView=item.findViewById(R.id.likebittom)

val timestamp:TextView=item.findViewById(R.id.timestamp)



}

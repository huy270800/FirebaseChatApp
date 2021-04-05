package square.ball.firebasechatapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

import square.ball.firebasechatapp.R
import square.ball.firebasechatapp.model.User

class ChatActivity : AppCompatActivity() {

    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var intent = getIntent()
        var userId = intent.getStringExtra("userId")

        imgBack.setOnClickListener{
            onBackPressed()
        }


        firebaseUser = FirebaseAuth.getInstance().currentUser

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)




        reference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user =  snapshot.getValue(User::class.java)
                tvUserName.text = user!!.userName
                if (user.profileImage == ""){
                    imgProfile.setImageResource(R.drawable.profile)
                }
                else {
                    Glide.with(this@ChatActivity).load(user.profileImage).into(imgProfile)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        btnSendMessage.setOnClickListener{
            var message:String = etMessage.text.toString()

            if (message.isEmpty()){
                Toast.makeText(applicationContext,"message is empty", Toast.LENGTH_SHORT).show()
            }
            else{
                sendMessage(firebaseUser!!.uid, userId, message)
            }
        }

    }
    private fun sendMessage(senderId:String, receiverId:String, message:String){
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap:HashMap<String,String> = HashMap()

        hashMap.put("senderId" , senderId)
        hashMap.put("receiverId" , receiverId)
        hashMap.put("message" , message)

        reference!!.child("Chat").push().setValue(hashMap)

    }

}
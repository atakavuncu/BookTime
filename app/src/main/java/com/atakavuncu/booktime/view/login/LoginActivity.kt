package com.atakavuncu.booktime.view.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.atakavuncu.booktime.MainActivity
import com.atakavuncu.booktime.R
import com.atakavuncu.booktime.data.model.UserModel
import com.atakavuncu.booktime.data.repository.UserRepository
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameText : EditText
    private lateinit var passwordText : EditText
    private lateinit var loginButton: Button
    private lateinit var errorLoginMessage : TextView
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        val window = window
        window.statusBarColor = Color.parseColor("#D11E2F")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameText = findViewById(R.id.usernameText)
        passwordText = findViewById(R.id.passwordText)
        loginButton = findViewById(R.id.loginButton)
        errorLoginMessage = findViewById(R.id.errorLoginMessage)
        database = FirebaseDatabase.getInstance().reference

        loginButton.setOnClickListener {
            val usernameInput = usernameText.text.toString()
            val passwordInput = passwordText.text.toString()
            //Control the users registration in the database
            val query : Query = database.child("users").orderByChild("username").equalTo(usernameInput)
            query.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var isAuth = false
                    var user: UserModel? = null
                    for (userSnapshot in dataSnapshot.children){
                        user = userSnapshot.getValue(UserModel::class.java)
                        if (user?.password == passwordInput){
                            isAuth = true
                            break
                        }
                    }
                    if(isAuth){
                        val intent = Intent(this@LoginActivity, MainActivity::class.java).also {
                            it.putExtra("EXTRA_DATA", user?.username)
                            startActivity(it)
                        }
                    } else{
                        errorLoginMessage.text = "Kullanıcı adı veya parola yanlış"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    errorLoginMessage.text = "Veritabanı Hatası: ${databaseError.message}"
                }
            })
        }

    }
}
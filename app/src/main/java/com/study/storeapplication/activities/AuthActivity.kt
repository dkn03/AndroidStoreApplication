package com.study.storeapplication.activities

import SessionData
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.study.storeapplication.R
import db.Entities.User
import db.UserViewModel
import db.UserViewModelFactory
import kotlinx.coroutines.runBlocking


class AuthActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as App).userRepository, (application as App).productRepository)
    }

    private fun checkInputData(user: User, inputLogin: String, inputPassword:String): String{
        return if ((inputLogin.equals(user.email)) && inputPassword.equals(user.password)){
            "Логин и пароль введены успешно"
        } else{
            "Неправильные логин или пароль"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val loginField = findViewById<TextInputEditText>(R.id.loginInput)
        val passwordField = findViewById<TextInputEditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.payment_activity_pay_button)



        loginButton.setOnClickListener{
            val login = loginField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            var messageText = String.format("Логин - %s, Пароль - %s", login, password)
            runBlocking{
                userViewModel.getUserByEmail(login).await().observe(
                    this@AuthActivity, Observer {
                        value ->
                        if (value == null){
                            Toast.makeText(this@AuthActivity, "Неверный логин и/или пароль", Toast.LENGTH_LONG).show()
                        }
                        else {
                            SessionData.userData = value
                            this@AuthActivity.finish()

                        }
                    }
                )
            }


        }

        val moveBackButton = findViewById<LinearLayout>(R.id.go_back_layout)
        moveBackButton.setOnClickListener{
            this.finish()
        }
    }
}
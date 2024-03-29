package com.study.storeapplication.activities

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

class SignUpActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as App).userRepository, (application as App).productRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val emailField = findViewById<TextInputEditText>(R.id.signUpPageEmailInput)
        val phoneNumberField = findViewById<TextInputEditText>(R.id.signUpPagePhoneNumberInput)
        val passwordField = findViewById<TextInputEditText>(R.id.signUpPagePasswordInput)
        val nameField = findViewById<TextInputEditText>(R.id.signUpPageNameInput)

        val signUpButton =  findViewById<Button>(R.id.payment_activity_pay_button)

        val moveBackButton = findViewById<LinearLayout>(R.id.go_back_layout)
        moveBackButton.setOnClickListener{
            this.finish()
        }

        signUpButton.setOnClickListener{
            val email: String = emailField.text.toString().trim()
            val phoneNumber: String = phoneNumberField.text.toString().trim()
            val password: String = passwordField.text.toString().trim()
            val name: String = nameField.text.toString().trim()
            runBlocking{
                userViewModel.getUserByEmail(email).await().observe(this@SignUpActivity,
                    Observer {
                        user ->
                        if (user != null){
                            Toast.makeText(this@SignUpActivity, "Данный логин уже используется", Toast.LENGTH_LONG).show()
                        }
                        else{
                            userViewModel.insert(User(email, phoneNumber, password, name))
                        }

                    }
                )
            }
            this@SignUpActivity.finish()


        }
    }
}
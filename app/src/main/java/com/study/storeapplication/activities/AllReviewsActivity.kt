package com.study.storeapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.study.storeapplication.R

class AllReviewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reviews)

        val moveBackButton = findViewById<LinearLayout>(R.id.go_back_layout)
        moveBackButton.setOnClickListener{
            this.finish()
        }
    }
}
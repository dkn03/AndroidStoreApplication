package com.study.storeapplication.activities
import android.app.Application
import db.AppDatabase
import db.CartRepository
import db.ProductRepository
import db.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

public class App: Application(){
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    val database by lazy {AppDatabase.getDatabase(this, applicationScope)}
    val userRepository by lazy {UserRepository(database.userDao())}
    val productRepository by lazy {ProductRepository(database.productDao())}
    val cartRepository by lazy { CartRepository(database.cartDao()) }
}
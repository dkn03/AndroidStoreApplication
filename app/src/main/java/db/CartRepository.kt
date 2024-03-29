package db

import androidx.annotation.WorkerThread
import db.Entities.Cart
import db.Entities.CartProductAndAmount
import db.dao.CartDao
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allCarts: Flow<List<Cart>> = cartDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cart: Cart) {
        cartDao.insert(cart)
    }

    @WorkerThread
    suspend fun delete(cart: Cart){
        cartDao.delete(cart)
    }

    @WorkerThread
    suspend fun getUserProducts(id: Long): Flow<List<Cart>>{
        return cartDao.getUserProducts(id)
    }

    @WorkerThread
    suspend fun getAllProducts(): Flow<List<Cart>> {
        return cartDao.getAll()
    }

    @WorkerThread
    suspend fun getItem(userId: Long, productId: Long): Flow<Cart>{
        return cartDao.getItem(userId, productId)
    }
    @WorkerThread
    suspend fun getUserProductsAndAmount(id: Long): Flow<List<CartProductAndAmount>>{
        return cartDao.getUserProductsAndAmount(id)
    }



    @WorkerThread
    suspend fun update(cart: Cart){
        cartDao.update(cart)
    }

    @WorkerThread
    suspend fun getAll(): Flow<List<Cart>>{
        return cartDao.getAll()
    }
}
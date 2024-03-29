package db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import db.Entities.Cart
import db.Entities.CartProductAndAmount
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): Flow<List<Cart>>

    @Query("SELECT * FROM cart WHERE user_id = :id")
    fun getUserProducts(id: Long): Flow<List<Cart>>

    @Query("SELECT cart.user_id, cart.product_id, products.name, products.price, products.images, cart.amount FROM products INNER JOIN cart on products.id = cart.product_id WHERE cart.user_id = :id")
    fun getUserProductsAndAmount(id: Long): Flow<List<CartProductAndAmount>>

    @Query("SELECT * FROM cart WHERE user_id = :userId AND product_id = :productId")
    fun getItem(userId: Long, productId: Long): Flow<Cart>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cart: Cart)
    @Update
    fun update(cart: Cart)

    @Delete
    suspend fun delete(cart: Cart)


}
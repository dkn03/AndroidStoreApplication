package db.Entities

import androidx.room.ColumnInfo

data class CartProductAndAmount(
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("product_id") val productId: Long,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("price") val price: Double,
    @ColumnInfo("images") val images: String,
    @ColumnInfo("amount") val amount: Int
)
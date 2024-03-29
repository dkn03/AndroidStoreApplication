package db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "cart",
    foreignKeys = [
    ForeignKey(
        entity=User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id")
),
    ForeignKey(
        entity=Product::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("product_id")
    )],
    primaryKeys = ["user_id", "product_id"])
data class Cart(
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "amount") val amount: Int
)

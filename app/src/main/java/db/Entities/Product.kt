package db.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("amount") val amount: Int,
    @ColumnInfo("price") val price: Double,
    @ColumnInfo("images") val images: String,
    @ColumnInfo("characteristics") val characteristics: String
){
    @PrimaryKey(autoGenerate = true) var id: Long = 0

}

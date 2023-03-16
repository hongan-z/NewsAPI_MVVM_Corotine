package Model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news_table")
data class MyDBNews(

   @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val author: String,
    val content: String,
    val urlImage: String
)


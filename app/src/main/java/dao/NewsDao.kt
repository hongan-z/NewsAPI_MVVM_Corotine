package dao

import Model.MyDBNews
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_table ORDER BY id ASC")
    fun readAllNews():LiveData<List<MyDBNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNews(myDBNews: MyDBNews)

    @Delete
    fun deleteItemNews(myDBNews: MyDBNews)

    @Query("DELETE FROM news_table ")
    fun deleteAll()

}
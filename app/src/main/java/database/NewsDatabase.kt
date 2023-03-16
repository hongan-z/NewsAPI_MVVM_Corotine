package database

import Model.Converts
import Model.MyDBNews
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dao.NewsDao



@Database(entities = [MyDBNews::class] , version = 3, exportSchema = false)
@TypeConverters(Converts::class)
abstract class NewsDatabase:RoomDatabase() {

    abstract fun newsDao() : NewsDao

    companion object{
        @Volatile
        private var INSTANCE : NewsDatabase?=null
        fun getDatabase(context: Context):NewsDatabase{
            val tempinstance = INSTANCE
            if (tempinstance!=null){
                return tempinstance
            }
            synchronized(this){
                val intance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_table"


                ).allowMainThreadQueries().build()
                INSTANCE = intance
                return intance
            }
        }
    }
}

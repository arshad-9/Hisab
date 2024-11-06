package ars.example.hisab.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [entitiy::class], version = 11)
abstract class database:RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        @Volatile
        var INSTANCE: database? = null

        fun getDataBaseInstance(context: Context): database {
            val tempInstence = INSTANCE
            if(tempInstence!=null)
            {
                return tempInstence
            }
            synchronized(this){
                INSTANCE = Room.databaseBuilder(context, database::class.java,"MyDataBase")
                    .fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }



}
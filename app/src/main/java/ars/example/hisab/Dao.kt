package ars.example.hisab

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert
    suspend fun insert(obj:entitiy)

    @Delete
    suspend fun delete(entitiy: entitiy)

    @Update
    suspend fun update(entitiy: entitiy)

    @Query("SELECT *FROM MyDataBase where id=:id")
   suspend fun getById(id:Int): entitiy

    @Query("SELECT *FROM MyDataBase")
    fun getAll(): Flow<List<entitiy>>
    @Query("SELECT *FROM MyDataBase where Status=:status")
    fun getUnpaid(status:Boolean): Flow<List<entitiy>>



}
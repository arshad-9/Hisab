package ars.example.hisab

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "MyDataBase")
data class entitiy(
    @PrimaryKey(autoGenerate = true)
    val id :Int=0,
    var ItemName:String,
    var Total:Double,
    var Units :Double ,
    val Amount_PerUnit :Double,
    var Date :String="Today",
    var Status :Boolean
)

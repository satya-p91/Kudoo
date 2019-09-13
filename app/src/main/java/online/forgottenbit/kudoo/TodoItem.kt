package online.forgottenbit.kudoo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "todoTable")
data class TodoItem(val title : String) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}
package online.forgottenbit.kudoo

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.Query


@Dao
interface TodoItemDao {

    @Query("Select * from todoTable")
    fun loadAllTodos() : LiveData<List<TodoItem>>

    @Insert(onConflict = IGNORE)
    fun insertTodo(todo: TodoItem)

    @Delete
    fun deleteTodo(todo: TodoItem)

}


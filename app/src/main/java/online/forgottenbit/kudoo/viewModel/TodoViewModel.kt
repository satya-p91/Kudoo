package online.forgottenbit.kudoo.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import online.forgottenbit.kudoo.AppDatabase
import online.forgottenbit.kudoo.DB
import online.forgottenbit.kudoo.TodoItem
import online.forgottenbit.kudoo.dbScope


class TodoViewModel(app : Application) : AndroidViewModel(app){
    private val dao by lazy { AppDatabase.getDatabase(getApplication()).todoItemDao()}


    suspend fun getTodos() : LiveData<List<TodoItem>> = withContext(DB){

        dao.loadAllTodos()
    }

    fun add(todo: TodoItem) = dbScope.launch { dao.insertTodo(todo) }

    fun delete(todo: TodoItem) = dbScope.launch { dao.deleteTodo(todo) }
}
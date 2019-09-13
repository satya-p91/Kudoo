package online.forgottenbit.kudoo.view.add

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.coroutines.launch
import online.forgottenbit.kudoo.R
import online.forgottenbit.kudoo.TodoItem
import online.forgottenbit.kudoo.dbScope
import online.forgottenbit.kudoo.view.common.getViewModel
import online.forgottenbit.kudoo.viewModel.TodoViewModel

class AddTodoActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        viewModel = getViewModel(TodoViewModel::class)

        setUpListeners()
    }



    private fun setUpListeners() {
        btnAddTodo.setOnClickListener {
            val newTodo = etNewTodo.text.toString()
            dbScope.launch { viewModel.add(TodoItem(newTodo)) }
            finish()
        }
    }
}

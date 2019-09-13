package online.forgottenbit.kudoo

import android.arch.lifecycle.Observer
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import online.forgottenbit.kudoo.view.add.AddTodoActivity
import online.forgottenbit.kudoo.view.common.getViewModel
import online.forgottenbit.kudoo.viewModel.TodoViewModel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity(),CoroutineScope {


    private lateinit var viewModel: TodoViewModel  // Now references view model, not DB


    val uiScope = CoroutineScope(coroutineContext + SupervisorJob())


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        viewModel = getViewModel(TodoViewModel::class)

        setUpRecyclerView()

        /*dbScope.launch {
            viewModel.add(TodoItem("Wont show up automatically yet"))
        }*/



        fab.setOnClickListener { view ->
           startActivity(Intent(this,AddTodoActivity::class.java))
        }
    }


    private fun setUpRecyclerView() {

        with(listWork) {
            adapter = RecyclerViewAdapter(mutableListOf(),onRecyclerItemClick())
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        uiScope.launch {
            val todosLiveData = viewModel.getTodos()
            todosLiveData.observe(this@MainActivity, Observer { todos ->
                // Observes changes in the LiveData
                todos?.let {
                    val adapter = (listWork.adapter as RecyclerViewAdapter)
                    adapter.setItems(it)  // Updates list items when data changes (next step)
                }
            })
        }
    }


    private fun onRecyclerItemClick(): (TodoItem) -> Unit = { todo ->
        dbScope.launch { viewModel.delete(todo) }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //private suspend fun sampleData() = withContext(DB){db.todoItemDao().loadAllTodos()}



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

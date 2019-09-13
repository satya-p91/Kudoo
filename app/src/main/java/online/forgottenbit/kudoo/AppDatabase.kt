package online.forgottenbit.kudoo

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

val DB = newSingleThreadContext("DB") // CoroutineContext for DB operations

val dbScope = CoroutineScope(DB)

@Database(entities =  [TodoItem::class],version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE : AppDatabase? = null


        fun getDatabase(cxt : Context) : AppDatabase{

            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(cxt, AppDatabase::class.java,"AppDatabase").build()
            }

            return INSTANCE!!
        }

        private fun prepopulateCallback(ctx: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {  // Uses onCreate callback
                    super.onCreate(db)
                    populateWithSampleData(ctx)
                }
            }
        }

        private fun populateWithSampleData(ctx: Context) {  // Adds sample data to DB
            dbScope.launch(DB) {  // DB operations must be done on a background thread
                with(getDatabase(ctx).todoItemDao()) {  // Uses DAO to insert items into DB
                    insertTodo(TodoItem("Create entity"))
                    insertTodo(TodoItem("Add a DAO for data access"))
                    insertTodo(TodoItem("Inherit from RoomDatabase"))
                }
            }
        }
    }

    abstract fun todoItemDao() : TodoItemDao

}
package online.forgottenbit.kudoo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

class RecyclerViewAdapter(private val item: MutableList<TodoItem>,
                          private val onItemCheckboxClicked: (TodoItem) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, id: Int): ViewHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.list_items,parent,false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int  = item.size

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.bindItem(item[position])
    }


    fun setItems(items: List<TodoItem>) {
        this.item.clear()
        this.item.addAll(items)
        notifyDataSetChanged()
    }


    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        val tvTodoTitle: TextView = listItemView.findViewById(R.id.textList)
        val checkbox: CheckBox = listItemView.findViewById(R.id.checkbox)

        fun bindItem(todoItem: TodoItem) {
            tvTodoTitle.text = todoItem.title
            checkbox.isChecked = false

            checkbox.setOnCheckedChangeListener { _, _ ->
                onItemCheckboxClicked(todoItem)
            }
        }

    }
}
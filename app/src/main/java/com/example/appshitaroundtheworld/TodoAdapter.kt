package com.example.appshitaroundtheworld

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appshitaroundtheworld.model.Land
class TodoAdapter(val items: List<Land>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(currentItemView: View) : RecyclerView.ViewHolder(currentItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_inhoud, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodoItem = items[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.bestemming).text = currentTodoItem.naam
            var datum: String = (currentTodoItem.dag.toString() + "/" + currentTodoItem.maand.toString() + "/" + currentTodoItem.jaar.toString())
            findViewById<TextView>(R.id.datum).text = datum
            findViewById<RatingBar>(R.id.ratingBar).rating = currentTodoItem.rating.toFloat()

        }
    }

    override fun getItemCount(): Int = items.size
}

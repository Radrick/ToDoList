package com.example.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallBack()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}


    //responsável por criar a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //passa o contexto dos layouts para o binding
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //Classe ViewHolder
    inner class TaskViewHolder(
        private val binding : ItemTaskBinding
    ): RecyclerView.ViewHolder(binding.root){

        //insere os valores do model no campos de texto do recycler view
        fun bind(item: Task) {
            binding.tvTitle.text =  item.title
            binding.tvDate.text = "${item.date} ${item.hours}"
            binding.icMore.setOnClickListener {
                showPopup(item)
            }
        }

        //infla o menu no recycler view
        private fun showPopup(item: Task){
            val icMore = binding.icMore
            val popup = PopupMenu(icMore.context, icMore)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popup.show()
        }
    }
}
class DiffCallBack : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}
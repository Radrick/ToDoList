package com.example.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.ui.AddNewTaskActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTask.adapter = adapter
        updateListener()
        insertListners()
    }

    private fun insertListners(){
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this, AddNewTaskActivity::class.java), CREATE_NEW_TAKS)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddNewTaskActivity::class.java)
            intent.putExtra(AddNewTaskActivity.ID_TASK, it.id)
            startActivityForResult(intent, CREATE_NEW_TAKS)
        }
        adapter.listenerDelete = {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_NEW_TAKS && resultCode == Activity.RESULT_OK) {
            updateListener()
            //binding.rvTask.adapter = adapter
            //adapter.submitList(TaskDataSource.getListTask())
        }
    }

    private fun updateListener(){
        adapter.submitList(TaskDataSource.getListTask())
    }

    companion object{
        private const val CREATE_NEW_TAKS = 1000
    }
}
package com.example.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.extension.format
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddNewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(ID_TASK)){
            val taskId = intent.getIntExtra(ID_TASK, 0)
            TaskDataSource.findById(taskId)?.let {

                binding.title.editText?.setText(it.title)
                binding.calendar.editText?.setText(it.date)
                binding.timer.editText?.setText(it.hours)
            }
        }

        insertListener()
    }

    private fun insertListener(){
        binding.calendar.editText?.setOnClickListener{
            //instancia o datepicker
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            //captura a seleção do datepicker
            datePicker.addOnPositiveButtonClickListener(){
                //instancia o timezone local. Necessário pois o datepicker, na seleção, vem com a data errada
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.calendar.editText!!.setText(Date(it + offset).format())
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        //Log.e("TAG", "insertListener")
        }

        binding.timer.editText?.setOnClickListener {
            val timerPicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timerPicker.addOnPositiveButtonClickListener {
                val minute = if (timerPicker.minute in 0..9) "0${timerPicker.minute}" else "${timerPicker.minute}"
                val hour = if (timerPicker.hour in 0..9) "0${timerPicker.hour}" else "${timerPicker.hour}"
                binding.timer.editText!!.setText(
                    "${hour}:${minute}"
                )
            }
            timerPicker.show(supportFragmentManager, null)
        }

        binding.btnCancelTask.setOnClickListener {
            finish()
        }
        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.title.editText!!.text!!.toString(),
                date = binding.calendar.editText!!.text!!.toString(),
                hours = binding.timer.editText!!.text!!.toString(),
                id = intent.getIntExtra(ID_TASK, 0 )
            )
            TaskDataSource.setTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object {
        const val ID_TASK = "id_task"
    }
}
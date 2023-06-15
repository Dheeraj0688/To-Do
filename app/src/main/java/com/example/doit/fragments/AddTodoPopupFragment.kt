package com.example.doit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.view.textservice.TextInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.navArgument
import com.example.doit.R
import com.example.doit.databinding.FragmentAddTodoPopupBinding
import com.example.doit.utils.ToDoData
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText


class AddTodoPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListeners
    private var toDoData: ToDoData? = null
    fun setListener(listener: DialogNextBtnClickListeners) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddToDoPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )
            binding.todoEt.setText(toDoData?.Task)
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()) {
                if(toDoData==null){
                listener.onSaveTask(todoTask, binding.todoEt)}
                else{
                    toDoData?.Task=todoTask
                    listener.onUpdateTask(toDoData!!,binding.todoEt)
                }
            } else {
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }
        binding.todoClose.setOnClickListener {
            dismiss()
        }

    }

    interface DialogNextBtnClickListeners {
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoEt: TextInputEditText)
    }
}
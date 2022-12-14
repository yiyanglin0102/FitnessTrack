package com.fitnesstrack.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitnesstrack.R
import com.fitnesstrack.adapters.TaskListItemsAdapter
import com.fitnesstrack.firebase.Firestore
import com.fitnesstrack.firebase.models.Board
import com.fitnesstrack.firebase.models.Card
import com.fitnesstrack.firebase.models.Task
import com.fitnesstrack.utilities.Constants
import kotlinx.android.synthetic.main.activity_task_list.*

class TaskListActivity : AppCompatActivity() {

    private lateinit var boardDetails: Board
    private lateinit var boardDocumentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        if (intent.hasExtra(Constants.DOCUMENT_ID)) {
            boardDocumentId = intent.getStringExtra(Constants.DOCUMENT_ID)!!
        }
        Firestore().getBoardDetails(this@TaskListActivity, boardDocumentId)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_task_list_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = boardDetails.name
        }

        toolbar_task_list_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun boardDetails(board: Board) {
        boardDetails = board
        setupActionBar()
        val addTaskList = Task(resources.getString(R.string.add_list))
        board.taskList.add(addTaskList)

        rv_task_list.layoutManager =
            LinearLayoutManager(this@TaskListActivity, LinearLayoutManager.HORIZONTAL, false)
        rv_task_list.setHasFixedSize(true)

        val adapter = TaskListItemsAdapter(this@TaskListActivity, board.taskList)
        rv_task_list.adapter = adapter
    }

    fun createTaskList(taskListName: String) {
        val task = Task(taskListName, Firestore().getCurrentUserID())
        boardDetails.taskList.add(0, task)
        boardDetails.taskList.removeAt(boardDetails.taskList.size - 1)
        Firestore().addUpdateTaskList(this@TaskListActivity, boardDetails)
    }

    fun addUpdateTaskListSuccess() {
        Firestore().getBoardDetails(this@TaskListActivity, boardDetails.documentId)
    }

    fun updateTaskList(position: Int, listName: String, model: Task) {
        val task = Task(listName, model.createdBy)
        boardDetails.taskList[position] = task
        boardDetails.taskList.removeAt(boardDetails.taskList.size - 1)
        Firestore().addUpdateTaskList(this@TaskListActivity, boardDetails)
    }

    fun deleteTaskList(position: Int) {
        boardDetails.taskList.removeAt(position)
        boardDetails.taskList.removeAt(boardDetails.taskList.size - 1)
        Firestore().addUpdateTaskList(this@TaskListActivity, boardDetails)
    }

    fun addCardToTaskList(position: Int, cardName: String) {
        boardDetails.taskList.removeAt(boardDetails.taskList.size - 1)
        val cardAssignedUsersList: ArrayList<String> = ArrayList()
        cardAssignedUsersList.add(Firestore().getCurrentUserID())
        val card = Card(cardName, Firestore().getCurrentUserID(), cardAssignedUsersList)
        val cardsList = boardDetails.taskList[position].cards
        cardsList.add(card)
        val task = Task(
            boardDetails.taskList[position].title,
            boardDetails.taskList[position].createdBy,
            cardsList
        )
        boardDetails.taskList[position] = task
        Firestore().addUpdateTaskList(this@TaskListActivity, boardDetails)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == CARD_DETAILS_REQUEST_CODE
        ) {
            Firestore().getBoardDetails(this@TaskListActivity, boardDocumentId)
        } else {
            Log.e("Cancelled", "Cancelled")
        }
    }

    fun cardDetails(taskListPosition: Int, cardPosition: Int) {
        val intent = Intent(this@TaskListActivity, CardDetailsActivity::class.java)
        intent.putExtra(Constants.BOARD_DETAIL, boardDetails)
        intent.putExtra(Constants.TASK_LIST_ITEM_POSITION, taskListPosition)
        intent.putExtra(Constants.CARD_LIST_ITEM_POSITION, cardPosition)
        startActivityForResult(intent, CARD_DETAILS_REQUEST_CODE)
    }

    companion object {
        const val CARD_DETAILS_REQUEST_CODE: Int = 14
    }

}

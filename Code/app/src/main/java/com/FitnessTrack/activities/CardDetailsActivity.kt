package com.fitnesstrack.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fitnesstrack.R
import com.fitnesstrack.firebase.Firestore
import com.fitnesstrack.firebase.models.Board
import com.fitnesstrack.firebase.models.Card
import com.fitnesstrack.firebase.models.Task
import com.fitnesstrack.utilities.Constants
import kotlinx.android.synthetic.main.activity_card_details.*
import kotlinx.android.synthetic.main.item_task.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CardDetailsActivity : AppCompatActivity() {

    private lateinit var boardDetails: Board
    private var taskListPosition: Int = -1
    private var cardPosition: Int = -1
    private var selectedColor: String = ""
    private var selectedDueDateMilliSeconds: Long = 0
    private var inputCalories: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        getIntentData()
        setupActionBar()
        et_name_card_details.setText(boardDetails.taskList[taskListPosition].cards[cardPosition].name)
        et_name_card_details.setSelection(et_name_card_details.text.toString().length)
        btn_update_card_details.setOnClickListener {
            if (et_name_card_details.text.toString().isNotEmpty()) {
                updateCardDetails()
            } else {
                Toast.makeText(this@CardDetailsActivity, "Enter card name.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        selectedColor = boardDetails.taskList[taskListPosition].cards[cardPosition].labelColor
        if (selectedColor.isNotEmpty()) {
            setColor()
        }

        inputCalories = boardDetails.taskList[taskListPosition].cards[cardPosition].calories
        if (inputCalories.isNotEmpty()) {
            setCalories()
        }

        selectedDueDateMilliSeconds =
            boardDetails.taskList[taskListPosition].cards[cardPosition].dueDate
        if (selectedDueDateMilliSeconds > 0) {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val selectedDate = simpleDateFormat.format(Date(selectedDueDateMilliSeconds))
            tv_select_due_date.text = selectedDate
        }

        tv_select_due_date.setOnClickListener {
            showDataPicker()
        }

        tv_select_label_color.setOnClickListener {
            labelColorsListDialog()
        }
    }


    private fun colorsList(): ArrayList<String> {

        val colorsList: ArrayList<String> = ArrayList()
        colorsList.add("#43C86F")
        colorsList.add("#D5B91D")
        colorsList.add("#F72400")

        return colorsList
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_card_details_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = boardDetails.taskList[taskListPosition].cards[cardPosition].name
        }
        toolbar_card_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setColor() {
        tv_select_label_color.text = ""
        tv_select_label_color.setBackgroundColor(Color.parseColor(selectedColor))
    }

    private fun setCalories() {
        tv_input_calories.setText(boardDetails.taskList[taskListPosition].cards[cardPosition].calories)
//        tv_input_calories.text(inputCalories)
//        tv_task_list_title. = model.title
    }

    fun addUpdateCardDetailsSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_card -> {
                alertDialogForDeleteCard(boardDetails.taskList[taskListPosition].cards[cardPosition].name)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_card, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun getIntentData() {
        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            boardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }
        if (intent.hasExtra(Constants.TASK_LIST_ITEM_POSITION)) {
            taskListPosition = intent.getIntExtra(Constants.TASK_LIST_ITEM_POSITION, -1)
        }
        if (intent.hasExtra(Constants.CARD_LIST_ITEM_POSITION)) {
            cardPosition = intent.getIntExtra(Constants.CARD_LIST_ITEM_POSITION, -1)
        }
    }

    private fun alertDialogForDeleteCard(cardName: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.alert))
        builder.setMessage(
            resources.getString(
                R.string.confirmation_message_to_delete_card,
                cardName
            )
        )
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, which ->
            dialogInterface.dismiss()
            deleteCard()
        }
        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, which ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun updateCardDetails() {
        val card = Card(
            et_name_card_details.text.toString(),
            boardDetails.taskList[taskListPosition].cards[cardPosition].createdBy,
            boardDetails.taskList[taskListPosition].cards[cardPosition].assignedTo,
            selectedColor,
            selectedDueDateMilliSeconds
        )
        val taskList: ArrayList<Task> = boardDetails.taskList
        taskList.removeAt(taskList.size - 1)
        boardDetails.taskList[taskListPosition].cards[cardPosition] = card
        Firestore().addUpdateTaskList(this@CardDetailsActivity, boardDetails)
    }

    private fun deleteCard() {
        val cardsList: ArrayList<Card> = boardDetails.taskList[taskListPosition].cards
        cardsList.removeAt(cardPosition)
        val taskList: ArrayList<Task> = boardDetails.taskList
        taskList.removeAt(taskList.size - 1)
        taskList[taskListPosition].cards = cardsList
        Firestore().addUpdateTaskList(this@CardDetailsActivity, boardDetails)
    }

    private fun labelColorsListDialog() {
        val colorsList: ArrayList<String> = colorsList()
        val listDialog = object : LabelColorListDialog(
            this@CardDetailsActivity,
            colorsList,
            resources.getString(R.string.str_select_label_color)
        ) {
            override fun onItemSelected(color: String) {
                selectedColor = color
                setColor()
            }
        }
        listDialog.show()
    }

    private fun showDataPicker() {
        val c = Calendar.getInstance()
        val year =
            c.get(Calendar.YEAR) // Returns the value of the given calendar field. This indicates YEAR
        val month = c.get(Calendar.MONTH) // This indicates the Month
        val day = c.get(Calendar.DAY_OF_MONTH) // This indicates the Day
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val sDayOfMonth = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
                val sMonthOfYear =
                    if ((monthOfYear + 1) < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
                val selectedDate = "$sDayOfMonth/$sMonthOfYear/$year"
                tv_select_due_date.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                selectedDueDateMilliSeconds = theDate!!.time
            },
            year,
            month,
            day
        )
        dpd.show()
    }
}

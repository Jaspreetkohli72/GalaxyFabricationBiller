package com.galaxyfabrication.biller

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.galaxyfabrication.biller.data.AppDatabase
import com.galaxyfabrication.biller.data.Client
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddClientActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var doneButton: Button
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        nameEditText = findViewById(R.id.clientNameInput)
        doneButton = findViewById(R.id.doneButton)
        db = AppDatabase.getDatabase(this)

        doneButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter client name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val client = Client(name = name, date = date)

            lifecycleScope.launch {
                db.clientDao().insert(client)
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}

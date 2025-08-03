package com.galaxyfabrication.biller

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.galaxyfabrication.biller.data.AppDatabase
import com.galaxyfabrication.biller.data.Client
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var addClientLauncher: ActivityResultLauncher<Intent>
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var clientAdapter: ClientAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)

        recyclerView = findViewById(R.id.clientRecyclerView)
        emptyTextView = findViewById(R.id.emptyTextView)
        val fab: FloatingActionButton = findViewById(R.id.addClientButton)

        clientAdapter = ClientAdapter(this, mutableListOf())
        recyclerView.adapter = clientAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this, AddClientActivity::class.java)
            addClientLauncher.launch(intent)
        }

        addClientLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                // No need to manually reload data; Room + Flow handles updates automatically.
            }

        observeClients()
    }

    private fun observeClients() {
        lifecycleScope.launch {
            db.clientDao().getAllClientsByDateDesc().collectLatest { clientList ->
                clientAdapter.updateClients(clientList)
                emptyTextView.visibility =
                    if (clientList.isEmpty()) TextView.VISIBLE else TextView.GONE
            }
        }
    }
}

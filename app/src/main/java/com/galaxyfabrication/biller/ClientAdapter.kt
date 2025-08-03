package com.galaxyfabrication.biller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.galaxyfabrication.biller.data.Client

class ClientAdapter(
    private val context: Context,
    private var clients: MutableList<Client>
) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        holder.clientButton.text = client.name
    }

    override fun getItemCount(): Int = clients.size

    fun updateClients(newClients: List<Client>) {
        clients.clear()
        clients.addAll(newClients)
        notifyDataSetChanged()
    }

    class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clientButton: Button = itemView.findViewById(R.id.clientButton)
    }
}

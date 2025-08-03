package com.galaxyfabrication.biller.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Insert
    suspend fun insert(client: Client)

    @Query("SELECT * FROM clients ORDER BY date DESC")
    fun getAllClientsByDateDesc(): Flow<List<Client>>
}

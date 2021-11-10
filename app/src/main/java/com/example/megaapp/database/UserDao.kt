package com.example.megaapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.megaapp.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM Users ORDER BY pk ASC")
    fun getUsers(): LiveData<List<User>>

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)


}
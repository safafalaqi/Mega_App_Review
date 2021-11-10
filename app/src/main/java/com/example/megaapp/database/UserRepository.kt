package com.example.megaapp.database

import androidx.lifecycle.LiveData
import com.example.megaapp.model.User

class UserRepository(private val dao:UserDao) {

    val getUsers: LiveData<List<User>> = dao.getUsers()

    suspend fun addUser(user: User){
        dao.addUser(user)
    }

    suspend fun updateUser(user: User){
        dao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
       dao.deleteUser(user)
    }

}
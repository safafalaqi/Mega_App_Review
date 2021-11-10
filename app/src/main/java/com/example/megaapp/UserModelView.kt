package com.example.megaapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.megaapp.database.UserDatabase
import com.example.megaapp.database.UserRepository
import com.example.megaapp.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserModelView(application: Application):AndroidViewModel(application) {

    private val repository: UserRepository
    private val users: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        users = repository.getUsers
    }

    //getter
    fun getUsers(): LiveData<List<User>>{
        return users
    }

    fun addUser(user:User){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addUser(user)
        }
    }

    fun editUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteUser(user)
        }
    }


}
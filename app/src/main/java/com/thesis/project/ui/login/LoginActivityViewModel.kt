package com.thesis.project.ui.login

import android.app.Application
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.thesis.project.data.roomdb.user.UserDao
import com.thesis.project.data.roomdb.user.UserLocalDataBase
import com.thesis.project.models.user.User
import com.thesis.project.util.SnackbarHelper

class LoginActivityViewModel(application: Application) :AndroidViewModel(application),Observable
{
    var userDao : UserDao? = null
    private var snackbarHelper = SnackbarHelper()

    var users : List<User>? = null

    companion object { var currentUser: User? = null }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    @Bindable
    var displayUserName = MutableLiveData<String>()

    @Bindable
    var displayPassword = MutableLiveData<String>()

    init
    {
        userDao = UserLocalDataBase
            .getDatabase(application)
            .userDao()
        displayUserName.value = "teszt"
        displayPassword.value = "teszt"
    }

    fun startLogin() : Boolean
    {
        if (checkIfTheEditTextsValuesAreNullOrEmpty())
        {
            Thread(Runnable {  users = userDao!!.getAllUsers() }).start()

            return delayLoginCheck()
        }
        else { showToast("Some of the fields are not filled") }
        return false
    }

    private fun delayLoginCheck() : Boolean
    {
        Thread.sleep(700)
        return loginCheck()
    }

    private fun loginCheck() : Boolean
    {
        if(checkIfUsersListContainsGivenUserInputs(users!!, displayUserName.value!!, displayPassword.value!!))
        {
            showToast("Welcome ${displayUserName.value}")
            return true
        }

        showToast("The given inputs are incorrect")
        return false
    }

    private fun checkIfTheEditTextsValuesAreNullOrEmpty() : Boolean = !(displayUserName.value.isNullOrEmpty() || displayPassword.value.isNullOrEmpty() )

    private fun checkIfUsersListContainsGivenUserInputs(users : List<User>, username : String, password: String) : Boolean
    {
        var i = 0
        var isUserInputDataCorrect = false
        while (i < users.size && !isUserInputDataCorrect)
        {
            if (users[i].username == username && users[i].password == password)
            {
                currentUser = users[i]
                isUserInputDataCorrect = true
            }
            i++
        }
        return isUserInputDataCorrect
    }

    private fun showToast(message: String){ Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.add(callback) }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.remove(callback) }
}
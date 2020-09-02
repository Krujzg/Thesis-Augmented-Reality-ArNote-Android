package com.thesis.project.ui.register

import android.app.Application
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thesis.project.data.roomdb.user.UserDao
import com.thesis.project.data.roomdb.user.UserLocalDataBase
import com.thesis.project.models.user.User
import kotlinx.coroutines.launch

class RegisterActivityViewModel (application: Application) : AndroidViewModel(application), Observable
{
    var userDao : UserDao? = null

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    @Bindable
    var displayUserName = MutableLiveData<String>()

    @Bindable
    var displayPassword = MutableLiveData<String>()

    @Bindable
    var displayPasswordAgain = MutableLiveData<String>()

    @Bindable
    var displayUserEmail = MutableLiveData<String>()

    init
    {
        userDao = UserLocalDataBase
            .getDatabase(application)
            .userDao()
    }

    fun saveNewUserIntoLocalDb() : Boolean
    {
        if (isSaveUserButtonClickable())
        {
            if (checkIfThePasswordsAreEquals())
            {
                viewModelScope.launch {
                    userDao!!.insertUser(
                        User(username = displayUserName.value!!,
                            email = displayUserEmail.value!!,
                            password = displayPassword.value!!)
                    )
                }
                showToast("Register is successful")
                return true
            }
            else { showToast("The passwords are not the same") }
        }
        else { showToast("Some of the fields are not filled") }
        return false
    }

    private fun isSaveUserButtonClickable() : Boolean = checkIfTheEditTextsValuesAreNullOrEmpty()

    private fun checkIfTheEditTextsValuesAreNullOrEmpty() : Boolean
    {
        return !(displayUserName.value.isNullOrEmpty() ||
                displayPassword.value.isNullOrEmpty() ||
                displayPasswordAgain.value.isNullOrEmpty() ||
                displayUserEmail.value.isNullOrEmpty())
    }

    private fun checkIfThePasswordsAreEquals() : Boolean = displayPassword.value!! == displayPasswordAgain.value!!

    private fun showToast(message: String){ Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show() }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.add(callback) }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.remove(callback) }
}
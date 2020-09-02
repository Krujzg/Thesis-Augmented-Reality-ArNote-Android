package com.thesis.project.ui.settings

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
import com.thesis.project.ui.login.LoginActivityViewModel
import kotlinx.coroutines.launch

class SettingsActivityViewModel(application: Application) : AndroidViewModel(application), Observable
{
    var userDao : UserDao? = null

    var currentUser = LoginActivityViewModel.currentUser

    @Bindable
    var displayUserName = MutableLiveData<String>()

    @Bindable
    var displayEmail = MutableLiveData<String>()

    @Bindable
    var displayPassword = MutableLiveData<String>()

    @Bindable
    var displayPasswordAgain = MutableLiveData<String>()

    init {
        userDao = UserLocalDataBase
            .getDatabase(application)
            .userDao()
        displayUserName.value = currentUser!!.username
        displayEmail.value = currentUser!!.email
    }

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    private fun showToast(message: String){ Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show() }

    private fun checkIfTheEditTextsValuesAreNullOrEmpty() : Boolean
    {
        return !(displayUserName.value.isNullOrEmpty() ||
                displayEmail.value.isNullOrEmpty() ||
                displayPassword.value.isNullOrEmpty() ||
                displayPasswordAgain.value.isNullOrEmpty())
    }

    private fun checkIfThePasswordsAreEquals() : Boolean =  displayPassword.value == displayPasswordAgain.value


    fun updateUserInLocalDb()
    {
        if (checkIfTheEditTextsValuesAreNullOrEmpty())
        {
            if (checkIfThePasswordsAreEquals())
            {
                currentUser.apply {
                    this!!.username = displayUserName.value!!
                    this.email = displayEmail.value!!
                    this.password = displayPassword.value!!
                }
                viewModelScope.launch { userDao!!.updateUser(currentUser!!) }
                showToast("Successfully updated your account!")
            }
            else { showToast("The passwords are not equals") }
        }
        else{ showToast("Some of the fields are not filled")}
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.add(callback) }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) { callbacks.remove(callback) }
}
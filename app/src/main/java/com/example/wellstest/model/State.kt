package com.example.wellstest.model

sealed class State {
    object Success : State()
    class Loading(val showProgress: Boolean) : State()
    class Error(val message: String) : State()
}
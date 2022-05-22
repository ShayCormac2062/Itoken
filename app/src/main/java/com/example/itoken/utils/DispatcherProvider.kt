package com.example.itoken.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider {

    val Main: CoroutineDispatcher = Dispatchers.Main
    val IO: CoroutineDispatcher = Dispatchers.IO
    val Default: CoroutineDispatcher = Dispatchers.Default

}

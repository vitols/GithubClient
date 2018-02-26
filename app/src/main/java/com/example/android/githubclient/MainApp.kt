package com.example.android.githubclient

import android.support.multidex.MultiDexApplication
import com.example.android.githubclient.base.api.RestApi
import com.example.android.githubclient.base.controllers.LoginController
import com.example.android.githubclient.base.utils.Prefs


/**
 * Created by admin on 26.02.2018.
 */
class MainApp : MultiDexApplication()
{

    companion object {
        lateinit var instance: MainApp
    }

    override fun onCreate(){
        super.onCreate()


    }
}
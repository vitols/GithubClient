package com.example.android.githubclient

import android.app.Application


/**
 * Created by admin on 26.02.2018.
 */
class MainApp : Application()
{

    companion object {
        lateinit var instance: MainApp
    }
}
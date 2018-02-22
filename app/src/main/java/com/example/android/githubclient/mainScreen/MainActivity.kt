package com.example.android.githubclient.mainScreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.android.githubclient.base.navigator.ScreenInterface
import com.example.android.githubclient.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityParent {

    var navigator: MainActivityNavigator = MainActivityNavigator(supportFragmentManager, R.id.main_activity_container)

    private fun setListeners() {
        main_sidebar_me.setOnClickListener{
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_PROFILE) {
                Log.e("MainActivity", "Profile Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_PROFILE)
            }
        }

        main_sidebar_users.setOnClickListener {
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_USERS) {
                Log.e("MainActivity", "Users Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_USERS)
            }
        }

        main_sidebar_repos.setOnClickListener {
            if (navigator.getCurrentScreen() != MainActivityNavigator.Screens.SCREEN_REPOS) {
                Log.e("MainActivity", "Repos Clicked")
                navigator.showScreen(MainActivityNavigator.Screens.SCREEN_REPOS)

                // main_sidebar_me.background =
                // main_sidebar_users.background =
                // main_sidebar_repos.background =
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setListeners()
        navigator.openFirstFragment()
    }

    override fun action(screen: ScreenInterface?, data: Any?) {

    }

    override fun onBackPressed() {
        if(!navigator.backNavigation())
            super.onBackPressed()
    }
}

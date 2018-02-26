package com.example.android.githubclient.base.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by admin on 23.02.2018.
 */
class Prefs private constructor() {

    companion object {

        private val gson = GsonBuilder().create()

        private var prefs: SharedPreferences? = null

        private var editor: SharedPreferences.Editor? = null

        fun init(context: Context) {
            Log.e("Prefs", "Object")
            prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
            editor = prefs!!.edit()
        }

        fun <T> save(key: String, value: T) {
            editor!!.putString(key, gson.toJson(value))
                    .apply()
        }

        fun <T> load(key: String) : T {
            Log.e("Prefs", "load")
            return gson.fromJson<T>(prefs!!.getString(key, null),
                                    object : TypeToken<T>() {}.type)
        }

        fun contains(key: String): Boolean {
            Log.e("Prefs", "Contains")
            return prefs!!.contains(key)
        }

        fun clear() {
            editor!!.clear().apply()
        }

        fun remove(vararg keys: String) {
            for (key in keys)
                editor!!.remove(key)
            editor!!.apply()
        }
    }

}
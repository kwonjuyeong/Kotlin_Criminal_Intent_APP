package com.example.study_kotlin2

import android.app.Application
import android.util.Log
import com.example.study_kotlin2.database.CrimeRepository

class CriminalIntentApplication :Application() {
    override fun onCreate(){
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}
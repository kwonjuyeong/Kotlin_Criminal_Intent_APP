package com.example.study_kotlin2

import androidx.lifecycle.ViewModel
import com.example.study_kotlin2.database.CrimeRepository

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()
}
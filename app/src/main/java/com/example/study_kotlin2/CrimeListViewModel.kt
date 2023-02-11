package com.example.study_kotlin2

import androidx.lifecycle.ViewModel
import com.example.study_kotlin2.database.CrimeRepository

class CrimeListViewModel : ViewModel() {
   /*
    val crimes = mutableListOf<Crime>()
    init {
        for(i in 0 until 100){
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i%2 == 0
            crimes += crime
        }
    }*/

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()
}
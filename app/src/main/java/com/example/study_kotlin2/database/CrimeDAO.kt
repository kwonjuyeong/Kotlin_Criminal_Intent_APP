package com.example.study_kotlin2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.study_kotlin2.Crime
import java.util.*

@Dao
interface CrimeDAO{
    //crime 데이터베이스 테이블에 저장된 모든 행의 모든 열을 가져옴.
    @Query("SELECT * FROM crime")
    //fun getCrimes():List<Crime>
    fun getCrimes():LiveData<List<Crime>>

    //id 값이 일치하는 행의 모든 열만 가져옴
    @Query("SELECT * FROM crime WHERE id=(:id)")
    //fun getCrime(id: UUID) : Crime
    fun getCrime(id:UUID) : LiveData<Crime?>
}
package com.example.study_kotlin2.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.study_kotlin2.Crime
import java.util.UUID
import java.util.concurrent.Executors

//CrimeRepository는 싱글톤 패턴이다.
//싱글톤은 앱이 메모리에 있는 한 계속 존재하므로, 싱글톤이 갖는 속성은 액티비티나 프로그먼트의 생명주기 상태가 변경되어도 계속 유지할 수 있다.
//하지만 안드로이드 운영체제가 메모리에서 앱을 제거하면 싱글톤도 같이 소멸하기 떄문에 장기간 저장하기 위한 해결책이 되지는 않는다.

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context){

    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCrimes() : LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id:UUID) : LiveData<Crime?> = crimeDao.getCrime(id)

    fun updateCrime(crime: Crime){
        executor.execute{
            crimeDao.updateCrime(crime)
        }
    }

    fun addCrime(crime: Crime){
        executor.execute{
            crimeDao.addCrime(crime)
        }
    }

    companion object{
        private var INSTANCE : CrimeRepository ?= null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }
        fun get() : CrimeRepository{
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }



}
package com.example.study_kotlin2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.study_kotlin2.Crime
//데이터 베이스 클래스 생성하기

@Database(entities = [Crime::class], version = 2)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase(){

    abstract fun crimeDao() : CrimeDao
}

//Migration 객체를 생성해 데이터베이스를 업데이트 한다. -> 여기서 받는 인자 1인 업데이트 전의 데이터베이스 버전이고, 두 번쨰는 업데이트 할 버전을 뜻한다.
val migration_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}
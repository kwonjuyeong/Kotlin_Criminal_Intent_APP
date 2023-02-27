package com.example.study_kotlin2
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Room Entity 적용
@Entity
data class Crime(
    @PrimaryKey val id : UUID = UUID.randomUUID(),
    var title : String = "",
    var date : Date = Date(),
    var isSolved : Boolean = false,
    var suspect : String = ""
)

//UUID는 안드로이드 프레임 워크에 포함된 유틸리티 클래스이다.
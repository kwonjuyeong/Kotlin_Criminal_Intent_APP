package com.example.study_kotlin2
import java.util.*

data class Crime(
    val id : UUID = UUID.randomUUID(),
    var title : String = "",
    var date : Date = Date(),
    var isSolved : Boolean = false
)

//UUID는 안드로이드 프레임 워크에 포함된 유틸리티 클래스이다.
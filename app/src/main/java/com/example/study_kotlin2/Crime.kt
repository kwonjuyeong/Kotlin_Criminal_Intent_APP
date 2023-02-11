package com.example.study_kotlin2
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//Room Entity 적용
@Entity
data class Crime(
    //@PrimaryKey는 기본키 열을 지정한다. 기본키는 테이블의 모든 행에 고유한 데이터를 갖는 열이므로 각 행을 검색하는데 사용할 수 있다.
    //여기서는 id 속성값이 모든 Crime 객체에 고유하기 때문에 이 속성을 사용해 Crime 객체 데이터를 쿼리할 수 있다.
    @PrimaryKey val id : UUID = UUID.randomUUID(),
    var title : String = "",
    var date : Date = Date(),
    var isSolved : Boolean = false
)

//UUID는 안드로이드 프레임 워크에 포함된 유틸리티 클래스이다.
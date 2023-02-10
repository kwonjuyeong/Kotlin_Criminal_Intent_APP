package com.example.study_kotlin2.ChapterMemo;

/*
Chapter 10
레이아웃과 위젯으로 사용자 인터페이스 생성하기

중첩된 Layout
- 레이아웃 내부에 다른 레이아웃을 중첩할 경우, 레이아웃을 파악하고 변경하는데 어려우며, 앱의 성능도 저하된다.
중첩된 레이아웃을 안드로이드 운영체제가 처리하고 보여주는 데 시간이 오래 걸리기 때문이다.(LinearLayout 내부에 LinearLayout)
- 중첩되지 않은 Layout은 안드로이드 운영체제가 빠르게 처리해서 보여준다.(ConstraintLayout)

ConstraintLayout 개요
- Constraint(제약)Layout은 제약들을 레이아웃에 추가한다.
- 제약은 두 개의 물건을 연결해 끌어당기는 고무 밴드라고 생각하면 된다.
- 상하좌우 네 방향에서 제약을 생성할 수 있고, 위젯의 크기는 직접 지정, wrap_content, 지정된 제약에 맞춰 위젯이 확장되게 하는 방법이다.
- ConstraintLayout은 레이아웃에 포함된 뷰들이 충분한 제약을 갖고 있지 않으면 해당 뷰들의 위치를 알 수 없기 때문에 에러가 발생한다.

뷰 크기 설정 유형
- Xdp : 변경되지 않는 크기로 뷰를 지정한다(X는 크기 값). 크기 지정 단위는 dp다.
- wrap_content : 뷰에서 원하는 크기가 자동 지정된다.
- 0dp : 지정된 제약에 맞춰 뷰의 크기가 신축성 있게 조정된다.






 */

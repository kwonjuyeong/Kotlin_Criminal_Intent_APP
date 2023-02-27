package com.example.study_kotlin2.ChapterMemo;

/*
Chapter 9
RecyclerView로 리스트 보여주기

예제 : Criminal Intent
구조 :
모델 = Crime(title, id, date, solved), List
컨트롤러 = CrimeListFragment, CrimeListViewModel, MainActivity
뷰 = RecyclerView, FrameLayout

RecyclerViewHolder - Adapter

Adapter 의 역할
- 필요한 ViewHolder 인스턴스들을 생성한다.
- 모델 계층의 데이터를 ViewHolder들과 바인딩한다.

RecyclerView의 역할
- 새로운 ViewHolder 인스턴스의 생성을 어뎁터에게 요청한다
- 지정된 위치의 데이터 항목에 ViewHolder를 바인딩하도록 어뎁터에게 요청한다.
- onBindViewHolder 함수 효율적으로 만들지 않으면 스크롤할 때마다 딱딱하게 스크롤된다.

뷰의 재활용 : RecyclerView
- 현재 앱에서 한 화면에 보여지는 List는 12개이다.
- 그리고 화면을 스크롤하면 100개의 데이터가 보여지게 된다, 하지만 메모리에 100개의 View 모두 가지고 있지 않다.
- RecyclerView는 한 화면을 채우는데 충분한 개수를 생성하고, 화면이 스크롤되면서 항목 View가 화면을 벗어날 때 RecyclerView는 해당 항목 View를 버리지 않고
재사용한다.
- 이런 이유로 onCreateViewHolder(ViewGroup, Int) 함수는 onBindViewHolder(ViewHolder, Int) 보다 호출이 덜 된다.
- 일단 충분한 ViewHolder가 생성되면 RecyclerView는 onCreateViewHolder의 호출을 중단하고 기존의 ViewHolder를 재활용해 onBindViewHolder에 전달함으로써 시간과 메모리를 절약한다.
 */

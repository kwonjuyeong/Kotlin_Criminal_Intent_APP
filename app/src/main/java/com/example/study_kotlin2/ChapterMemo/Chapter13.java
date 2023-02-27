package com.example.study_kotlin2.ChapterMemo;

/*
Chapter 13
Dialog(Date Picker 사용)
대화상자(Dialog)는 사용자의 주의를 끌고 입력을 받는 데 사용되며 사용자의 선택을 받거나 중요한 정보를 보여줄 때도 유용하다.

Model                    Crime(title, id, date, isSolved)
Controller                                      MainActivity
                CrimeDetailViewModel         CrimeFragment                   DatePickerFragment
View                                   EditText    CheckBox    Button         DatePickerDialog

DatePicker
- 사용자가 날짜를 선택할 수 있게 해주며 사용자 선택을 알아내기 위해 구현하는 리스너 인터페이스를 제공한다.
- DatePickerFragment 클래스를 생성한다.
- DatePickerDialog 인스턴스를 생성하여 대화상자를 만든다
- FragmentManager를 통해 대화상자를 화면에 보여준다.

두 프래그먼트 간의 데이터를 전달하는 방법
- 같은 액티비티에 의해 호스팅되는 두 프래그먼트(CrimeFragment - DatePickerFragment)간의 데이터 저달
- CrimeFragment에서는 DatePickerFragment에 현재 범죄 발생일자를 전달하고 DatePickerFragment에서는 사용자가 선택한 날짜를 전달한다.
- DatePickerFragment에 범죄 발생일자를 전달하기 위해서 newInstance(Date) 함수를 작성하고 이 함수의 인자로 전달된 발생 일자를 DatePickerFragment의 프래그먼트 인자로 전달한다.

CrimeFragment -> DatePickerFragment 로 데이터 전달
- DatePickerFragment에서 newInstace를 생성해 데이터를 보낸다.(date 전달)
- CrimeFragment에서 인자를 받는다.(DatePickerFragment.newInstance(crime.date).apply)
- DatePickerFragment 를 초기화하려면 정수 값이 필요한데, Date 객체는 타임스태프 형식이므로 Date 객체를 사용해서 Calendar 객체를 생성한다.
   val date = arguments?.getSerializable(ARG_DATE) as Date
   calendar.time = date 를 통해 데이터를 받아 초기화해준다.

CrimeFragment로 데이터 반환하기
- CrimeFragment가 DatePickerFragment로부터 선택된 날짜를 돌려받으려면 두 프래그먼트 간의 관계를 유지하고 관리해야한다.
- CrimeFragment를 DatePickerFragment의 대상 프래그먼트(target Fragment)로 만들면 액티비티와 유사한 연결을 만들 수 있다.
- CrimeFragment 인스턴스와 DatePickerFragment 인스턴스 모두가 안드로이드 운영체제에 의해 소멸되었다가 다시 생성되어도 두 프래그먼트 간의 연결은 자동으로 복구된다.
-> setTargetFragment(fragment : Fragment, requestCode : Int) 사용



 */
package com.example.study_kotlin2.ChapterMemo;

/*
Chapter 17
지역화
지역화란?
사용자의 언어 설정을 기반으로 앱의 적합한 리소스를 제공하는 절차다.
장치의 언어가 스페인어로 설정되면 런타임 시에 안드로이드가 스페인어 문자열을 자동으로 찾아 사용한다.

리소스 지역화하기
- res/values -> 새로운 Resource 파일 만들어주기(strings) -> 사용 가능한 수식자(Available qualifiers)에서 Locale 선택 -> 수식자 언어 목록에서 원하는 언어 선택
- 이 때 strings 파일은 각각 대조되어야 한다.

기본 리소스
- 영어의 언어 구성 수식자는 -en이다.(한국어는 -kr)
- 따라서 기존의 values 디렉터리 이름을 values-en으로 변경해도 된다고 생각할 수 있다. 하지만 그렇게 변경하면 이 앱은 values-en의 strings.xml과 values-es의 strings.xml을 가진다.
- 이렇게 하면 앱을 빌드하고 스페인어나 영어로 언어가 설정된 장치가 아닌 앱에서 문제가 생길 수 있다.
- Resource.NotFoundException이 발생하면서 문자열 리소스를 찾을 수 없게 된다.
= 따라서 앱에 각 리소스의 기본 리소스를 제공한다.

번역 편집기로 문자열 리소스 확인하기
- 앱에서 지원할 언어가 많아질수록 각 언어의 문자열 리소스를 따로 제공하는 것도 어려워진다.
- 모든 번역 문자열을 한곳에서 볼 수 있게 안드로이드 스튜디오에서 간편한 번역 편집기(Translation editor)를 제공한다.
- strings.xml -> 오른쪽 클릭 -> Open Translation Editor에서 한 눈에 문자열을 볼 수 있다.

로케일과 리소스 선택
- 리소스 디렉터리는 언어


 */
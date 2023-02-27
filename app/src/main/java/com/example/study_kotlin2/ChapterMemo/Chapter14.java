package com.example.study_kotlin2.ChapterMemo;

/*
Chapter 13
앱 바(App bar)
앱 바는 사용자가 수행할 수 있는 액션과 화면 간을 이동할 수 있는 메커니즘을 제공한다.
더불어 디자인의 일관성도 제공한다.

앱 바
- 액션바(Action bar) 혹은 툴 바(Tool bar) 라고도 한다.
- 새로운 프로젝트를 생성할 때 안드로이드 스튜디오가 AppCompat의 서브 클래스인 모든 액티비티에 앱 바를 기본으로 포함하도록 설정하고 있다.
- AppCompat은 application compatibility의 단축어이다.
- res/values/theme(style).xml에서 기본 스타일을 지정할 수 있다.

메뉴
- 앱 바의 오른쪽 위에 메뉴(menu)를 넣을 수 있다.
- 메뉴는 액션 항목으로 구성되며, 액션 항목은 현재 화면과 관련된 액션 또는 앱 전체의 액션을 수행할 수 있다.
- 액션 항목의 이름은 문자열 리소스로 만들어야 한다.
- res/values/strings.xml에 menu의 문자열을 추가해준다.

XML로 메뉴 정의하기
- 메뉴는 레이아웃과 유사한 리소스로, XML 파일로 생성해 프로젝트의 res/menu 디렉터리에 둔다. 그리고 코드에서 메뉴를 인플레이트해 사용하도록 앱을 빌드하면 메뉴 파일의 리소스 ID가 자동 생성된다.
- menu item의 showAsAction 속성은 액션 항목이 앱 바 자체에 보이게 할 것인지, 아니면 오버플로 메뉴에 포함되어 보이게 될 것인지를 나타낸다.
- 여기서는 ifRoom과 withText 두 값을 같이 지정했으므로 앱 바에 공간이 있으면 액션 항목의 아이콘과 텍스트 모두 앱 바에 나타난다.
(showAction 속성의 다른 값으로 "always"와 "never"가 있는데 always는 액션 항목을 항상 앱 바에 보여주기 때문에 권장하지 않고 ifRoom을 사용해 안드로이드 운영체제가 결정하게 한다.
never는 자주 사용하지 않는 액션에 사용한다. 따라서 자주 사용할 액션 항목들만 앱 바에 두는 것이 좋다)

메뉴 생성하기
- 메뉴는 Activity 클래스의 콜백 함수가 관리한다. 메뉴가 필요하면 안드로이드는 Activity 함수인 onCreateOptionsMenu(Menu)를 호출한다.
 override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }
- Fragment를 호스팅하는 액티비티가 운영체제로부터 자신의 onCreateOptionMenu 콜백 함수 호출을 받았을 때, FragmentManager는 Fragment.onCreateOptionMenu를 호출하는 책임을 가진다.
- 따라서 CrimeListFragment.onCreate(Bundle?)에 CrimeListFragment가 메뉴 콜백 함수 호출을 받아야함을 알려주는 코드를 추가한다
(setHasOptionMenu(true))

메뉴 선택에 응답하기
- 사용자가 액션 항목을 눌렀을 때 그에 대한 응답을 하려면 데이터베이스에 새로운 범죄 데이터를 추가할 방법이 필요하다.
- 그러기 위해서는 리포지터리 addCrime(Crime) 함수를 호출하는 코드를 CrimeListViewModel에 추가한다.
- 응답하기 위해 CrimeListFragment에 onOptionSelected 콜백 함수를 추가해 옵션이 선택되었을 때 액션을 취할 수 있도록 한다.
- onOptionSelected 함수는 Boolean 값을 반환한다. -> 즉 선택된 item을 정상적으로 처리하고 나면 true, 처리를 구현하지 않은 액션 항목 ID는
onOptionItemSelected(MenuItem) 함수를 호출한다.

안드로이드 에셋 스튜디오 사용하기
- 시스템 아이콘은 프로젝트의 리소스가 아닌 장치에 있는 아이콘이다.
- 시스템 아이콘을 참조해도 무난하지만 장치나 안드로이드 버전에 따라 매우 다를 수 있어 의도한 디자인과 맞지 않는 아이콘이 나타날 수 있다.

시스템 아이콘을 참조하지 않고 이미지 에셋을 사용하는 방법
1. 자체 아이콘을 생성하는 방법을 사용한다. (각 화면 밀도별로 만든다)
2. 앱의 요구에 맞는 시스템 아이콘을 찾아서 프로젝트의 Drawable 리소스로 복사한다.
- 시스템 아이콘은 안드로이드 SDK 디렉터리에 있다.
3. 안드로이드 스튜디오에 포함된 안드로이드 에셋 스튜디오를 사용한다.
- 프로젝트 도구창 -> res/drawable -> 오른쪽 마우스 클릭 -> New -> Image Asset에서 이미지를 추가한다.
- 이미지 선택 후 해상도에 따라 PNG 파일을 생성해준다.



+ 앱 바, 액션 바, 툴 바의 차이
- 안드로이드 문서에서 이 세 개의 용어를 혼용해서 사용한다. 하지만 셋은 정확하게 같은 것은 아니며 UI 설계 요소로는 "앱 바"라고 한다.
- 안드로이드 5.0 이전에는 앱 바가 ActionBar 클래스를 사용해서 구현되었다.
- 안드로이드 5.0 이후부터는 앱 바를 구현하는 방법으로 Toolbar 클래스가 도입되었다.

- ActionBar와 Toolbar는 매우 유사하지만 툴바는 변경된 UI를 가지며 액션 바보다 유연성 있게 사용할 수 있는 반면에 액션 바는 항상 화면의 제일
위쪽에 나타나며 한 화면에 하나만 있는 등 많은 제약을 가졌다.
- 액션 바의 크기는 정해져 있어서 변경할 수 없지만, 툴바는 이런 제약을 갖지 않는다.
- 화면의 어떤 위치에도 툴바를 둘 수 있고 여러 개를 넣을 수도 있다. 이런 유연성 덕분에 흥미로운 화면 디자인이 가능하다.
- 또한 툴바는 내부에 다른 뷰들을 둘 수 있고 높이도 조정할 수 있어서 앱의 작동 방식에 훨씬 더 좋은 유연성을 제공한다.
 */
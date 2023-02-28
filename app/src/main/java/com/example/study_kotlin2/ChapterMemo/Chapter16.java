package com.example.study_kotlin2.ChapterMemo;

/*
Chapter 16
인텐트를 사용한 사진 찍기

파일 스토리지
사진 파일은 화면이 아닌 다른 곳에 저장해야한다.
그러나 실제 크기의 사진은 용량이 너무 커서 SQLite 데이터베이스에 넣기 어렵다. 따라서 장치의 파일 시스템에 저장해야 한다.

Context 클래스에 있는 기본적인 파일과 디렉터리 함수
1. getFilesDir() : File
- 앱 전용 파일들의 디렉터리 핸들을 반환한다.
2. openFileInput(name : String) : FileInputStream
- 데이터를 읽기 위해 파일 디렉터리의 기존 파일을 연다
3. openFileOutput(name : String, mode: Int) : FileOutputStream
- 데이터를 쓰기 위해 파일 디렉터리의 파일을 연다.(생성도 한다.)
4. getDir(name : String, mode : Int) : File
- 파일 디렉터리 내부의 서브 디렉터리를 알아낸다.
5. fileList(...) : Array(String)
- 파일 디렉터리의 파일 이름들을 알아낸다. 예를 들면 openFileInput(String)과 함께 사용한다.
6. getCacheDir() : File
- 캐시 파일 저장에 사용할 수 있는 디렉터리의 핸들을 반환한다. 단, 이 디텍터리는 가능한한 작은 용량을 사용하도록 주의해야 한다.

문제점 : 개인 스토리지 영역의 파일들은 이 앱에서만 읽거나 쓸 수 있다. 다른 앱이 파일을 써야 한다면(외부 카메라 앱 사용 등) ContentProvider를 이용해야 한다.
- Context.MODE_WORLD_READALBE 플래그를 openFileOutput(String, Int) 함수에 전달하는 방법은 사용이 금지되어서 새로운 안드로이드 버전의 장치에서는 돌아가지 않을 수 있다.
- Content Provider로 파일을 콘텐츠 URI로 다른 앱에 노출하면 다른 앱에서는 해당 URI로부터 파일을 다운로드하거나 쓸 수 있다. 제어할 수도 있고, 읽기-쓰기를 거부할 수 있다.

FileProvider 사용하기
- 다른 앱으로부터 파일을 받는 것이 전부라면 ContentProvider 전체를 구현할 필요는 없다.
1. Manifest : Fileprovider 콘텐츠 제공자 선언 축
2. xml -> resource -> file.xml 생성 후 경로 추가
3. Manifest : meta-data 태그에 file.xml 연결

사진 위치 지정하기
1. 데이터 클래스에 사진 위치 지정
2. 사진 파일 위치 찾기 context.applicationContext.fileDir
3. ViewModel을 통해 사진 파일 정보 제공

카메라 인텐트 사용하기
1. 사진 파일의 위치를 속성에 저장한다.
2. uri를 엑스트라의 값으로 설정해 인텐트에 전달한다.(uri는 FileProvider에 의해 서비스되는 위치를 가리킨다.
3. 인텐트 실행을 요청한다.

섬네일 이미지 효율적으로 로드하기
- ViewTreeObserver
 */
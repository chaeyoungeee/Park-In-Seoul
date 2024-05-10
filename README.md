# 🌳 Park-In-Seoul (Android application)

## ❗️Description
**"파크 인 서울"** 은 서울시 실시간 도시데이터 API에서 제공하는 서울시 주요 113곳의 장소 중 **공원으로 분류된 장소 29곳**의 상세 데이터를 제공해주는 안드로이드 앱이다. 이 앱은 `공원의 인구 혼잡도`, `날씨 및 미세먼지`, `행사` 등의 정보를 제공하며 이를 통해 사용자의 공원 이용 편리성 증가를 목표로 한다. 

## ❗Main Functions & Running
### 메인 액티비티
**[1] 공원 데이터 출력 및 정렬 기능**
> 1. 29곳의 공원에 대해 서울시 실시간 도시 데이터 API를 호출<br>
> → 공원의 혼잡도, 최저/최고 기온, 날씨 데이터를 불러와 파싱해 데이터 제공
> 2. 스피너를 통해 업데이트 순/혼잡도 낮은 순/혼잡도 높은 순으로 데이터 제공
<p align="center">
<img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/3438cfe4-5a79-4927-b58b-58e79cdffdfc">
<img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/265e85f5-240f-40e4-913f-94dc8d3cfa57">
</p>

**[2] 공원 북마크 기능**
> 1. Room 라이브러리 사용해 스마트폰의 내장 DB에 북마크한 공원을 저장
> 2. 북마크한 공원은 상단에 있는 툴바의 북마크 아이콘을 클릭하여 확인 가능
<p align="center">
<img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/2fe2eb4f-cd95-4d3c-8041-fb0bf069cad5">
</p>

**[3] 공원 검색 기능**
> 1. 공원 이름으로 검색
> 2. 검색 범위를 전체 공원 혹은 북마크한 공원 중 선택 가능
<p align="center">
<img width="221" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/ae0141ab-c9de-4603-ae09-6a2057502aed">
<img width="221" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/41165a98-98fc-465b-8b48-b94778418775">
</p>

**[4] 공원 북마크 기능**
> 1. Room 라이브러리 사용해 스마트폰의 내장 DB에 북마크한 공원을 저장
> 2. 북마크한 공원은 상단에 있는 툴바의 북마크 아이콘을 클릭하여 확인 가능
<p align="center">
<img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/d4394cbb-1c3f-4e7a-8eab-b3a697a5773d">
</p>

### 디테일 액티비티
**[1] 상단(고정)**
> 1. 공원 사진 데이터 제공
> 2. 현재 혼잡도 및 일출/일몰 시간 데이터 제공

**[2] 탭 레이아웃(혼잡도)**
> 1. MPAndroidChart 라이브러리를 이용해 인구 전망 그래프 제공
> 2. 실시간 인구 정보 메시지 제공
<p align="center">
  
**[3] 탭 레이아웃(날씨)**
> 1. 기온/체감온도 데이터 제공
> 2. 최저/최고기온 데이터 제공
> 3. 강수 정보 메시지 제공
> 4. 현재 시각 이후 24시간 동안 날씨/기온/강수량/강수확률 데이터 제공

**[4] 탭 레이아웃(미세먼지)**
> 1. 대기질 정보 메시지 제공
> 2. 통합대기환경지수 데이터 제공
> 3. 미세먼지 데이터 제공
> 4. 초미세먼지 데이터 제공

**[5] 탭 레이아웃(행사)**
> 1. 행사 기간, 장소 데이터 제공

<p align="center">
<img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/15406a99-d0eb-4652-acd4-a3cb926742e7">
<img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/e0c3c8dc-26a0-490f-9e78-e2462d0595e8">
  <img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/1c692012-4366-4459-92dc-828569abb3d0">
<img width="222" alt="image" src="https://github.com/chaeyoungeee/Park-In-Seoul/assets/102286483/8c69820e-75b3-4e0d-adc0-9782d9e74299">
</p>

## ❗️Reference
- [레트로핏 API 호출](https://hello-bryan.tistory.com/507)<br>
- [XML 파싱](https://velog.io/@changhee09/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-XmlPullParser%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-XML-%ED%8C%8C%EC%8B%B1)<br>
- [리사이클러뷰1](https://uknowblog.tistory.com/29)<br>
- [리사이클러뷰2](https://notepad96.tistory.com/131)<br>
- [뷰바인딩](https://duckssi.tistory.com/42)<br>
- [스피너](https://magicalcode.tistory.com/entry/%EC%BD%94%ED%8B%80%EB%A6%B0-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%8A%A4%ED%94%BC%EB%84%88Spinner-%ED%95%9C%EB%B0%A9%EC%97%90-%EB%81%9D%EB%82%B4%EA%B8%B0)<br>
- [스크롤뷰](https://velog.io/@kimbsu00/Android-7)<br>
- [뷰페이저 및 탭 레이아웃](https://minchanyoun.tistory.com/126)




# daily-scheduler-api
데일리 스케쥴러 서버

<br />

1. 아키텍처
2. ERD
3. 시퀀스
4. 성능 테스트

<br />


### 1. 아키텍처
<img src="https://github.com/user-attachments/assets/88e85ba1-2489-42dc-806e-60fe743c702c" width="600px" />

<br />
<br />

### 2. ERD
<img src="https://github.com/user-attachments/assets/387c1380-7c57-40d1-8504-e377d42a9a88" width="600px" />

<br />
<br />

### 3. 시퀀스
<img src="https://github.com/user-attachments/assets/d8c0b00b-1239-44dd-a479-0fabd1791b6e" width="600px" />

<br />
<br />

### 4. 성능 테스트

Case1. 1초마다 동일한 유저가 100개의 요청을 보내는 경우

Case2. 1초마다 랜덤한 유저가 1000개의 요청을 보내는 경우


<br />

Case1)

<figure style="margin-left: 50%; transform: translateX(-50%); width: 1200px; max-width: 1200px;">
  <div style="display: flex; justify-content: space-between; flex-wrap: wrap;">
    <img src="https://github.com/user-attachments/assets/d360e4d5-04aa-4827-88cc-964ba8370e8d" width="40%" />
    <img src="https://github.com/user-attachments/assets/c72755f3-bc5e-401f-9197-fdf8293a48c6" width="40%" />
  </div>
</figure>

<br />

-> 캐싱 적용으로 RPS 약 29 증가, 평균 응답 속도 640ms에서 74ms로 단축

<br />

Case2)

<figure style="margin-left: 50%; transform: translateX(-50%); width: 1200px; max-width: 1200px;">
  <div style="display: flex; justify-content: space-between; flex-wrap: wrap;">
    <img src="https://github.com/user-attachments/assets/740db00c-ca3a-422a-922b-adb7ca0bd49f" width="40%" />
    <img src="https://github.com/user-attachments/assets/6f9f4499-121d-4347-b63a-b35992dc66ea" width="40%" />
  </div>
</figure>

<br />

→ 캐싱 적용으로 RPS 약 11 증가, 평균 응답 속도 1500ms에서 600ms로 단축

<br />
<br />


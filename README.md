# AutoBttWithdrawal
AutoBttWithdrawal은 일정 시간마다 재단 지갑을 확인하여 BTT를 자동으로 인출하는 프로그램입니다.

![image](https://user-images.githubusercontent.com/29080625/145715965-08404cd3-fe32-431c-8d9f-7d08995114b8.png)

## 다운로드
JDK 버젼과 Chrome 브라우저 버젼에 따라 올바른 버젼을 다운로드 해주세요.
JDK는 최신버젼이 설치되어 있다면 재설치 할 필요가 없습니다. Chrome 버젼은 정확하게 일치해야 합니다.

+ Chrome 버젼 확인
  + chrome://settings/help 접속합니다.
  + 현재 버젼정보를 확인합니다.
  + ![image](https://user-images.githubusercontent.com/29080625/145716374-440f64d8-71c3-4b23-8968-92fd927e688b.png)
---
* 다운로드 : https://github.com/Boris-Nemtsov/AutoBttWithdrawal/releases


## 사용법
+ BTFS 기능을 활성화하여 http://127.0.0.1:5001/hostui/ 페이지가 정상적으로 열리는지 확인하세요.
+ 프로그램을 다운로드하고, 압축을 풀어 같은 폴더에 모두 넣어주세요.
+ settings.txt 을 열고 비밀번호와 설정 값을 입력해주세요.
+ start.bat 를 실행해주세요.


## 설정 옵션
Parameter  | Default | Explain
------------- | ------------- | ------------- 
wallet_password | "" | BTFS Wallet password.
withdrawal_waiting_strength | 1 | Waiting for system delay when attempting to withdraw. <br/> Increase the Integer if your system is slow to reduce errors.
browser | "Chrome" | Type of browser to run  eg) Chrome, IE, Firefox.
withdrawal_unit | 1000 | Amount to withdraw each time.
foundation_min_balance | 10000 | Minimum balance of Foundation Wallet to start to attempt withdrawal.
scheduler_waiting_second | 60 | Waiting time after withdrawal.


## 사용 라이브러리
+ Chrome driver
+ Selenium server


## 오류
+ 실행하자마자 바로 꺼지는 경우
  + PC에 설치된 JDK 버젼이 설치한 프로그램보다 상위버젼인지 확인해주세요.
  + 구동파일이 깨졌을 수 잇으니, 다시 다운로드 해주세요.

+ 인출을 시작하면 프로그램이 꺼지는 경우
  + Chrome 버젼이 설치한 프로그램과 정확하게 일치하는지 확인해주세요.

+ 인출 후 '브라우저 페이지에 지정한 요소가 없습니다.' 메시지가 계속 나타날 경우
  + settings.txt 에 입력한 비밀번호가 정확한지 확인해주세요.
  + settings.txt 에서 withdrawal_waiting_strength 항목의 숫자를 늘려주세요.

+ 인출 결과가 transfer failed 로 계속 나타날 경우
  + BTT 인출은 경쟁으로 이뤄집니다. 경쟁에 밀릴 경우 언제든지 transfer failed 가 나타날 수 있습니다.

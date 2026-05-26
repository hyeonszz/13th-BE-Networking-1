# 13th-BE-Networking-2

---

## 1. 아키텍처 다이어그램

### A. 사용자 요청 및 네트워크 처리 흐름

```text
사용자
  ↓ HTTPS 요청
nip.io 도메인
  ↓ DNS Public IP 매핑
AWS EC2 보안 그룹
  ↓ HTTPS(443) 허용
Nginx 리버스 프록시
  ↓ proxy_pass (8080)
Spring Boot 컨테이너
  ↓ JDBC Connection
MySQL 컨테이너
  ↓ Volume Mount
db_data 볼륨
```

### B. CI/CD 자동화 배포 흐름

```text
개발자
  ↓ git push (main)
GitHub 저장소
  ↓ GitHub Actions 실행
Docker 이미지 빌드 및 Push
  ↓
Docker Hub
  ↓ SSH 배포 명령 실행
AWS EC2 서버
```

---

## 2. 배포 URL

- **서비스 URL**  
  `https://54.180.104.80.nip.io`

- **Swagger URL**  
  `https://54.180.104.80.nip.io/swagger-ui/index.html`

---

## 3. Swagger 접속 화면

<img width="2719" height="1610" alt="Image" src="https://github.com/user-attachments/assets/d01abf8e-72b5-4623-ba9f-6a5427ebbd1b" />

---

## 4. GitHub Actions 실행 결과

<img width="2143" height="472" alt="Image" src="https://github.com/user-attachments/assets/59fd03d8-7559-47af-90c6-22509609cd8d" />

<img width="2800" height="900" alt="Image" src="https://github.com/user-attachments/assets/f7bc39cf-084c-41af-ab61-3b0f427536e5" />

---

## 5. Dockerfile / Nginx 설정

환경 변수 및 민감 정보는 별도로 관리했습니다.

### ① Dockerfile

- Multi-stage 빌드를 사용해 이미지 크기를 줄이고 실행 환경을 분리했습니다.

```dockerfile
# --- Build Stage ---
FROM gradle:8.8-jdk17 AS builder
WORKDIR /app

COPY --chown=gradle:gradle . .

RUN gradle bootJar --no-daemon -x test

# --- Run Stage ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Duser.timezone=Asia/Seoul", "-jar", "app.jar"]
```

### ② Nginx 설정 파일 (`/etc/nginx/conf.d/cotato.conf`)

- HTTPS 요청을 Spring Boot 서버로 전달하도록 Reverse Proxy를 구성했습니다.
- HTTP 요청은 HTTPS로 리다이렉트되도록 설정했습니다.

```nginx
server {
    server_name 54.180.104.80.nip.io;

    location / {
        proxy_pass http://127.0.0.1:8080;

        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    listen 443 ssl;
    listen [::]:443 ssl ipv6only=on;

    ssl_certificate /etc/letsencrypt/live/54.180.104.80.nip.io/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/54.180.104.80.nip.io/privkey.pem;

    include /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;
}

server {
    listen 80;
    listen [::]:80;

    server_name 54.180.104.80.nip.io;

    return 301 https://$host$request_uri;
}
```
---

## 6. 트러블슈팅 노트

### 트러블슈팅 1: `nip.io` SSL 인증서 발급 실패

- **문제**  
  `certbot` 실행 시 아래 오류가 발생하며 SSL 인증서 발급에 실패했습니다.

```text
too many certificates already issued for "nip.io"
```

- **원인**  
  `nip.io`는 공용 도메인이므로 Let's Encrypt 인증서 발급 제한에 도달한 상태였습니다.

- **해결**  
  일정 시간 이후 다시 시도하여 인증서를 정상 발급했습니다.

---

### 트러블슈팅 2: `Access denied` 에러로 인한 Spring Boot 컨테이너 재시작

- **문제**  
  Spring Boot 컨테이너 실행 시 아래 오류가 발생하며 컨테이너가 반복적으로 재시작되었습니다.

```text
java.sql.SQLException: Access denied for user
```

- **원인**  
  `.env` 파일의 MySQL 계정 정보와 Spring Boot DB 접속 정보가 서로 일치하지 않았습니다. 또한 기존 Docker Volume 데이터가 유지되어 이전 설정값이 계속 사용되고 있었습니다.

- **해결**  
  DB 계정 정보를 동일하게 수정한 뒤, `docker-compose down -v` 명령으로 기존 Volume을 삭제하고 컨테이너를 다시 실행하여 해결했습니다.

---

### 트러블슈팅 3: Docker Buildx 버전 문제로 인한 빌드 실패

- **문제**  
  `docker-compose up --build -d` 실행 시 아래 오류가 발생하며 빌드가 실패했습니다.

```text
compose build requires buildx 0.17.0 or later
```

- **원인**  
  EC2 환경의 Docker Buildx 버전이 `docker-compose`에서 요구하는 버전보다 낮았습니다.

- **해결**  
  Docker 공식 GitHub 저장소에서 최신 Buildx 바이너리를 설치한 뒤 문제를 해결했습니다.

---

### 트러블슈팅 4: Let's Encrypt 인증 타임아웃

- **문제**  
  `certbot` 실행 시 아래 오류가 발생하며 SSL 인증서 발급이 실패했습니다.

```text
Timeout during connect
```

- **원인**  
  AWS EC2 보안 그룹에서 `80` 포트가 외부에 열려 있지 않아 Let's Encrypt 서버가 인증 요청에 접근하지 못했습니다.

- **해결**  
  보안 그룹 인바운드 규칙에 `HTTP(80)` 및 `HTTPS(443)` 포트를 추가한 뒤 인증서를 정상 발급했습니다.

---
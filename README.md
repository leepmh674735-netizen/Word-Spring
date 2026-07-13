# Aggro's Towne BBS 🐾

Spring Boot 3.4.3 및 JSP 기반의 세련되고 프리미엄한 웹 BBS(bulletin board system) 프로젝트입니다.

## 🖥️ 홈페이지 스크린샷

마스코트인 Guide Neko와 함께하는 BBS 메인 화면입니다.

![BBS Homepage Screenshot](bbs_homepage.png)

## 🛠️ 기술 스택 (Technology Stack)

- **Backend**: Java 21, Spring Boot 3.4.3, Spring MVC, Spring Security
- **Database**: PostgreSQL (Supabase) / MyBatis & JPA (Hibernate)
- **Frontend**: JSP (Jakarta Server Pages), Bootstrap 5, Vanilla CSS
- **Mascot Artwork**: Midjourney / Image Generation

## ⚙️ 설정 및 특징

- **데이터베이스 자동 초기화**: PostgreSQL 데이터베이스 스키마 및 기초 데이터를 최초 구동 시 자동으로 로드하도록 설정되어 있습니다. ([schema1.sql](src/main/resources/schema1.sql), [data1.sql](src/main/resources/data1.sql))
- **JWT 보안**: JWT 인증 메커니즘이 구현되어 있습니다.
- **반응형 웹 UI**: 모바일과 데스크톱 모두에서 매끄럽게 동작하는 반응형 레이아웃과 Glassmorphic 카드 UI 디자인을 갖추고 있습니다.

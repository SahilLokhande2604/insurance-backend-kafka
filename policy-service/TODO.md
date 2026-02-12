# TODO: Remove user-service and keep only policy-service

- [x] Delete user-service related files:
  - [x] src/main/java/com/insurance/user_service/UserServiceApplication.java
  - [x] src/main/java/com/insurance/user_service/config/JwtFilter.java
  - [x] src/main/java/com/insurance/user_service/config/JwtUtil.java
  - [x] src/main/java/com/insurance/user_service/config/MultiDataSourceConfig.java
  - [x] src/main/java/com/insurance/user_service/config/UserDataSourceConfig.java
  - [x] src/main/java/com/insurance/user_service/config/SecurityConfig.java
  - [x] src/main/java/com/insurance/user_service/controller/UserController.java
  - [x] src/main/java/com/insurance/user_service/dto/LoginRequest.java
  - [x] src/main/java/com/insurance/user_service/dto/LoginResponse.java
  - [x] src/main/java/com/insurance/user_service/dto/UserRequest.java
  - [x] src/main/java/com/insurance/user_service/dto/UserResponse.java
  - [x] src/main/java/com/insurance/user_service/model/User.java
  - [x] src/main/java/com/insurance/user_service/model/Role.java
  - [x] src/main/java/com/insurance/user_service/model/user/Role.java
  - [x] src/main/java/com/insurance/user_service/model/user/User.java
  - [x] src/main/java/com/insurance/user_service/repository/user/UserRepository.java
  - [x] src/main/java/com/insurance/user_service/service/UserDetailsServiceImpl.java
  - [x] src/main/java/com/insurance/user_service/service/UserService.java

- [x] Create new main application class PolicyServiceApplication.java in src/main/java/com/insurance/policy_service/

- [x] Update src/main/resources/application.properties:
  - [x] Change spring.application.name=policy-service
  - [x] Change server.port=8081
  - [x] Remove user-service datasource configuration properties

- [x] Keep all policy-service related files intact

- [ ] Verify project builds and runs on port 8081 with only policy-service

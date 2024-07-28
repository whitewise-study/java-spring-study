# Interceptor 예제


## HandlerInterceptor implements 하는 클래스 생성
> 나는 토큰을 검사하는 인터셉터를 만듬

```java
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

  public final static String TOKEN_INFO  =  "tokenInfo";
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthenticationException {

    String authorization = request.getHeader(JWT_HEADER_KEY);

    if (authorization == null) {
      throw new AuthenticationException("authorization가 없습니다.");
    }

    jwtTokenProvider.isBearerToken(authorization);

    String accessToken = authorization.substring(7);
    Claims decodeToken = jwtTokenProvider.getClaimsFormToken(accessToken);

    TokenInfoDto tokenInfo = new TokenInfoDto(
        decodeToken.get(CLAIMS_KEY_USER_NAME).toString(),
        (Long) decodeToken.get(CLAIMS_KEY_USER_ID)
    );

    request.setAttribute(TOKEN_INFO, tokenInfo);
    return true;
  }
}
```


## WebMvcConfigurer를 implements하는 클래스 생성   

```java
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final JwtTokenInterceptor jwtTokenInterceptor;

  private final String[] JWT_INTERCEPTOR_URI = {
      "/swagger-ui/index.html",
      "/swagger-ui/swagger-ui-bundle.js",
      "/swagger-ui/swagger-initializer.js",
      "/api-docs/swagger-config",
      "/api-docs",
      "/api/user/login",
      "/api/user/join",
  };
  

  //토큰을 받아야 하는 서비스 설정
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(jwtTokenInterceptor) //로그인이 필요한 서비스 요청시 Interceptor가 그 요청을 가로챔
        .excludePathPatterns(JWT_INTERCEPTOR_URI); //인터셉터 제외시키기 
  }
}
```

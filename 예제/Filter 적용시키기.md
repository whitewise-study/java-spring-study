# Filter 적용시키기
## 1. Application에 `@ServletComponentScan` 
```java
@EnableJpaAuditing
@ServletComponentScan
@SpringBootApplication
public class HhplusECommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HhplusECommerceApplication.class, args);
  }
}

```

## 2. config에 Bean 만들기

```java
@Configuration
public class WebConfig {

  @Bean
  public FilterRegistrationBean<RequestResponseLoggingFilter> filterFilterRegistrationBean() {
    FilterRegistrationBean<RequestResponseLoggingFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>(new RequestResponseLoggingFilter());
    filterFilterRegistrationBean.setOrder(1);
    return filterFilterRegistrationBean;
  }

}
```

## 3. Filter를 implements하는 클래스 생성하고  @WebFilter 사용

```java
@WebFilter
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
  
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {

  }

  @Override
  public void destroy() {

  }
}

```

✅ `init 메소드`  
init 메소드는 필터 객체를 초기화하고 서비스에 추가하기 위한 메소드이다. 웹 컨테이너가 1회 init 메소드를 호출하여 필터 객체를 초기화하면 이후의 요청들은 doFilter를 통해 처리된다.  


✅ `doFilter 메소드`  
doFilter 메소드는 url-pattern에 맞는 모든 HTTP 요청이 디스패처 서블릿으로 전달되기 전에 웹 컨테이너에 의해 실행되는 메소드이다. doFilter의 파라미터로는 FilterChain이 있는데, FilterChain의 doFilter 통해 다음 대상으로 요청을 전달하게 된다. chain.doFilter() 전/후에 우리가 필요한 처리 과정을 넣어줌으로써 원하는 처리를 진행할 수 있다.    


✅ `destroy 메소드`  
destroy 메소드는 필터 객체를 서비스에서 제거하고 사용하는 자원을 반환하기 위한 메소드이다. 이는 웹 컨테이너에 의해 1번 호출되며 이후에는 이제 doFilter에 의해 처리되지 않는다.  



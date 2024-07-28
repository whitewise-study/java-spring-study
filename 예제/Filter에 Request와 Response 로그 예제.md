#  Filter에 Request와 Response 로그 찍어보기

Request와 Response의 바디와 파리미터등 로그를 찍을 때 시행착오들이 많았는데 나중에도 사용하기위 해서 남기는 기록 

```java
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

    try {
      log.info("Request Method: {}", requestWrapper.getMethod());  // 메소드
      log.info("Request URI: {}", requestWrapper.getRequestURI());   // URI

      // Header 
      requestWrapper.getHeaderNames().asIterator().forEachRemaining(header ->
          log.info("Request Header {}: {}", header, requestWrapper.getHeader(header))
      );

      // Parameter
      requestWrapper.getParameterNames().asIterator().forEachRemaining(param ->
          log.info("Request Parameter {}: {}", param, requestWrapper.getParameter(param))
      );

      long start = System.currentTimeMillis();
      filterChain.doFilter(requestWrapper, responseWrapper);
      long end = System.currentTimeMillis();

    // Request Body
      String requestBody = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
      log.info("Request Body: {}", requestBody);

   // Response Body
      String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
      log.info("Response Body: {}", responseBody);

      responseWrapper.copyBodyToResponse();

      log.info("Response Status: {}", HttpStatus.valueOf(responseWrapper.getStatus()));
      log.info("Response Time: {} ms", (end - start));
    } catch (Exception e) {
      log.error("Failed to log request/response", e);
    }
  }
```

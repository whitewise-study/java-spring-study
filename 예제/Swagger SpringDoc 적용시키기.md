# Swagger SpringDoc 적용시키기

## SwaggerConfig 생성
- [https://springdoc.org/](https://springdoc.org/)

```java
package practice.hhplusecommerce.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  
  @Bean
  public OpenAPI openAPI() {
    Info info = new Info()
        .title("Springdoc Swagger")
        .description("Springdoc을 사용한 Swagger UI")
        .version("1.0.0")
        .license(new License().name("Apache 2.0").url("http://springdoc.org"));

    // SecuritySecheme명
    String jwtSchemeName = "jwtAuth";
    // API 요청헤더에 인증정보 포함
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
    // SecuritySchemes 등록
    Components components = new Components()
        .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
            .name(jwtSchemeName)
            .type(SecurityScheme.Type.HTTP) // HTTP 방식
            .scheme("bearer")
            .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

    return new OpenAPI()
        .info(info)
        .addSecurityItem(securityRequirement)
        .components(components);
  }
}
```


## Migrating from SpringFox  
![image](https://github.com/user-attachments/assets/b6e3f3e6-8ae2-4782-88bb-b9e27e5e20cf)



## controller
```java

@Tag(name = "장바구니")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

  private final CartFacade cartFacade;

  @Operation(summary = "장바구니 목록 조회")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @GetMapping
  public List<CartResponse> getCartList(
      @RequestAttribute(value = TOKEN_INFO) TokenInfoDto tokenInfoDto
  ) {
    return cartFacade.getCartList(tokenInfoDto.getUserId())
        .stream()
        .map(CartResponseDtoMapper::toCartResponse)
        .toList();
  }

  @Operation(summary = "장바구니 추가")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404", description = "유저정보 or 상품정보가 존재하지 않습니다."),
      @ApiResponse(responseCode = "400", description = "{상품명}이 품절 상태 입니다.")
  })
  @PostMapping
  public CartResponse create(
      @RequestAttribute(value = TOKEN_INFO) TokenInfoDto tokenInfoDto,
      @RequestBody CartCreate create
  ) {
    return CartResponseDtoMapper.toCartResponse(cartFacade.addCart(tokenInfoDto.getUserId(), CartRequestDtoMapper.toCreate(create)));
  }

  @Operation(summary = "장바구니 삭제")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @DeleteMapping("/{cart-id}")
  public CartResponse delete(
      @RequestAttribute(value = TOKEN_INFO) TokenInfoDto tokenInfoDto,
      @PathVariable("cart-id") Long cartId
  ) {
    return CartResponseDtoMapper.toCartResponse(cartFacade.deleteCart(tokenInfoDto.getUserId(), cartId));
  }
}
```


## dto
```java
package practice.hhplusecommerce.cart.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class CartRequestDto {

  @Getter
  @Setter
  @Schema(name = "장바구니 생성 DTO")
  public static class CartCreate {

    @Schema(description = "상품 고유 번호", defaultValue = "1")
    private Long productId;

    @Schema(description = "구매 개수", defaultValue = "1")
    private Integer quantity;
  }
}

```

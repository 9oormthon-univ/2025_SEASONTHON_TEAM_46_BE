package jpabasic.newsthinkybe.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        Info info=new Info()
                .version("v1.0.0")
                .title("News THINKY API")
                .description("newsTHINKY swagger 입니다.");

        String jwtSchemaName="jwtAuth";
        String accessTokenScheme="accessToken";
        String refreshTokenScheme="refreshToken";

        SecurityRequirement securityRequirement=new SecurityRequirement().addList(jwtSchemaName);

        Components components=new Components()
                //Access Token
                .addSecuritySchemes(accessTokenScheme,
                        new SecurityScheme().name("Authorization") //실제 헤더 이름
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                //Refresh Token
                .addSecuritySchemes(refreshTokenScheme,
                        new SecurityScheme()
                                .name("x-refresh-token") //커스텀 헤더 이름
                                .in(SecurityScheme.In.HEADER)
                                .type(SecurityScheme.Type.APIKEY));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}

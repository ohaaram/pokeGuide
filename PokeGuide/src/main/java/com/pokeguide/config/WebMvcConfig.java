package kr.co.orangenode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AppInfo appInfo;

    @Value("${myServerProd.static-resources-path}")
    private String staticServerPathProd;

    // ====== 배포시엔 해당 어노테이션 사용 ======
    @Value("${MyServerImg.static-resources-pathImg}")
    private String staticServerPathImg;

    @Value("${front.url}")
    private String frontUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 이미지 경로
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        // 이미지 경로
        registry.addResourceHandler("/prodImg/**")
                .addResourceLocations("file:prodImg/"); // file => C://

        // 추가할 새로운 리소스 핸들러
        registry.addResourceHandler("/newStaticResources/**")
                .addResourceLocations("file:newStaticResources/");
    }

    // CORS 방지를 위한 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(frontUrl, "http://3.34.204.24","http://ec2-3-34-204-24.ap-northeast-2.compute.amazonaws.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("Authorization", "Cache-Control", "Content-Type", "X-Requested-With", "Origin", "Accept","Access-Control-Allow-Origin")
                .allowCredentials(true);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
package com.kh.eco.configuration;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${file.upload.path:./uploads/}") 
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        String absolutePath = new File(uploadPath).getAbsolutePath() + "/";
        
        String resourceLocation = "file:///" + absolutePath;

        // 3. 연결
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourceLocation);
        

        System.out.println("✅ (상대경로 적용) 내 컴퓨터 실제 경로: " + resourceLocation);
    }
}
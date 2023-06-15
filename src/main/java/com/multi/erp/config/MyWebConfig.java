package com.multi.erp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.multi.erp.common.LoginCheckInterceptor;

//자동으로 구성된 스프링 MVC구성을 변경없이 추가 작업을 하기 위해 사용
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		//특정 path로 요청하는 경우 실제 파일이 저장된 위치를 연결해서 리소스를 가져올 수 있도록 처리
		registry.addResourceHandler("/download/**")
		.addResourceLocations("file:///C:/javaweb/upload/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new LoginCheckInterceptor()).order(1).addPathPatterns("/**").excludePathPatterns(
				"/index.do", "/emp/login.do", "/emp/spring/login", "/board/list.do", "/board/ajax/list.do",
				"/images/**", "/css/**", "/js/**");
	}

}

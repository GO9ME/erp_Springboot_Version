package com.multi.erp.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.multi.erp.member.MemberDTO;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인사용자인 경우 세션에 user라는 이름의 어트리뷰트가 저장되어 있으므로 
		//user가 없으면 로그인이 처리되지 않음을 의미
		HttpSession session = request.getSession(false);
		if(session!=null) {
			MemberDTO user = (MemberDTO)session.getAttribute("user");
			if(user==null) {//로그인하지 않은 상태
				response.sendRedirect("/erp/emp/login.do");
				//로그인하지 않은 사용자는 다음(뷰에서 선택한 path로 이동되지 않도록)으로 넘어가지 않도록 false를리턴
				return false;
			}
		}
		//로그인된 사용자는 다음으로 넘어갈 수 있도록 true리턴
		return true;
		
	}
	

}

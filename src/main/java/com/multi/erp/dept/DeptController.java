package com.multi.erp.dept;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//클라이언트에게 보여줄 뷰를 요청하기 위한 메소드
//디비연동이나 비지니스로직을 처리할 메소드
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Controller
public class DeptController {
	@Autowired
	DeptService service;

	// insert를 하기 위해 view를 볼 수 있는 메소드
	@RequestMapping("/dept/register")
	public String showPage() {
		return "dept/dept_register";
	}

	// view에 입력한 데이터를 db에 insert하기 위한 메소드
	// 사용자가 입력한 데이터를 추출
	@RequestMapping("/dept/insert.do")
	public String insert(DeptDTO dept) throws IllegalStateException, IOException {
		
		System.out.println(dept);
		service.insert(dept);
		return "redirect:/dept/list.do";
	}

	// 조회한 List를 공유하고 view에서 정보를 출력하도록 해야 한다.
	@RequestMapping("/dept/list.do")
	public ModelAndView list() {
		/* ModelAndView mav = new ModelAndView("dept/deptlist_jstl"); */
		 ModelAndView mav = new ModelAndView("dept/list"); 
		// 서비스의 메소드 호출
		List<DeptDTO> deptlist = service.select();
		System.out.println(deptlist);
		// 결과 공유
		mav.addObject("deptlist", deptlist);
		return mav;
	}

	@RequestMapping("/dept/read.do")
	public ModelAndView read(String deptno, String state) {
		ModelAndView mav = new ModelAndView();
		// 서비스메소드 호출
		DeptDTO dept = service.read(deptno);
		// 데이터공유
		mav.addObject("dept", dept);
		// 뷰정보셋팅
		String view = "";
		if (state.equals("READ")) {
			view = "dept/dept_read_jstl";
		} else {
			view = "dept/dept_update";
		}
		mav.setViewName(view);
		return mav;
	}

	@RequestMapping("/dept/delete.do")
	public String delete(String deptno) {
		service.delete(deptno);
		return "redirect:/dept/list.do";
	}

	@RequestMapping("/dept/update.do")
	public String update(DeptDTO dept) {
		System.out.println("======" + dept);
		service.update(dept);
		return "redirect:/dept/list.do";
	}

}

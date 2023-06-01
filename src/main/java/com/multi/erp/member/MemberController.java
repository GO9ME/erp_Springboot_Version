package com.multi.erp.member;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multi.erp.common.FileUploadLogicService;
import com.multi.erp.dept.DeptDTO;
import com.multi.erp.dept.DeptService;
//@SessionAttributes("user")user라는 것은  세션에 저장하는 어트리뷰트명
//컨트롤러에서 user라는 이름으로 Model객체 저장된 어트뷰트가 있으면 이를 세션에도 저장해준다.
@Controller
@RequestMapping("/emp")
@SessionAttributes("user")
public class MemberController {
	MemberService service;
	DeptService deptService;
	FileUploadLogicService fileuploadService;
	
	public MemberController() {
		
	}
	@Autowired
	public MemberController(MemberService service, DeptService deptService, FileUploadLogicService fileuploadService) {
		super();
		this.service = service;
		this.deptService = deptService;
		this.fileuploadService = fileuploadService;
	}

//	<view-controller path="/emp/insert" view-name="member/insertPage"/>
	@RequestMapping("/insert")
	public String insertPage(Model model) {
		//DepteService등록하고 부서명을 register.jsp에서 사용할 수 있도록 attribute로 추가하기
		List<DeptDTO> deptnamelist = deptService.select();
		model.addAttribute("deptnamelist", deptnamelist);
		return "member/insertPage";
	}
	@PostMapping("/insert.do")
	public String insert(MemberDTO user,HttpSession session) throws IllegalStateException, IOException {
		//파일업로드
		MultipartFile file = user.getUserImage();
		//String path = WebUtils.getRealPath(session.getServletContext(), "/WEB-INF/static/upload");
		System.out.println("=========================");
		System.out.println(user);
		System.out.println("=========================");
		// 파일업로드 서비스의 메소드를호출해서 직접 업로드
		String storeFilename = fileuploadService.uploadFile(file);
		user.setProfile_photo(storeFilename);
		if(user.getMarry()==null) {//체크박스 선택하지 않았다는 의미 - 미혼의의미
			user.setMarry("0");
		}
		user.setGender(user.getSsn().substring(6, 7));//7번째 위치의 값을 추출
		System.out.println("=******************************========================");
		System.out.println(user);
		System.out.println("====================******************************======");
		service.insert(user);
		return "redirect:/index";
	}
	@GetMapping("/login.do")
	public String login() {
		return "login";
	}
	@PostMapping("/login.do")
	public ModelAndView login(MemberDTO loginUserInfo ,HttpServletRequest request) {
		System.out.println("=============loginUserInfo==========="+loginUserInfo);
		ModelAndView mav = new ModelAndView();
		MemberDTO user =  service.login(loginUserInfo);
		String view="";
		if(user!=null) {
			//로그인성공
			HttpSession session = request.getSession(); //세션만들기
			//세션에 데이터 공유하기
			session.setAttribute("user", user);
			//서비스에서 가공한 뷰의 이름 - 로그인한 사용자가 어떠 job이냐에 따라 작업하 수 있는 메뉴가 달라질 수 있도록 
			view = user.getMenupath();
		}else {
			//로그인실패
			view = "redirect:/emp/login.do";
		}
		mav.setViewName(view);
		return mav;
	}
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		if(session!=null) {
			 //사용하던 세션을 메모리에서 제거하기
			 session.invalidate();
		}
		return "redirect:/emp/login.do";
	}
	
	//스프링에서 제공되는 기능을 이용한 로그인
	@RequestMapping("/spring/login")
	public String springlogin(MemberDTO loginUserInfo ,Model model) {
		System.out.println("스프링이 제공하는 @sessionAttributes를 이용해서 세션처리하기");
		MemberDTO user =  service.login(loginUserInfo);
		String view="";
		if(user!=null) {
			model.addAttribute("user",user);
			view = user.getMenupath();
		}else {
			view = "redirect:/emp/login.do";
		}
		return view;			
	}
		
	//스프링에서 제공되는 기능을 이용한 로그아웃
	@RequestMapping("/spring/logout")
	public String springlogout(SessionStatus status) {
		System.out.println("SessionStatus를 이용한 로그아웃처리하기");
		status.setComplete();//세션에 있는 객체를 제거 
		return "redirect:/index";
	}
	
	
	@RequestMapping("/mypage")
	public String mypage(HttpSession session ) {
		//나중에는 제일 복잡한 컨트롤러가 될 수 있다.
		//결재에 대한 진행상황
		//스케쥴표 -업무스케쥴,미팅일정,휴가일정
		//진행중인 메인업무에 대한 내용
		MemberDTO user = (MemberDTO) session.getAttribute("user");
		return user.getMenupath();
	}
	@RequestMapping("/dept/tree.do")
	public String showTree(Model model) {
		List<DeptDTO> deptlist =  deptService.select();
		model.addAttribute("deptlist",deptlist);
		return "dept/tree";
	}
	@RequestMapping(value="/idcheck.do",produces = "application/text;charset=utf-8"
			, method = RequestMethod.GET)
	
	public @ResponseBody String idCheck(String id) {
		String msg = "";
		boolean check = service.idCheck(id);
		if(check) {
			//기존 DB에 저장되어 있는 아이디
			msg = "사용불가능한 아이디";
		}else {
			msg = "사용가능한 아이디";
		}
		return msg;
	}
	//선택한 부서에 근무하는 직원목록과 선택한 부서의 하위부서목록을 JSON문자열로 리턴
	@RequestMapping(value = "/ajaxtreedata",produces = "application/text;charset=utf-8")
	@ResponseBody
	public String getTreedata(String deptno) throws JsonProcessingException {
		List<DeptDTO> deptlist =  deptService.getSubDeptlist(deptno);
		List<MemberDTO> memberlist =service.getTreeEmpList(deptno);
		System.out.println("=========================================");
		System.out.println(deptlist);
		System.out.println(memberlist);
		System.out.println("=========================================");
		//Java객체 ----------------> JSON
		TreeDataDTO treedto = new TreeDataDTO(memberlist, deptlist);
		//자바객체를 JSON문자열로 변경하기 위해서 Jackson라이브러리에서 지원하는 ObjectMapper를 직접 사용
		ObjectMapper mapper = new ObjectMapper();
		//writeValue(변환하기위한 JSON형식파일,변환할객체)
		//writeValueAsString(변환할객체-DTO) - 자바객체를 JSON문자열로 변경
		String jsonStr =  mapper.writeValueAsString(treedto);
		System.out.println(jsonStr);
		return jsonStr;
	}
}


































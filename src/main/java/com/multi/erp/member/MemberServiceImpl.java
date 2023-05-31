package com.multi.erp.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class MemberServiceImpl implements MemberService {
	MemberDAO dao;
	public MemberServiceImpl() {
		
	}
	@Autowired
	public MemberServiceImpl(MemberDAO dao) {
		super();
		this.dao = dao;
	}

	@Override
	public List<MemberDTO> getTreeEmpList(String deptno) {
		// TODO Auto-generated method stub
		return dao.getTreeEmpList(deptno);
	}

	@Override
	public int insert(MemberDTO user, MultipartFile file, String realpath, String filename) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean idCheck(String id) {
		// TODO Auto-generated method stub
		return dao.idCheck(id);
	}

	@Override
	public List<MemberDTO> getMemberList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberDTO read(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberDTO> search(String column, String search, String pass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(MemberDTO user) {
		// TODO Auto-generated method stub
		return 0;
	}
    // 컨틀롤러 ------> 서비스 -----> DAO
	// 컨트롤러 <------ 서비스 <----- DAO
	///menu/insa_menu.jsp
	@Override
	public MemberDTO login(MemberDTO loginUser) {
		//user가 db인증 후에 받은 결과
		MemberDTO user= dao.login(loginUser);
		System.out.println("서비스==>"+user);
		//db에서 가져온 값에서 menupath를 가공해서 뷰의 이름을 menupath에 셋팅
		if(user!=null) {
			String menupath = user.getMenupath();
			menupath = menupath.substring(menupath.indexOf("/")+1, menupath.indexOf("_"));
			user.setMenupath(menupath);//잘라낸 문자열이 뷰이름이므로 다시 menupath에 셋팅하는 작업
		}
		System.out.println("서비스=+++++++++++++++++++++++++=>"+user);
		return user;
	}
	@Override
	public int insert(MemberDTO user) {
		// TODO Auto-generated method stub
		return dao.insert(user);
	}

}















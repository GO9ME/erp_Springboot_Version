package com.multi.erp.member;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class MemberDAOImpl implements MemberDAO{
	SqlSession sqlSessionTemplate;
	public MemberDAOImpl() {
		
	}
	@Autowired
	public MemberDAOImpl(SqlSession sqlSessionTemplate) {
		super();
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	@Override
	public List<MemberDTO> getTreeEmpList(String deptno) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("com.multi.erp.member.treedata", deptno);
	}

	@Override
	public int insert(MemberDTO user) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("com.multi.erp.member.insert",user);
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

	@Override
	public MemberDTO login(MemberDTO loginUser) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("com.multi.erp.member.login", loginUser);
	}

	@Override
	public boolean idCheck(String id) {
		boolean result = false;
		MemberDTO user = sqlSessionTemplate.selectOne("com.multi.erp.member.idcheck", id);
		if(user!=null) {
			result = true;
		}
		return result;
	}

	@Override
	public MemberDTO findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}

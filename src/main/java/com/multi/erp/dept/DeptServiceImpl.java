package com.multi.erp.dept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	DeptDAO dao;
	@Override
	public int insert(DeptDTO dept) {
		// TODO Auto-generated method stub
		return dao.insert(dept);
	}

	@Override
	public List<DeptDTO> getDeptName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DeptDTO> select() {
		// TODO Auto-generated method stub
		return dao.select();
	}

	@Override
	public int delete(String deptno) {
		// TODO Auto-generated method stub
		return dao.delete(deptno);
	}

	@Override
	public DeptDTO read(String deptno) {
		// TODO Auto-generated method stub
		return dao.read(deptno);
	}

	@Override
	public int update(DeptDTO dept) {
		// TODO Auto-generated method stub
		return dao.update(dept);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insert(DeptDTO dept, List<DeptFileDTO> deptfiledtolist) {
		dao.insert(dept);
		dao.insertFile(deptfiledtolist);
		return 0;
	}

	@Override
	public List<DeptDTO> getSubDeptlist(String uppercode) {
		// TODO Auto-generated method stub
		return dao.getSubDeptlist(uppercode);
	}

}

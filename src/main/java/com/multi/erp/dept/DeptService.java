package com.multi.erp.dept;

import java.util.List;

public interface DeptService {
	int insert(DeptDTO dept);
	List<DeptDTO> getDeptName();
	//전체사원목을 조회하는 메소드
	public List<DeptDTO> select();
	//부서등록  - dept테이블과 deptimage_file테이블에 저장
	int insert(DeptDTO dept,List<DeptFileDTO> deptfiledtolist);
	public int delete(String deptno);
	public DeptDTO read(String deptno);
	int update(DeptDTO dept);
	//하위부서를 조회하기 위한 메소드
	public List<DeptDTO> getSubDeptlist(String uppercode);
}

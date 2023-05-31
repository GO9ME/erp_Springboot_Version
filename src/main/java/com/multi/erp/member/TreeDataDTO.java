package com.multi.erp.member;

import java.util.List;

import com.multi.erp.dept.DeptDTO;

public class TreeDataDTO {
	private List<MemberDTO>  memberlist;
	private List<DeptDTO>  deptlist;
	public TreeDataDTO() {
		
	}
	public TreeDataDTO(List<MemberDTO> memberlist, List<DeptDTO> deptlist) {
		super();
		this.memberlist = memberlist;
		this.deptlist = deptlist;
	}
	public List<MemberDTO> getMemberlist() {
		return memberlist;
	}
	public void setMemberlist(List<MemberDTO> memberlist) {
		this.memberlist = memberlist;
	}
	public List<DeptDTO> getDeptlist() {
		return deptlist;
	}
	public void setDeptlist(List<DeptDTO> deptlist) {
		this.deptlist = deptlist;
	}
	@Override
	public String toString() {
		return "TreeDataDTO [memberlist=" + memberlist + ", deptlist=" + deptlist + "]";
	}
	
}

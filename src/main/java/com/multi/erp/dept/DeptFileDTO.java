package com.multi.erp.dept;

public class DeptFileDTO {
	private String deptno;
	private String originalFilename;
	private String storeFilename;
	private String deptImageFileno;
	public DeptFileDTO() {
		
	}
	
	public DeptFileDTO(String originalFilename, String storeFilename) {
		super();
		this.originalFilename = originalFilename;
		this.storeFilename = storeFilename;
	}
	
	//boardfile의 상세정보 - insert
	public DeptFileDTO(String originalFilename, String storeFilename, String deptImageFileno) {
		super();
		this.originalFilename = originalFilename;
		this.storeFilename = storeFilename;
		this.deptImageFileno = deptImageFileno;
	}
	
	//select
	public DeptFileDTO(String deptno, String originalFilename, String storeFilename, String deptImageFileno) {
		super();
		this.deptno = deptno;
		this.originalFilename = originalFilename;
		this.storeFilename = storeFilename;
		this.deptImageFileno = deptImageFileno;
	}

	
	
	@Override
	public String toString() {
		return "BoardFileDTO [board_no=" + deptno + ", originalFilename=" + originalFilename + ", storeFilename="
				+ storeFilename + ", boardFileno=" + deptImageFileno + "]";
	}

	
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	public String getStoreFilename() {
		return storeFilename;
	}
	public void setStoreFilename(String storeFilename) {
		this.storeFilename = storeFilename;
	}

	public String getDeptno() {
		return deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}

	public String getDeptImageFileno() {
		return deptImageFileno;
	}

	public void setDeptImageFileno(String deptImageFileno) {
		this.deptImageFileno = deptImageFileno;
	}

	
		
}

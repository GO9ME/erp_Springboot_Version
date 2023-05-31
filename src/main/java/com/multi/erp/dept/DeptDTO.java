package com.multi.erp.dept;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*@Setter
@Getter
@ToString*/
@Data
//전체 멤버변수를 매개변수로 하는 생성자 정의
@AllArgsConstructor 
@NoArgsConstructor
@RequiredArgsConstructor
@Alias("dept")
public class DeptDTO {
	@NonNull
	private String deptno;
	@NonNull
	private String deptname;
	private String deptStartDay;
	private String deptEndDay;
	private String deptlevel;
	private String deptstep;
	private String deptuppercode;
	private String job_category;
	private String mgr_id;
	private String deptaddr;
	private String depttel;
	public DeptDTO(@NonNull String deptname, String deptlevel, String deptstep) {
		super();
		this.deptname = deptname;
		this.deptlevel = deptlevel;
		this.deptstep = deptstep;
	}
	
	
}

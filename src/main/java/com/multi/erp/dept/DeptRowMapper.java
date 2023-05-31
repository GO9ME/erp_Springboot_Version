package com.multi.erp.dept;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
//queryXXX메소드 내부에서 디비에서 조회한 레코드를 어떤 객체에 매핑시켜야 하는지 정보를 담고 있는 객체
//while(){  }, if() { }안에서 처리할 내용을 정의
//mapRow는 레코드 하나가 메소드 한 번 호출
public class DeptRowMapper implements RowMapper<DeptDTO> {
	//mapRow는 레코드 갯수만큼 호출
	@Override
	public DeptDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		System.out.println(rowNum);
		DeptDTO dept = new DeptDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
				rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),
				rs.getString(10),rs.getString(11));
		
		return dept;
	}

}

package com.multi.erp.dept;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;

@Repository
public class DeptDAOImpl implements DeptDAO {
	@Autowired
	JdbcTemplate template;
	@Override
	public List<DeptDTO> getDeptName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(DeptDTO dept) {
		String sql = "insert into dept values(null,?,?,null,?,?,?,?,?,?,?)";
		return template.update(sql, dept.getDeptname(),dept.getDeptStartDay(),dept.getDeptlevel(),
				dept.getDeptstep()	,dept.getDeptuppercode(),dept.getJob_category(),dept.getMgr_id(),
				dept.getDeptaddr(),dept.getDepttel());
	}

	@Override
	public List<DeptDTO> select() {
		//jdbc작업할 때 작성했던 모든 코드가 query메소드 내부에서 자동으로 처리
		//매번 달라지는 sql문과 조회한 레코드를 어떤 dto에 매핑할 것인지 정보를 담고있는 RowMapper객체만 넘겨주면 
		//자동으로 모든레코드를 List로 만들어서 리턴
		return template.query("select * from dept", new DeptRowMapper());
	}

	@Override
	public int delete(String deptno) {
		// TODO Auto-generated method stub
		return template.update("delete from dept where deptno=?", deptno);
	}

	@Override
	public DeptDTO read(String deptno) {
		// TODO Auto-generated method stub
		return template.queryForObject("select * from dept where deptno =?", new Object[] {deptno}, new DeptRowMapper());
	}

	@Override
	public int update(DeptDTO dept) {
		String sql = "update dept set mgr_id=?,deptaddr=?,depttel=? where deptno=?";
		return template.update(sql,dept.getMgr_id(),
				dept.getDeptaddr(),dept.getDepttel(),dept.getDeptno());
	}

	/*
	 * 보통 쿼리를 던지고 응답을 받은 후에야 다음 쿼리를 전달하기 때문에 개별 Insert의 경우 
	 * 지연이 많이 발생하지만 하나의 트랜잭션으로
	 * 묶이는 Batch Insert의 경우는 하나의 쿼리문으로 수행하기 때문에 성능이 월등히 좋습니다.
	 */
	@Override
	public int insertFile(List<DeptFileDTO> deptfiledtolist) {
		/*
		 * StringBuffer sql = new StringBuffer();
		 * sql.append("insert into deptimage_file values "); for(DeptFileDTO dto :
		 * deptfiledtolist) {
		 * sql.append("(last_insert_id(),"+dto.getOriginalFilename()+","+dto.
		 * getStoreFilename() +","+dto.getDeptImageFileno()+"),"); }
		 * System.out.println(sql);
		 */
		String sql = "insert into deptimage_file values (last_insert_id(),?,?,?)";
//		for(DeptFileDTO dto :deptfiledtolist) {
//			template.update(sql,dto.getOriginalFilename(),
//					dto.getStoreFilename(),dto.getDeptImageFileno());
//		}
//		template.batchUpdate(sql, deptfiledtolist, deptfiledtolist.size(),
//				(PreparedStatement ps, DeptFileDTO deptfiledto) -> {
//                    ps.setString(1, deptfiledto.getOriginalFilename());
//                    ps.setString(2,deptfiledto.getStoreFilename());
//                    ps.setString(3,deptfiledto.getDeptImageFileno());
//                });
		template.batchUpdate(sql, deptfiledtolist, deptfiledtolist.size(),
				new ParameterizedPreparedStatementSetter<DeptFileDTO>() {

					@Override
					public void setValues(PreparedStatement ps, DeptFileDTO deptfiledto) throws SQLException {
						 ps.setString(1, deptfiledto.getOriginalFilename());
	                    ps.setString(2,deptfiledto.getStoreFilename());
	                    ps.setString(3,deptfiledto.getDeptImageFileno());
					}
			
				});
		return 0;
	}

	@Override
	public List<DeptDTO> getSubDeptlist(String uppercode) {
		// TODO Auto-generated method stub
		return template.query("select * from dept where deptuppercode=?", new Object[] {uppercode},new DeptRowMapper());
	}

}














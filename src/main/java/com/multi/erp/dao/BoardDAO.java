package com.multi.erp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.multi.erp.dto.BoardDTO;
import com.multi.erp.dto.BoardFileDTO;

@Repository
public class BoardDAO {
	
	SqlSession sqlSessionTemplate;
	
	@Autowired
	public BoardDAO(SqlSession sqlSessionTemplate) {
		super();
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public void insert(BoardDTO board) {
		System.out.println("dao boarddto : " +  board);
	}
	
	
	
	
	public List<BoardDTO> boardList() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("com.multi.erp.board.selectall");
	}

	
	public BoardDTO read(String board_no) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("com.multi.erp.board.read", board_no);
	}

	
	public int update(BoardDTO board) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("com.multi.erp.board.update",board);
	}

	
	public int delete(String board_no) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("com.multi.erp.board.delete", board_no);
	}

	
	public List<BoardDTO> search(String data) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("com.multi.erp.board.search",data);
	}

	
	public List<BoardDTO> search(String tag, String data) {
		List<BoardDTO> list = null;
		System.out.println(tag+",===================,"+data);
		Map<String, String> map = new HashMap<String, String>();
		map.put("tag", tag);//key에 정의한 값을 mybatis에서 매핑
		map.put("data", data);
		list = sqlSessionTemplate.selectList("com.multi.erp.board.dynamicsearch", map);
		return list;
	}

	
	public List<BoardDTO> findByCategory(String category) {
		System.out.println(category);
		// TODO Auto-generated method stubcategorySearch
		List<BoardDTO> list = sqlSessionTemplate.selectList("com.multi.erp.board.categorySearch",category);
		System.out.println("====dao===="+list.size());
		return list;
	}
	
	public int insertFile(List<BoardFileDTO> boardfiledtolist) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("com.multi.erp.board.fileinsert", boardfiledtolist);
	}
	
	public List<BoardFileDTO> getFileList(String boardno) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("com.multi.erp.board.fileselect", boardno);
	}
	
	public BoardFileDTO getFile(BoardFileDTO inputdata) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("com.multi.erp.board.getfileinfo", inputdata);
	}
}

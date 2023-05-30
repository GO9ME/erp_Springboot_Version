package com.multi.erp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.multi.erp.common.FileUploadLogicService;
import com.multi.erp.dto.BoardDTO;
import com.multi.erp.dto.BoardFileDTO;
import com.multi.erp.service.BoardService;


@Controller
public class BoardController {
	BoardService service;
	FileUploadLogicService fileuploadservice;

	public BoardController(BoardService service, FileUploadLogicService fileuploadservice) {
		super();
		this.service = service;
		this.fileuploadservice = fileuploadservice;
	}

	@GetMapping("/board/insert")
	public String insert() {

		return "board/board_write";
	}

	@PostMapping("/board/insert")
	public String insert(BoardDTO board) {
		service.insert(board);
		return "index2";
	}
	
	@GetMapping("/board/write")
	public String writePage() {
		return "board/writepage";//view
	}
	
	@PostMapping("/board/write")
	public String write(BoardDTO board,HttpSession session) throws IllegalStateException, IOException {
		System.out.println(board);
		//1. MultipartFile정보를 추출
		List<MultipartFile> files = board.getFiles();
		//2. 업로드될 서버의 경로 추출
		//  - 실제 서버의 경로를 추출하기 위해서 Context객체의 정보를 담고 있는 ServletContext객체를 추출
		//  - ServletContext가 우리가 웹에서 운영할 프로젝트에 대한 정보를 담고 있는 객체이고
		//    실제경로를 구할 수 있는 메소드가 있음
		String path = WebUtils.getRealPath(session.getServletContext(), "/WEB-INF/upload");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(path);
		//3. 업로드로직을 구현해서 업로드 되도록 처리
		List<BoardFileDTO> boardfiledtolist =  fileuploadservice.uploadFiles(files, path);
		//4. 게시글에 대한 일반적인 내용과 첨부파일이 있는 경우 첨부되는 파일의 정보를 담은 List<BoardFileDTO>를 디비에 저장하기 위해 서비스에 전달
		service.insert(board, boardfiledtolist);
		
		return "redirect:/board/list.do?category=all";//컨트롤러를 요청재지정
	}
	@RequestMapping("/board/list.do")
	public ModelAndView list(String category) {
		System.out.println(category+"====");
		ModelAndView mav = new ModelAndView("board/list");
		List<BoardDTO> boardlist =  service.findByCategory(category);
		mav.addObject("category", category);
		mav.addObject("boardlist",boardlist);
		System.out.println(boardlist);
		return mav;//view
	}
	@RequestMapping("/board/search.do")
	public ModelAndView search(String tag,String search) {
		ModelAndView mav = new ModelAndView("board/list");
		List<BoardDTO> boardlist =  service.search(tag,search);
		mav.addObject("boardlist",boardlist);
		System.out.println(boardlist);
		return mav;
	}

}

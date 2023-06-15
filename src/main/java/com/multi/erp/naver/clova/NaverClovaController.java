package com.multi.erp.naver.clova;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.multi.erp.common.FileUploadLogicService;
import com.multi.erp.dept.FileUploadLogic;

@Controller
public class NaverClovaController {
	OCRService service;
	FileUploadLogicService uploadService;

	@Autowired
	public NaverClovaController(OCRService service, FileUploadLogicService uploadService) {
		super();
		this.service = service;
		this.uploadService = uploadService;
	}

	@RequestMapping("/clovaapi/ocrview")
	public String ocrview() {
		return "commonjob/receiptjob";
	}
	
	//파일 업로드 되는 영수증에서 필요한 정보를 추출해서 리턴 - ocr 서비스 사용후 결과를 뷰로 보내기
	@RequestMapping(value = "/naverclova/ocr", produces = "application/text;charset=utf-8")
	@ResponseBody
	public String ocrTest(NCPCloverOcrDTO data) throws IllegalStateException, IOException {
		//dto에서 업로드되는 파일 정보 추출해서 파일업로드
		System.out.println("ajax요청 데이터 =====>" + data);
		// 1. dto에서 MultipartFile추출
		MultipartFile file = data.getFile();
		// 2. 우리 erp서버에 업로드
		String storeFilename = uploadService.uploadFile(file);
		String originalFilename = file.getOriginalFilename();
		// 3. 이미지 파일 경로를 매개변수로 넘기면서 네이버서버로 업로드
		String result = service.getJsonText(uploadService.getUploadpath(storeFilename));
		return result;
	}
}

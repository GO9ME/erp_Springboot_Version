package com.multi.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileDTO {
	private String board_no;
	private String originalFilename;
	private String storeFilename;
	private String boardFileno;
}

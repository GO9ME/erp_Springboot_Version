package com.multi.erp.board;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("boardfile")
public class BoardFileDTO {
	private String board_no;
	private String originalFilename;
	private String storeFilename;
	private String boardFileno;
}

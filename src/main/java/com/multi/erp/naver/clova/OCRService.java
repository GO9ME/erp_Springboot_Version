package com.multi.erp.naver.clova;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class OCRService {
	// API gateway invokeurl

	public String getJsonText(String imagename) {
		String apiURL = "https://lf1x5n5kuq.apigw.ntruss.com/custom/v1/23142/4198b31021f06e3dbb6a691f1e28196ea80c0be0cad47c211f63260d84be6a3b/infer";
		String secretKey = "YWphVHhpbm9KYXdwYnVZWkR0U0dmVFdmeERUclBIYms=";
		StringBuffer response = new StringBuffer();
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setReadTimeout(30000);
			con.setRequestMethod("POST");
			String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			con.setRequestProperty("X-OCR-SECRET", secretKey);

			// Jackson 라이브러리
			// OCR 서비스를 실행하기 위해서 request에 설정한 값들을 json으로 설정
			// json 만드는 라이브러리를 jackson에 맞게 변경
			// JSONOjbec -> ObjectNode
			// JSONArray -> ArrayNode
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode json = mapper.createObjectNode();

			json.put("version", "V2");
			json.put("requestId", UUID.randomUUID().toString());
			json.put("timestamp", System.currentTimeMillis());

			ObjectNode image = mapper.createObjectNode();
			//ArrayNode templateIds = mapper.createArrayNode();
			//templateIds.add(25202);
			image.put("format", "jpg");
			image.put("name", "demo");
			//image.putArray("templateIds").addAll(templateIds);

			ArrayNode images = mapper.createArrayNode();
			images.add(image);
			json.putArray("images").addAll(images);
			String postParams = json.toString();
			System.out.println(postParams);

			con.connect();
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			long start = System.currentTimeMillis();
			File file = new File(imagename);
			writeMultiPart(wr, postParams, file, boundary);
			wr.close();

			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { //응답성공이면 응답되는 데이터를 모두 읽어서 BufferedReader를 만듬
				System.out.println("200");
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				System.out.println("실패");
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			
			//BufferedREader에 추가된 응답데이터를 읽어서 StringBuffer에 추가
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();

			System.out.println(response);

		} catch (Exception e) {
			System.out.println(e);
		}
		//네이버 서버와 통신한 결과를 리턴 - 영수증에서 읽은 문자열들이 응답데이터
		return response.toString();
	}

	private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("--").append(boundary).append("\r\n");
		sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
		sb.append(jsonMessage);
		sb.append("\r\n");

		out.write(sb.toString().getBytes("UTF-8"));
		out.flush();

		if (file != null && file.isFile()) {
			out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
			StringBuilder fileString = new StringBuilder();
			fileString.append("Content-Disposition:form-data; name=\"file\"; filename=");
			fileString.append("\"" + file.getName() + "\"\r\n");
			fileString.append("Content-Type: application/octet-stream\r\n\r\n");
			out.write(fileString.toString().getBytes("UTF-8"));
			out.flush();

			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] buffer = new byte[8192];
				int count;
				while ((count = fis.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
				out.write("\r\n".getBytes());
			}

			out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
		}
		out.flush();
	}
}

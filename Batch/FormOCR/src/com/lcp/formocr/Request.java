package com.lcp.formocr;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

/**
 * 百度表格识别异步请求
 * @author 雷云竹
 *
 */
public class Request {
	public static final String APP_ID = "xxxxxxx";
	public static final String API_KEY = "xxxxxxxxxxxxxxxxxxxxx";
	public static final String SECRET_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	
	public static void main(String[] args) {

		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("detect_direction", "true");
		
		
		String value="E:\\Data\\2016\\32.jpg";
		JSONObject res = client.tableRecognitionAsync(value, options);
		System.out.println(res.toString(2));
		
	}
}

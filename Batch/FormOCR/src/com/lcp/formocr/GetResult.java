package com.lcp.formocr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

/**
 * 百度表格识别请求结果获取
 * @author 雷云竹
 *
 */
public class GetResult {

	public static final String APP_ID = "xxxxxxx";
	public static final String API_KEY = "xxxxxxxxxxxxxxxxxxxxx";
	public static final String SECRET_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
	
	public static void main(String[] args) {

		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		HashMap<String, String> options = new HashMap<String, String>();
		
		
		Map<String,String> imageResult=new HashMap<String,String>();
		imageResult.put("32.jpg","16178032_986220");


		for(String key: imageResult.keySet()) {
			String value=imageResult.get(key);
			JSONObject res = client.tableResultGet(value, options);
			System.out.println(key+"   "+ res.toString(2));
		}
		
	}
	static void downLoad(String url, String file) throws IOException {
		String command = "curl " + url + " --output " + file;
		Runtime.getRuntime().exec(command);
	}
}

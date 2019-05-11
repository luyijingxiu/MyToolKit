package com.lcp.formocr;

import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import com.baidu.aip.ocr.AipOcr;

/**
 * 批量地把相应目录下的图片转成excel
 * 
 * 问题：
 * 1.百度的接口经常抽风，有时候不是image recognition error，要么就是一直进行中，但是一般改图片之后一般就没问题了
 * 2.appid,key,目录，文件范围等都是硬编码
 * @author 雷云竹
 *
 */
public class FormOCR {
	public static final String APP_ID = "xxxxxxx";
	public static final String API_KEY = "xxxxxxxxxxxxxxxxxxxxx";
	public static final String SECRET_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

	public static void main(String[] args) throws InterruptedException, IOException {
//        // 初始化一个AipOcr
		AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
		
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		String dir = "E:\\Data\\2013\\";
		File file = new File(dir);
		for (int i = 148; i <= 155; i++) {
			String imageName = i <= 9 ? "0" + i + ".jpg" : i + ".jpg";

			System.out.println(dir + imageName);
			HashMap<String, String> options = new HashMap<String, String>();
			String imagePath = dir + imageName;

			List<String> requestIds = request(client, options, imagePath);

			Thread.sleep(20000);
			for (Iterator iterator = requestIds.iterator(); iterator.hasNext();) {
				String requestId = (String) iterator.next();
				String url = getResult(client, options, requestId);

				if (url.isEmpty()) {
					System.out.println("No url data!");
				} else {
					String excelName = i < 9 ? "0" + i + ".xls" : i + ".xls";
					downLoad(url, dir + excelName);
				}
			}
		}

	}

	/**
	 * 请求识别
	 * 
	 * @param client
	 * @param options
	 * @param imagePath
	 * @return
	 */
	static List<String> request(AipOcr client, HashMap<String, String> options, String imagePath) {
		List<String> r = new ArrayList<>();
		JSONObject res = client.tableRecognitionAsync(imagePath, options);

		System.out.println(res.toString(2));

		if (!res.has("error_code")) {
			JSONArray result = res.getJSONArray("result");
			for (int j = 0; j < result.length(); j++) {
				JSONObject request = (JSONObject) result.get(j);
				String requestId = request.getString("request_id");
				r.add(requestId);
			}
		} else {
			System.out.println("Error: " + imagePath + "  " + res.toString(2));

		}
		return r;
	}
	
	/**
	 * 获取处理结果
	 * @param client 客户端
	 * @param options 请求选项
	 * @param requestId 请求id
	 * @return 返回excel文件的url
	 * @throws InterruptedException
	 */
	static String getResult(AipOcr client, HashMap<String, String> options, String requestId)
			throws InterruptedException {
		while (true) {
			JSONObject excel = client.tableResultGet(requestId, options);

			System.out.println(excel.toString(2));

			if (!excel.has("error_code")) {
				JSONObject excelResult = excel.getJSONObject("result");
				if (excelResult.getString("ret_msg").equals("已完成")) {
					String url = excelResult.getString("result_data");
					return url;
				} else {
					Thread.sleep(5000);
					continue;
				}

				// JSONObject result_data = excelResult.getJSONObject("result_data");

			} else {
				return "";
			}

		}
	}

	/**
	 * 下载
	 * 这里比较取巧的使用了系统curl命令
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	static void downLoad(String url, String file) throws IOException {
		String command = "curl " + url + " --output " + file;
		Runtime.getRuntime().exec(command);
	}
}

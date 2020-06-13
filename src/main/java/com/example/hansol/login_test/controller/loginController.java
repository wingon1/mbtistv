package com.example.hansol.login_test.controller;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class loginController {

	@RequestMapping( value = "/test/", method = RequestMethod.GET)
	public String test(Model model, HttpSession session, HttpServletRequest request) {

		return "login_test/index";
	}

	//네이버 콜백 URL
	@RequestMapping( value = "/test2/", method = RequestMethod.GET)
	public String test1(Model model, HttpSession session, HttpServletRequest request) throws UnsupportedEncodingException {

			String clientId = "BaoDPkcOzl1XAtC3mfP0";//애플리케이션 클라이언트 아이디값";
			String clientSecret = "vapCZ66Qis";//애플리케이션 클라이언트 시크릿값";
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			String redirectURI = URLEncoder.encode("http://18.191.171.198:8080/test3/", "UTF-8");
			String apiURL;
			apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
			apiURL += "client_id=" + clientId;
			apiURL += "&client_secret=" + clientSecret;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&code=" + code;
			apiURL += "&state=" + state;

			// access_token 요청
			try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			
			if(responseCode==200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();

			//성공시에 
			if(responseCode==200) {
				System.out.println(res.toString());
				//토큰 받은거......... json 파싱..........
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParser.parse(res.toString());
				System.out.println("access_token="+jsonObj.get("access_token"));

				// 이제.. 회원 정보를 요청한다............
				String token = jsonObj.get("access_token").toString(); // 네이버 로그인 접근 토큰;
				String header = "Bearer " + token; // Bearer 다음에 공백 추가
				String apiURL2 = "https://openapi.naver.com/v1/nid/me";
				Map<String, String> requestHeaders = new HashMap<>();
				requestHeaders.put("Authorization", header);
				String responseBody = getAPI(apiURL2,requestHeaders,"GET");
			

				// 회원정보 받은거 제이슨파싱... 
				JSONParser jsonParser1 = new JSONParser();
				JSONObject jsonObj1 = (JSONObject) jsonParser1.parse(responseBody);
				System.err.println(responseBody);
				String resultcode = jsonObj1.get("resultcode").toString();
				JSONObject memberArray = (JSONObject) jsonObj1.get("response");
				if(resultcode.equals("00")){
					String naver_id = memberArray.get("id").toString();
					String email = memberArray.get("email").toString();
					String name = memberArray.get("name").toString();
					model.addAttribute("name", name);
				}else{
					model.addAttribute("name", "실패~");
				}
			}
			} catch (Exception e) {
				System.out.println(e);
			}

			return "login_test/callback";
	}

	// 구글 ~
	@RequestMapping( value = "/test3/", method = RequestMethod.GET)
	public String test3(Model model, HttpSession session, HttpServletRequest request) {
		model.addAttribute("name", request.getParameter("name"));
		return "login_test/callback";
	}


	// 카카옥 ~
	@RequestMapping( value = "/test4/", method = RequestMethod.GET)
	public String test4(Model model, HttpSession session, HttpServletRequest request) {

		String code = request.getParameter("code");
		String grant_type = "authorization_code"; 
		String client_id = "c7cd4504865091c72940c65a8e3b0d83";
		String redirect_uri = "http://18.191.171.198:8080/test4/";
		
		String apiURL;
		apiURL = "https://kauth.kakao.com/oauth/token?";
		apiURL += "grant_type=" + grant_type;
		apiURL += "&client_id=" + client_id;
		apiURL += "&redirect_uri=" + redirect_uri;
		apiURL += "&code=" + code;

		//System.out.println("apiURL="+apiURL);
		// access_token 요청
		try {
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("POST");
		int responseCode = con.getResponseCode();
		BufferedReader br;
		//System.out.print("responseCode="+responseCode);
			if(responseCode==200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
		String inputLine;
		StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
		br.close();
			//성공시에 
			if(responseCode==200) {
				//토큰 받은거......... json 파싱..........
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParser.parse(res.toString());
				//System.out.println("access_token="+jsonObj.get("access_token"));

				// 이제.. 회원 정보를 요청한다............
				String token = jsonObj.get("access_token").toString(); // 네이버 로그인 접근 토큰;
				String header = "Bearer " + token; // Bearer 다음에 공백 추가
				String apiURL2 = "https://kapi.kakao.com/v2/user/me";
				Map<String, String> requestHeaders = new HashMap<>();
				requestHeaders.put("Authorization", header);
				String responseBody = getAPI(apiURL2,requestHeaders,"GET");
			

				// 회원정보 받은거 json파싱... 
				JSONParser jsonParser1 = new JSONParser();
				JSONObject jsonObj1 = (JSONObject) jsonParser1.parse(responseBody);
			
				if(jsonObj1.get("id").toString()!=null){
					String id = jsonObj1.get("id").toString();
					JSONObject propertiesArray = (JSONObject) jsonObj1.get("properties");
					JSONObject kakaoAccountArray = (JSONObject) jsonObj1.get("kakao_account");
					String name = propertiesArray.get("nickname").toString();
					String email = kakaoAccountArray.get("email").toString();
					model.addAttribute("name", name);
				}else{
					model.addAttribute("name", "실패~");
				}
			}
		
		}catch (Exception e) {
			System.out.println(e);
		}
		return "login_test/callback";
	}


		//api 호출
		private static String getAPI(String apiUrl, Map<String, String> requestHeaders, String reqType){
		
			HttpURLConnection con = connect(apiUrl);
			try {
				con.setRequestMethod(reqType);
				for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
					con.setRequestProperty(header.getKey(), header.getValue());
				}
				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
					return readBody(con.getInputStream());
				} else { // 에러 발생
					return readBody(con.getErrorStream());
				}
			} catch (IOException e) {
				throw new RuntimeException("API 요청과 응답 실패", e);
			} finally {
				con.disconnect();
			}
		}
		//http 커넥션
		private static HttpURLConnection connect(String apiUrl){
			try {
				URL url = new URL(apiUrl);
				return (HttpURLConnection)url.openConnection();
			} catch (MalformedURLException e) {
				throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
			} catch (IOException e) {
				throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
			}
		}
	
		private static String readBody(InputStream body){
			InputStreamReader streamReader = new InputStreamReader(body);
	
			try (BufferedReader lineReader = new BufferedReader(streamReader)) {
				StringBuilder responseBody = new StringBuilder();
	
				String line;
				while ((line = lineReader.readLine()) != null) {
					responseBody.append(line);
				}
	
				return responseBody.toString();
			} catch (IOException e) {
				throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
			}
		}
		
	
}

<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta name="google-signin-scope" content="profile email">
    <meta name="google-signin-client_id" content="437026415311-r2091506152lav8qpg3fih8v23ci00c8.apps.googleusercontent.com">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <title>로그인</title>
  </head>
  <body>
  <!------------------네이버 로그인----------------------------------------------------->
  <%
    String clientId = "BaoDPkcOzl1XAtC3mfP0";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://18.191.171.198:8080/test2/", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
  <a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
   
  <!------------------구글 로그인----------------------------------------------------->
  <div class="g-signin2" data-onsuccess="onSignIn"></div>
  <form action="/test3/" id="glogin" method="GET">
    <input type="hidden" id="id" name="id" value="">
    <input type="hidden" id="name" name="name" value="">
    <input type="hidden" id="email" name="email" value="">
  </form>
  <script>

    function onSignIn(googleUser) {

      var profile = googleUser.getBasicProfile();
      var id_token = googleUser.getAuthResponse().id_token;
      var auth2 = gapi.auth2.getAuthInstance();
      auth2.disconnect();

      $("#id").val(profile.getId());
      $("#name").val(profile.getName());
      $("#email").val(profile.getEmail());

      $("#glogin").submit();
    }
    
  </script>
    <!------------------카카오 로그인----------------------------------------------------->
    <a href="https://kauth.kakao.com/oauth/authorize?client_id=c7cd4504865091c72940c65a8e3b0d83&redirect_uri=http://18.191.171.198:8080/test4/&response_type=code">
            카카오톡 로그인
        </a>
  </body>
</html>
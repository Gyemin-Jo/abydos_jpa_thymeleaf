package com.jgm.learning.common;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;

public class Utils {

    public	static	final	String[]	DENY_FILE_EXTENTIONS	= new String[] {"class","java","jsp","cgi","css","js","bin","bat","com","sh"};
    public 	static	final	String[] 	IMG_FILE_EXTENTIONS		= new String[] {"jpg", "jpeg", "png", "gif", "bmp"};

    private static final Logger logger = LogManager.getLogger(Utils.class);

    public static int stopWatchFormat2milisecond(String response_time) {
        int result = 0;
        int hour = Integer.parseInt(response_time.substring(0,2));
        int minute = Integer.parseInt(response_time.substring(3,5));
        int second = Integer.parseInt(response_time.substring(6,8));
        int mili = Integer.parseInt(response_time.substring(9));
//		logger.debug(hour);
//		logger.debug(minute);
//		logger.debug(second);
//		logger.debug(mili);
        return (hour*60*60*1000) + (minute*60*1000) + (second*1000) + mili;
    }

    public static int maxFromArray(List<Integer> array) {
        int result = Integer.MIN_VALUE;

        for(Integer i:array) {
            if(i>result) result = i;
        }

        return result;
    }

    public static int minFromArray(List<Integer> array) {
        int result = Integer.MAX_VALUE;

        for(Integer i:array) {
            if(i<result) result = i;
        }

        return result;
    }

    public static int sumFromArray(List<Integer> array) {
        int result = 0;

        for(Integer i:array) {
            result += i;
        }

        return result;
    }

    public static double avgFromArray(List<Integer> array) {
        double result = 0;

        for(Integer i:array) {
            result += i;
        }

        result = result / array.size();

        return result;
    }

    public static String nvl(final String org) {
        return nvl(org, "");
    }

    public static String nvl(final String org, final String replace) {
        return null == org || "".equals(org) ? replace : org;
    }

    public static int inArray(final String[] vals, final String value) {
        return inArray(vals, value, true);
    }

    public static int inArray(final String[] vals, final String value, final boolean isUpper) {
        if(null == vals || null == value) return -1;

        for(int i = 0, count = vals.length;i < count;i++) {
            if(null != vals[i]) {
                if(isUpper)
                    if(value.equalsIgnoreCase(vals[i])) return i;
                    else
                    if(value.equals(vals[i])) return i;
            }
        }

        return -1;
    }
    public static int inArray(final List<String> vals, final String value) {
        return inArray(vals, value, true);
    }
    public static int inArray(final List<String> vals, final String value, final boolean isUpper) {
        if(null == vals || null == value) return -1;

        for(int i = 0; i < vals.size(); i++) {
            if(null != vals.get(i)) {
                if(isUpper)
                    if(value.equalsIgnoreCase(vals.get(i))) return i;
                    else
                    if(value.equals(vals.get(i))) return i;
            }
        }

        return -1;
    }

    public static int inArrayLike(final List<String> vals, final String value) {
        if(null == vals || null == value) return -1;

        for(int i = 0; i < vals.size(); i++) {
            if(null != vals.get(i)) {
                if(value.startsWith(vals.get(i))) return i;
            }
        }

        return -1;
    }

    public static boolean inArray(final Object[] vals, final Object[] values) {
        if(null == vals || null == values) return false;

        String arrays	= implode(vals, ",");
        for(int i = 0, count = values.length;i < count;i++)
            if(null != values[i] && (-1 < arrays.indexOf(values[i] + ",") || arrays.endsWith(values[i].toString()))) return true;

        return false;
    }

    public static String implode(Object[] vals, String separator) {
        if(null == vals || 0 >= vals.length) return "";

        StringBuilder	sb	= new StringBuilder();

        for(int i = 0, count = vals.length;i < count;i++) {
            if(null == vals[i] || "".equals(vals[i])) continue;
            if(0 < i) sb.append(separator);
            sb.append(vals[i].toString());
        }

        return sb.toString();
    }

    public static boolean isEmpty(final Object obj) {
        return !isNotEmpty(obj);
    }

    public static boolean isNotEmpty(final Object obj) {
        if(null == obj) return false;
        else {
            if(obj instanceof String) return "".equals(obj) ? false : true;
            else if(obj instanceof List) return !((List<?>)obj).isEmpty();
            else if(obj instanceof Map) return !((Map<?,?>)obj).isEmpty();
//			else if(obj instanceof Object[]) return 0 == Array.getLength(obj) ? false : true;
            else if(obj instanceof Integer) return !(null == obj);
            else if(obj instanceof Long) return !(null == obj);
            else return false;
        }
    }

    public static String getTimeStampString(final String format) {
        return getTimeStampString(new Date(), format);
    }

    public static String getTimeStampString(final Date date) {
        return getTimeStampString(date, "yyyyMMddHHmmss");
    }

    public static String getTimeStampString(final Date date, final String format){
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
        return formatter.format(date);
    }

    public static String getTimeStampString(String date, final String format) {
        try {
            if(null == date || "".equals(date)) return "";

            Date	d	= null;
            date= date.replaceAll("-", "");

            switch(date.length()) {
                case 14: break;
                case 12: date += "00";			break;
                case 10: date += "0000";		break;
                case 8:	 date += "000000";		break;
                case 6:	 date += "01000000";	break;
                case 4:	 date += "0101000000";	break;
                default: return "";
            }

            java.text.SimpleDateFormat tmpFormat = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);

            if("".equals(date)) d = new Date();
            else {
                tmpFormat.setLenient(true);
                d = tmpFormat.parse(date);
            }

            return getTimeStampString(d, format);
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String generationSaveName() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Utils.getTimeStampString("yyyyMMdd_HHmmss_SSS");
    }

    public static boolean isDevMode(String devMode) {

        boolean result = false;
        try {
            if(!"".equals(devMode) && "true".equals(devMode)) {
                result = true;
            }
            return result;
        } catch(Exception e) {
            logger.error("isDevMode Error", e);
            return false;
        }
    }
    public static String safeCrossScript(String value) {
        value	= value.replaceAll("<script>(.*?)</script>", "");
        value	= value.replaceAll("expression\\((.*?)\\)", "");
        value	= value.replaceAll("vbscript", "");
        value	= value.replaceAll("onload(.*?)=", "");
        value	= value.replaceAll("eval\\((.*)\\)", "");
        value	= value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value	= value.replaceAll("[\\<][\\s]*form(.*)", "");
        value	= value.replaceAll("\"", "");
        value	= replace(value, "%", "");
//		value	= replace(value, "javascript", "");
        value	= replace(value, "script", "");
        value	= replace(value, "object", "");
        value	= replace(value, "applet", "");
        value	= replace(value, "embed", "");
        value	= replace(value, "iframe", "");

        return safeHTML(value);
    }

    public static String safeCrossScriptParameter(final String queryString, final String key, String value) {
        return safeCrossScript(value);
    }

    public static String replace(final String source, final String oldPart, final String newPart) {
        if(source == null) return "";
        if(oldPart == null || newPart == null) return source;
        StringBuffer stringbuffer = new StringBuffer();

        int last = 0;
        while(true){
            int start = source.indexOf(oldPart, last);
            if(start >= 0) {
                stringbuffer.append(source.substring(last, start));
                stringbuffer.append(newPart);
                last = start + oldPart.length();
            }
            else {
                stringbuffer.append(source.substring(last));
                return stringbuffer.toString();
            }
        }
    }
    public static String safeHTML(String str) {
//    	str	= replace(str, "&", "&amp;");
//    	str	= replace(str, "#", "&#35;");
//    	str	= replace(str, "<", "&lt;");
//    	str	= replace(str, ">", "&gt;");
//    	str	= replace(str, "(", "&#40;");
//    	str	= replace(str, ")", "&#41;");
//    	str	= replace(str, "\"", "&quot;");
//    	str	= replace(str, "\'", "&#39;");

        return str;
    }

    public static String sendMessage(final Model model, final String msg) {
        if(!isEmpty(msg)) model.addAttribute("message", msg);

        return "sendMessage";
    }

    public static String getRndNumber(int number) {
        String code = "";

        for (int i = 0; i < number; i++) {
            Random r = new Random();
            int d = r.nextInt(9);
            code += d;
        }

        return code;
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(int i=0; i<cookies.length; i++) {
                if(cookies[i].getName().equals(name))
                    return cookies[i].getValue();
            }
        }

        return "";
    }

    public static void setCookie(HttpServletResponse response, String name, String value){
        Cookie 	cookie = new Cookie(name, value);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void delCookie(HttpServletResponse response, String name) {
        Cookie 	cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getFileName(final String filename) {
        if(null == filename || "".equals(filename)) return "";

        return -1 < filename.lastIndexOf("/") ? filename.substring(filename.lastIndexOf("/") + 1) : filename;
    }

    public static String getFileExtention(final String filename) {
        if(null == filename || "".equals(filename)) return "";

        return -1 < filename.lastIndexOf(".") ? filename.substring(filename.lastIndexOf(".") + 1).toLowerCase() : "";
    }

    public static boolean isDirectory(final String path) {
        File dir	= new File(path);

        if(!dir.exists()) return dir.mkdirs();

        return true;
    }

    /**
     * 모든 HTML 태그를 제거하고 반환한다.
     *
     * @param html
     * @throws Exception
     */
    public static String removeTag(String html) throws Exception {
        return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }

    public static void preparedPath(String path){
        preparedPath(new File(path));
    }

    public static void preparedPath(File path){
        path.mkdirs();
    }

    // HTTP GET request
    public static void sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        logger.info("Sending 'GET' request to URL : " + url);
        logger.info("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result

    }

    // HTTP POST request
    public static void sendPost(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "ko-KR,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
//		logger.debug("\nSending 'POST' request to URL : " + url);
//		logger.debug("Post parameters : " + urlParameters);
//		logger.debug("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result

    }

    public static List<String> getLocalHostIp(HttpServletRequest request) throws SocketException {

        List<String> result = new ArrayList<String>();
        Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();

        while (nienum.hasMoreElements()) {
            NetworkInterface ni = nienum.nextElement();
            Enumeration<InetAddress> kk = ni.getInetAddresses();

            while (kk.hasMoreElements()) {
                InetAddress inetAddress = (InetAddress) kk.nextElement();
//				logger.debug(inetAddress.getHostName()+" : "+inetAddress.getHostAddress());
                result.add(inetAddress.getHostAddress());
            }
        }
        return result;
    }

    // 모바일체크
    public static boolean isMobile(final HttpServletRequest request) {
        String userAgent = (String)request.getHeader("User-Agent");
        String[] mobileOS = {"iPhone","iPod","Android","BlackBerry","Windows CE","Nokia","Webos","Opera Mini","SonyEricsson","Opera Mobi","IEMobile"};
        String mode = request.getParameter("mode");
        boolean isMobile = false;

        if(userAgent != null && !userAgent.equals("")) {
            for(int i = 0 ; i < mobileOS.length ; i++){
                if(userAgent.indexOf(mobileOS[i]) > -1) isMobile = true;
            }
        }

        if(isNotEmpty(mode)) {
            if("M".equals(mode)) {
                isMobile = true;
            } else if("P".equals(mode)) {
                isMobile = false;
            }
        }
        return isMobile;
    }

    public static String getClientIp(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (ip == null) ip = req.getRemoteAddr();
        return ip;
    }

    public static String base64URLEncode(final String value) {
        return new String(Base64.getUrlEncoder().encode(value.getBytes()));
    }

    public static String base64URLDecode(final String value) {
        return new String(Base64.getUrlDecoder().decode(value));
    }

    public static String nl2br(String contents) {
        if(isEmpty(contents)) {
            return null;
        }
        contents = contents.replaceAll("\r\n", "</br>");
        return contents;
    }

    public static void fileDownload(String realFilePath, String realFileName, HttpServletRequest request, HttpServletResponse response) throws Exception{
        File downloadFile = new File(realFilePath + File.separator + realFileName);
        if(downloadFile.exists() && downloadFile.isFile()) {
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setContentLength((int)downloadFile.length());
            OutputStream outputStream = null;
            FileInputStream inputStream = null;
            try {
                //브라우저 선택
                String browser = "";
                String header = request.getHeader("User-Agent");
                //신규추가된 indexof : Trident(IE11) 일반 MSIE로는 체크 안됨
                if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1){
                    browser = "ie";
                }
                //크롬일 경우
                else if (header.indexOf("Chrome") > -1){
                    browser = "chrome";
                }
                //오페라일경우
                else if (header.indexOf("Opera") > -1){
                    browser = "opera";
                }
                //사파리일 경우
                else if (header.indexOf("Apple") > -1){
                    browser = "sarari";
                } else {
                    browser = "firfox";
                }

                String prefix = "attachment;filename=";
                String encodedfilename = null;
                if ("ie".equals(browser)) {
                    encodedfilename = URLEncoder.encode(realFileName, "UTF-8").replaceAll("\\+", "%20");
                }else if ("chrome".equals(browser)) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < realFileName.length(); i++){
                        char c = realFileName.charAt(i);
                        if (c > '~') {
                            sb.append(URLEncoder.encode("" + c, "UTF-8"));
                        } else {
                            sb.append(c);
                        }
                    }
                    encodedfilename = sb.toString();
                }else {
                    encodedfilename = "\"" + new String(realFilePath.getBytes("UTF-8"), "8859_1") + "\"";
                }

                response.setHeader("Content-Disposition", prefix + encodedfilename);
                response.setHeader("Content-Transfer-Encoding", "binary");

                outputStream = response.getOutputStream();
                inputStream = new FileInputStream(downloadFile);

                FileCopyUtils.copy(inputStream, outputStream);

//                byte[] buffer = new byte[1024];
//                int length = 0;
//                while ((length = inputStream.read(buffer)) > 0) {
//					outputStream.write(buffer, 0, length);;
//				}

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null){
                        inputStream.close();
                    }
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e2) {}
            }

        }else {
            logger.error(realFileName + " 파일이 없습니다.");
        }
    }
}

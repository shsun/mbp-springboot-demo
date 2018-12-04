package base.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;

import com.google.common.base.Charsets;

/**
 * @author richard
 */
public class Servlets {

    /**
     * 设置让浏览器弹出下载对话框的Header.
     *
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {

        // 替换空格，否则firefox下有空格文件名会被截断,其他浏览器会将空格替换成+号
        fileName = fileName.trim().replaceAll(" ", "_");
        String encodedfileName;
        String agent = request.getHeader("User-Agent");
        boolean isMSIE = (agent != null && (agent.contains("MSIE") || agent.contains("Trident")));
        if (isMSIE) {
            encodedfileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            encodedfileName = new String(fileName.getBytes(), Charsets.ISO_8859_1);
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedfileName + "\"");

    }
}

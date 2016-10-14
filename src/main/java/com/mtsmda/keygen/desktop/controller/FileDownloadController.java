package com.mtsmda.keygen.desktop.controller;

import com.mtsmda.helper.ObjectHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dminzat on 9/28/2016.
 */
@Controller
@RequestMapping(value = "/download", method = RequestMethod.GET)
public class FileDownloadController {

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response){
        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("");
        System.out.println("realPath - " + realPath);

        String fullPath = realPath + "/downloads/output.html";
        File downloadFile = new File(fullPath);
        try (InputStream inputStream = new FileInputStream(downloadFile)) {
            String mimeType = servletContext.getMimeType(fullPath);
            if(ObjectHelper.objectIsNull(mimeType)){
                mimeType = "application/octet-stream";
            }
            System.out.println("Mime - " + mimeType);
            response.setContentType("application/octet-stream");
            response.setContentLength((int) downloadFile.length());

            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
            }
            System.out.println("OK");
        }catch (Exception e){
            System.out.println(e.getMessage() + " = " + e);
        }
    }

}
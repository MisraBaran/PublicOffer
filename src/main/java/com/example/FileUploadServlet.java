package com.example;
/* 
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;

@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Dosyayı al
        Part filePart = request.getPart("file");
        String distributionType = request.getParameter("distributionType");
        String fileName = filePart.getSubmittedFileName();
        
        // Dosyayı kaydet
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        filePart.write(uploadPath + File.separator + fileName);

        // Dağıtım türüne göre işlem yap
        if ("equal".equals(distributionType)) {
            // Eşit dağıtım işlemleri
        } else if ("proportional".equals(distributionType)) {
            // Oransal dağıtım işlemleri
        }

        response.getWriter().print("Dosya başarıyla yüklendi ve dağıtım gerçekleştirildi.");
    }
}
*/
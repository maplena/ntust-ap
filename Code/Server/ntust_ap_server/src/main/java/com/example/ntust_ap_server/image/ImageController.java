package com.example.ntust_ap_server.image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import com.example.ntust_ap_server.activity.ImageRequestHandler;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
// 2021.11.26 Boyan Start And End All
// 來源:https://ithelp.ithome.com.tw/articles/10196021
// 來源:https://mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/


@RestController
@RequestMapping(path = "api/v1/image/user") // 設定 URL path
public class ImageController {
    private static String UPLOADED_FOLDER = "image/user";

        @RequestMapping("/upload")
    public ResponseEntity<?> uploadFileMulti(@RequestParam("files") MultipartFile[] uploadfiles) {

        // 取得檔案名稱
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        Boolean legal = false;
        String []fileNameSplit = uploadedFileName.toLowerCase().split("\\.");
        if (fileNameSplit.length ==2 && (
                fileNameSplit[1].equals("png") ||
                        fileNameSplit[1].equals("jpg") ||
                        fileNameSplit[1].equals("jpeg"))) {

            legal = true;
        }
//        System.out.println(uploadedFileName);
//        System.out.println(fileNameSplit[0] + ":" + fileNameSplit[1]);
        if (legal==false){
            return new ResponseEntity("該 filename value 不合法", HttpStatus.OK);
        }


        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("請選擇檔案!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfiles));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("成功上傳 - "
                + uploadedFileName+ "/" +Paths.get(UPLOADED_FOLDER), HttpStatus.OK);

    }

    //將檔案儲存
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //繼續下一個檔案
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "/" + file.getOriginalFilename());
            Files.write(path, bytes);

        }
    }

    // 來源:https://itw01.com/882F3EK.html

    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName) throws UnsupportedEncodingException {
        File file = new File(Paths.get(UPLOADED_FOLDER).toString() +'/'+ fileName);
        Boolean legal = false;
        String fileNameSplit[] = fileName.toLowerCase(Locale.ROOT).split("\\.");
        if (fileNameSplit.length ==2 && (
                fileNameSplit[1].contains("png") ||
                        fileNameSplit[1].contains("jpg") ||
                        fileNameSplit[1].contains("jpeg"))) {
            legal = true;
        }

        if (legal!=true){
            return "該 filename value 不合法";
        }

        if(!file.exists()){
            return "下載檔案不存在";
        }
        response.reset();
//        response.setContentType("application/octet-stream; charset=utf-8");
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName,"utf-8"));


        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "下載失敗";
        }
        return "下載成功";
    }


    //-------------------------分隔線--------------------------------------------------
    //2022.01.26 CLH
    //測試看看可不可行
    @Resource
    private ImageRequestHandler imageRequestHandler;

    @GetMapping("/download2")
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
             throws ServletException, IOException {
         File file = new File("image\\image.png");
         httpServletRequest.setAttribute(ImageRequestHandler.ATTRIBUTE_FILE, file);
         imageRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
     }
}

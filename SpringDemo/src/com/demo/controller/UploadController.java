package com.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.model.UploadForm;

@Controller
public class UploadController {

    @RequestMapping("/click")
    public String index() {
        return "upload";
    }
    
    /**
	 * Upload single file using Spring Controller
     * @throws IOException 
	 */
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
		File serverFile;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
		
				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				System.out.println(">>>>>>" + rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();
		
				// Create the file on server
				serverFile = new File(dir.getAbsolutePath() + File.separator + file.getName());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
		
				//return file
				response.setContentType("application/txt");
		        response.addHeader("Content-Disposition", "attachment; filename="+serverFile.getName());
		        InputStream inputStream = new BufferedInputStream(new FileInputStream(serverFile));
		        FileCopyUtils.copy(inputStream, response.getOutputStream());
		        
		        return "You successfully uploaded file=" + "test";
		      
		        
			
				// get your file as InputStream
			    //InputStream is = new FileInputStream(serverFile);
			    // copy it to response's OutputStream
			   // org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			   // response.flushBuffer();
			} catch (Exception e) {
				return "You failed to upload " + "test" + " => " + e.getMessage();
			} 
		} else {
			return "You failed to upload " + "test"
					+ " because the file was empty.";
		}
	}

}

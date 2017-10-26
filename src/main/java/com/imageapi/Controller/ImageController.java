package com.imageapi.Controller;

 
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.imageapi.Util.StudentUtils;

import javax.mail.MessagingException;
import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.imageapi.EmailConfiguration.SpringMailSender;
import com.imageapi.Objects.Image;
import com.imageapi.Objects.User;
import com.imageapi.Service.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 
@RestController
@Api(value = "/ImageBuilding", description = "Image Building API")
@RequestMapping("image")
//@MultipartConfig(fileSizeThreshold = 20971520)
//@JsonAutoDetect(fieldVisibility = Visibility.ANY)
 
public class ImageController {
	 
	public static final String APPLICATION_JSON = "application/json";
    @Autowired(required = true)
 	private ImageService imageservice;
    @Autowired
	private SpringMailSender springMailSender;
 	
 	
    @ApiOperation(value = "sample")
    @RequestMapping(value = "/sampleimage", method = RequestMethod.GET)
	public String Test()  {
            Locale currentLocale = LocaleContextHolder.getLocale();

		springMailSender.sample();
	 
    	//springMailSender.sample();
 		return "Image controller API is Working..!....";
	}
    @RequestMapping(value = "/html", method = RequestMethod.GET)
    public String getExampleHTML(Model model) {
        model.addAttribute("title", "Baeldung");
        model.addAttribute("description", "<strong>Thymeleaf</strong> tutorial");
        return "inliningExample.html";
    }
    @RequestMapping(value = "/plain", method = RequestMethod.GET)
    public String getExamplePlain(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext()
          .getAuthentication().getName());
        model.addAttribute("students", StudentUtils.buildStudents());
        return "studentsList.txt";
    }
	@RequestMapping(value = "/image2", method = RequestMethod.GET)
	public String Testting() {
		  System.out.println("IN conTROLLING IN CALLING ");
		  System.out.println("imageservvice" +imageservice);

		return imageservice.sample();
	}
	 @ApiOperation(value = "image blob savaing for imagebuilding")
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ResponseEntity<Map<String, Object>> create(@RequestParam("file") MultipartFile file ) throws IOException {
		  
		System.out.println("image save  is Calling");
		byte[] bytes = file.getBytes();
		Map<String,Object> map = new HashMap<String,Object>();
 		Image image =  new Image();
		image.setImage(bytes);
		Image obj = imageservice.save(image);
		Map<String, Object> res = new HashMap<String, Object>();
		Object message = "User added Succesfully !..";
		res.put("message", message);
		res.put("statusCode", HttpStatus.OK.value());
		res.put("successful", true);
 		// logger.info("Succesfully Added Account" + obj);

		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);

	}
	/*@RequestMapping(value = "/mutliple_images", method = RequestMethod.POST )
	public ResponseEntity<Map<String, Object>> create(@RequestParam("file") List<MultipartFile> file ) throws IOException {
		  
		System.out.println("mutliple images service is Calling");
		for (MultipartFile files : file) {
		byte[] bytes = files.getBytes();
		Map<String,Object> map = new HashMap<String,Object>();
 		Image image =  new Image();
		image.setImage(bytes);
		Image obj = imageservice.save(image);
		
		}
		Map<String, Object> res = new HashMap<String, Object>();
		Object message = "User added Succesfully !..";
		res.put("message", message);
		res.put("statusCode", HttpStatus.OK.value());
		res.put("successful", true);
 		// logger.info("Succesfully Added Account" + obj);

		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);

	}*/
    @ApiOperation(value = "Retrieves images")
    @RequestMapping(value = "/view-images", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> viewAll() {

		List<Image> feteched = imageservice.viewAll();
		System.out.println(feteched);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("Successfully fetched images.!", feteched);
		res.put("statusCode", HttpStatus.OK.value());

		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);

	}
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> delete(@RequestParam int id) {

		Integer fecthed = imageservice.delete(id);
		System.out.println(fecthed);
		Map<String, Object> res = new HashMap<String, Object>();
		Object message = "Succesfully Image deleted!.";
		res.put("message", message);
		res.put("successful", true);
		res.put("statusCode", HttpStatus.OK.value());

		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);

	}
	
/*	images to the folders
*/
//	private static String UPLOADED_FOLDER = "src\\main\\java\\com\\ravi\\image\\controller\\images\\";
	private static String UPLOADED_FOLDER =  "E://ImageBuilding//";



	@RequestMapping(value = "/uploadsingle", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	@ResponseBody
	public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (file.isEmpty()) {
			map.put("status", false);
			map.put("data", null);
			return ResponseEntity.ok().body(map);
		}
		List<String> result = saveImagestoFolder(Arrays.asList(file));
		map.put("status", true);
		map.put("data", result.get(0));
		return ResponseEntity.ok().body(map);
	}
	@RequestMapping(value = "/uploadmultiple", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	@ResponseBody
	public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") List<MultipartFile> files) {
		
		 System.out.println("Multiple images is calling to the folders<>");
		Map<String, Object> map = new HashMap<String, Object>();
		if (files.size() == 0) {
			map.put("status", false);
			map.put("data", null);
			return ResponseEntity.ok().body(map);
		}
		List<String> result = saveImagestoFolder(files);
		map.put("status", true);
		map.put("data", result);
		return ResponseEntity.ok().body(map);
	}
	private List<String> saveImagestoFolder(List<MultipartFile> files) {
		int count = 0;
		List<String> list = new ArrayList<String>();
		for (MultipartFile file : files) {
			try {
				byte[] bytes = file.getBytes();
				String date = getTimeInMillis();
				Path path = Paths.get(UPLOADED_FOLDER + date + ".png");
				Files.write(path, bytes);
				count++;
				list.add("ImageBuilding\\" + date + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (count == files.size()) {
			return list;
		} else {
			return list;
		}
	}
	private synchronized String getTimeInMillis() {
		return String.valueOf(new Date().getTime());
	}
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable String id) throws IOException {
		String url = "ImageBuilding\\" + id + ".png";
		System.out.println("url "+url);
		InputStream in = getClass().getResourceAsStream(url);
		return IOUtils.toByteArray(in);
	}
	

}

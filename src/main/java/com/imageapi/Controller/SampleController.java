package com.imageapi.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imageapi.Objects.User;
import com.imageapi.Service.UserService;
import com.roomsbooking.Controller.RoomController;

@RestController
@RequestMapping("user")
public class SampleController {
	
	public static final String APPLICATION_JSON = "application/json";
	
    @Autowired
 	private UserService userservice;
 	
 	
  
	@RequestMapping(value = "/usersample", method = RequestMethod.GET)
	public String Test() {

		return "Sample Controller Service <><> is caalling .!....";
	}
	 @RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> save(@RequestBody String data) {
		System.out.println("userservice<><><><><>>"+userservice);
		System.out.println("sample calling <><");
		User obj = new User();
		System.out.println("add is calling ");
		try {
			obj = new ObjectMapper().readValue(data, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
  		obj = userservice.save(obj);
		System.out.println("obj " + obj);

		System.out.println("User add is Calling");
		Map<String, Object> res = new HashMap<String, Object>();
		Object message = "User added Succesfully !..";
		res.put("message", message);
		res.put("statusCode", HttpStatus.OK.value());
		res.put("successful", true);
		res.put("Successful", obj);
		// logger.info("Succesfully Added Account" + obj);

		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);

		//return userservice.save(user);
	}
	 
	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> create(@RequestBody String data,
			@RequestParam("image") MultipartFile image) {
		

		User obj = new User();
		System.out.println("add is calling ");
		try {
			obj = new ObjectMapper().readValue(data, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   // obj.setImage(fileuploadservice.fileupload(image, "User"));
		obj.setImage(userservice.fileupload(image, "User"));
		obj = userservice.create(obj);
		System.out.println("obj " + obj);

		System.out.println("User add is Calling");
		Map<String, Object> res = new HashMap<String, Object>();
		Object message = "User added Succesfully !..";
		res.put("message", message);
		res.put("statusCode", HttpStatus.OK.value());
		res.put("successful", true);
		res.put("Successful", obj);
		// logger.info("Succesfully Added Account" + obj);

		return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);

	}*/
}

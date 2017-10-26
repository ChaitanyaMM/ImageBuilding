package com.imageapi.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imageapi.Dao.ImageDao;
import com.imageapi.DaoImpl.ImageDaoImpl;
import com.imageapi.Objects.Image;
import com.imageapi.Service.ImageService;


@Service
@Transactional

public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageDao imagedao;

   public Image save(Image image) {
		// TODO Auto-generated method stub
		return imagedao.save(image);
	} 

	public String sample() {
		  System.out.println("IN  service IMpl is  CALLING ");

 		return imagedao.sample();
	}

	public List<Image> viewAll() {
		
 		return imagedao.viewAll();

	}

	public Integer delete(int id) {
 		return imagedao.delete(id);

	}
	

}

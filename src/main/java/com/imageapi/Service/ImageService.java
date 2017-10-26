package com.imageapi.Service;

import java.util.List;

import com.imageapi.Objects.Image;

public interface ImageService {

	public Image save(Image image);

	public String sample();

	public List<Image> viewAll();

	public Integer delete(int id);

}

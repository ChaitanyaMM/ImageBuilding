package com.imageapi.Dao;

import java.util.List;

import com.imageapi.Objects.Image;

public interface ImageDao   {

	public Image save(Image image);

    public String sample();

	public List<Image> viewAll();

	public Integer delete(int id);

}

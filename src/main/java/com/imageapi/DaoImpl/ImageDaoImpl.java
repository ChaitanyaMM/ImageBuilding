package com.imageapi.DaoImpl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.imageapi.Dao.ImageDao;
import com.imageapi.Objects.Image;

@Repository
public class ImageDaoImpl implements ImageDao {

	private static final String COLLECTION_NAME = "image";
	   @Autowired
		private DataSource dataSource;
		private JdbcTemplate jdbcTemplate;

		@Autowired
		public void setDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
			this.jdbcTemplate = new JdbcTemplate(dataSource);

		}

		public DataSource getDataSource() {
			return dataSource;
		}

		public JdbcTemplate getJdbcTemplate() {
			return jdbcTemplate;
		}

		public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}
	public Image save(Image image) {
		 System.out.println("imaage insert dao impl  is calling ");
		String sql = "INSERT INTO images (image) values (?)";
		System.out.println("sql<><><"+sql);
		jdbcTemplate.update(sql, image.getImage());
		return image;
	}

	public String sample() {
		// TODO Auto-generated method stub
		return "Sample is Calling !!!";
	}

	public List<Image> viewAll() {
		String sql = "select * from images";
		List<Image> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Image.class));
		System.out.println("total" + list.toString().length());
		return list;
	}

	public Integer delete(int id) {
		String sql = "delete from images where id = ?";
		jdbcTemplate.update(sql, id);

		System.out.println("Deleted image with ID = " + id);
		return id;

	}

	 
}
 
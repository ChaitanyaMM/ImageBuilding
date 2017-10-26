package com.imageapi.DaoImpl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.imageapi.Dao.UserDao;
import com.imageapi.Objects.Image;
import com.imageapi.Objects.User;

@Repository
public class UserDaoImpl implements UserDao {
	 private static final String COLLECTION_NAME = "user";
	   // @Autowired(required=true)
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
		 
	public String sample() {
 		return "Sample is Calling !!!";
	}

	public User save(User sample) {
		System.out.println("hai dao");
		String sql= "INSERT INTO user (name,email) values (?,?)";
		System.out.println("sql priniinn<><><"+sql +sample);
		jdbcTemplate.update(sql, sample.getName(),sample.getEmail());
        return sample;
	}
	
 
	 
 

}

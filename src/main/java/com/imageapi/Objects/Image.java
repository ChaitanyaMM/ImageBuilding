package com.imageapi.Objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Image implements Serializable{
	  
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)	
	    private long id;
		@Lob
		private byte[] image;
		@Column(name="created_date")
		private Date createdDate = new Date();
		 
		public Image() {
			super();
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public byte[] getImage() {
			return image;
		}
		public void setImage(byte[] image) {
			this.image = image;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
}
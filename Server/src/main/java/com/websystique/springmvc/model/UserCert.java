package com.websystique.springmvc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="USER_CERT")
public class UserCert {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@NotEmpty
	@Column(name="HASH_VALUE", unique=true, nullable=false)
	private String hash_value;
	
	@NotEmpty
	@Column(name="FILE_NAME", nullable=false)
	private String file_name;

	@Column(name="USER_ID", nullable=false)
	private Integer user_id;

	@Column(name="USER_MAC")
	private String user_mac;
	
	@Column(name="UPLOAD_TIME",columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date upload_time;
	
	@Column(name="LAST_MODIFIED_TIME", columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_modified_time;

	@Column(name="CREATE_TIME", columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_time;
	
	@NotEmpty
	@Column(name="FILE_TYPE", nullable=false)
	private String file_type;
	
	@PrePersist
	public void createdAt() {
		this.user_mac = "66:66:66:66:66:66";
	    this.upload_time = this.last_modified_time = this.create_time = new Date();
	}
	
	public UserCert() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHash_value() {
		return hash_value;
	}

	public void setHash_value(String hash_value) {
		this.hash_value = hash_value;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_mac() {
		return user_mac;
	}

	public void setUser_mac(String user_mac) {
		this.user_mac = user_mac;
	}

	public Date getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(Date upload_time) {
		this.upload_time = upload_time;
	}

	public Date getLast_modified_time() {
		return last_modified_time;
	}

	public void setLast_modified_time(Date last_modified_time) {
		this.last_modified_time = last_modified_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	
	
	
}

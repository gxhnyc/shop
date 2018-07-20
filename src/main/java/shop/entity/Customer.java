package shop.entity;


import java.util.Date;

import javax.validation.constraints.Pattern;


public class Customer {
	  private Long c_id;//用户id
	  @Pattern(regexp = "[0-9a-Z-_] {2,64}",message="2~64位，仅限数字字母、连字符-、下划线")
      private String username;//用户名
	  @Pattern(regexp = "[\\p{Digit}\\p{Alpha}\\p{Punct}] {6,64}",message="6~64位，仅限数字字母、英文标点")
      private String password;//密码
      private Date lastdate;//最后一次登录时间
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastdate() {
		return lastdate;
	}
	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	
      
      
}

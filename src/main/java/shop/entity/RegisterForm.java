
package shop.entity;

import javax.validation.constraints.Pattern;

public class RegisterForm {
		private String user_id;
	 	@Pattern(regexp = "[0-9a-zA-Z-_]{2,64}", message = "2~64位，仅限数字字母、连字符-、下划线_")
	    private String username;
	    
	    @Pattern(regexp = "[\\p{Digit}\\p{Alpha}\\p{Punct}]{6,64}", message = "6~64位，仅限数字字母、英文标点")
	    private String password;
	    
	    private String Email;
	    
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
		public String getEmail() {
			return Email;
		}
		public void setEmail(String email) {
			Email = email;
		}
		public String getUser_id() {
			return user_id;
		}
		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}
}

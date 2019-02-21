/**
 * 
 */
package test.service;


import test.bean.User;

/**
 * UserService.java
 * 
 * @author PLF
 * @date 2019年2月13日
 *
 */
public class UserService {

	private User user;

	public String getUser(int id) {
		
		if (id == user.getId()) {
			return "user:" + user.getName();
		}

		return "no user of " + id;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}

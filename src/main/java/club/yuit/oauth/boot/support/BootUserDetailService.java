package club.yuit.oauth.boot.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import club.yuit.oauth.boot.entity.User;
import club.yuit.oauth.boot.service.IUserService;

/**
 * @author yuit
 * @create time 2018/10/11  9:13
 * @description
 * @modify by
 * @modify time
 **/
@Component
public final class BootUserDetailService implements UserDetailsService {

	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userService.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		user.setPassword("$2a$10$ZY/IjJ9YdJw3XPoJkGl3AOz7IfF10eh/S9yB8IeojLNmsyGW9qOnK");
		user.setIsEnable(true);
		user.setIsLocked(false);

		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);
		user.setAuthorities(authorities);

		return user;
	}
}

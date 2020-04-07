package zju.edu.qyTest.consumer.security;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zju.edu.qyTest.common.pojo.Users;
import zju.edu.qyTest.common.service.UsersService;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录认证信息查询
 * @author Louis
 * @date Jan 14, 2019
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference(version = "1.0.0")
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        String permissions = usersService.findPermissions(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new GrantedAuthorityImpl(permissions));
        return new JwtUserDetails(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
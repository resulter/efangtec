package com.efangtec.passport.service.impl;

import com.efangtec.common.utils.SnowflakeIdWorker;
import com.efangtec.passport.filter.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserServiceImpl implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(JwtUserServiceImpl.class);
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> roles = new ArrayList<>();
       /* UserInfo param = new UserInfo();
        param.setIdentifier(username);
        UserInfo userInfo = userInfoService.findByNamedParam(param);
        if (ObjectUtils.isEmpty(userInfo)) {
            throw new BadCredentialsException("用户名不正确");
        } else {
            userInfo.setCredential(userInfo.getCredential());
        }
        userInfo.setLastTime(new Date());*/
        //userInfoService.update(userInfo);
        JwtUser jwtUser = null;

       /* userInfo.setCredential(userInfo.getCredential());
        if(Constants.USER_PATIENT.equals(userInfo.getIdentityType())) {
            roles.add(new SimpleGrantedAuthority("ROLE_PATIENT"));
        }else if(Constants.USER_MEDICINE.equals(userInfo.getIdentityType())) {
            roles.add(new SimpleGrantedAuthority("ROLE_MEDICINE"));
        }else if(Constants.USER_FOUNDATION.equals(userInfo.getIdentityType())) {
            roles.add(new SimpleGrantedAuthority("ROLE_PLATFORM"));
        } else if (Constants.USER_VOLUNTEER.equals(userInfo.getIdentityType())) {
            roles.add(new SimpleGrantedAuthority("ROLE_VOLUNTEER"));
        } else if (Constants.USER_COMPANY.equals(userInfo.getIdentityType())) {
            roles.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
        }*/
        jwtUser = new JwtUser(0L,0L,"","" , roles);

        return jwtUser;
    }
}

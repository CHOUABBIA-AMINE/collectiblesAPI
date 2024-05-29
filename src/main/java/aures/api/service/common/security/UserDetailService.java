/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: UserDetailService
 *	@CreatedOn	: 05-27-2024
 *
 *	@Type		: Class
 *	@Layaer		: Service
 *	@Goal		: Security
 *
 **/

package aures.api.service.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import aures.api.repository.security.UserRepository;

@Component
public class UserDetailService implements UserDetailsService {
    
	@Autowired
	private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
    }
}
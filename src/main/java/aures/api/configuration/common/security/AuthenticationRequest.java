/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: AuthenticationRequest
 *	@CreatedOn	: 06-26-2023
 *
 *	@Type		: Class
 *	@Layaer		: Configuration
 *	@Goal		: Security
 *
 **/

package aures.api.configuration.common.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {
    private String username;
    private String password;
}
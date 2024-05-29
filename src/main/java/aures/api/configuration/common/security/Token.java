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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Token {
    private String value;
    private String type;
}
/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: FileRepository
 *	@CreatedOn	: 05-27-2024
 *
 *	@Type		: Interface
 *	@Layaer		: Repository
 *	@Goal		: Utility
 *
 **/

package aures.api.repository.utility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import aures.api.model.utility.File;

@RepositoryRestResource(collectionResourceRel = "file", path = "file")
public interface FileRepository extends JpaRepository<File, Long> {
    
}
/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: FileService
 *	@CreatedOn	: 01-09-2023
 *
 *	@Type		: Class
 *	@Layaer		: Service
 *	@Goal		: Utility
 *
 **/

package aures.api.service.common.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import aures.api.model.utility.File;
import aures.api.repository.utility.FileRepository;


@Component
public class FileService {
    
	private static String rootPath = "F:\\AURES\\FileStore";
	
	@Autowired
    private FileRepository fileRepository;
	
	public File store(MultipartFile file) throws IOException {
		if(file.isEmpty()) {
			
			return null;
			
		}else{
			File dbFile = new File();
	    	dbFile.setExtension(this.getExtension(file.getOriginalFilename()));
	    	dbFile.setSize(file.getSize());
	    	dbFile.setPath("");
	    	dbFile.setFileType(file.getContentType());
	    	dbFile = fileRepository.save(dbFile);
			Path path = Path.of(rootPath).toAbsolutePath().normalize().resolve(dbFile.getId() + dbFile.getExtension());
			dbFile.setPath(path.toString());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			dbFile = fileRepository.save(dbFile);
			return dbFile;
		}
	}
	
	public String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}
	
	public Resource getFile(String filePath) throws IOException {
		Path path = Path.of(filePath).normalize();
		return new UrlResource(path.toUri());
	}
	
	public Resource load(Long id) throws IOException {
		
		Optional<File> dbFile = fileRepository.findById(id);
		if(dbFile.isPresent()) {
			Path filePath = Path.of(rootPath).toAbsolutePath().normalize().resolve(dbFile.get().getId() + dbFile.get().getExtension()).normalize();
            return new UrlResource(filePath.toUri());
		}else {
			return null;
		}
	}
	
	public Boolean deleteFile(String filePath) throws IOException {
		return Files.deleteIfExists(Paths.get(filePath));
	}
	
}
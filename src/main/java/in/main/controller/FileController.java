package in.main.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.main.service.FileService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/file/")
public class FileController {

	private final FileService fileService;
	
	public FileController(FileService fileService) {
		super();
		this.fileService = fileService;
	}
	
	@Value("${project.poster}")
	private String path;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException{
		String fileName=fileService.uploadFile(path, file);
		return ResponseEntity.ok("file uploded " + fileName);
	}

	@GetMapping("/{fileName}")
	public void getResourceFile(@PathVariable String fileName,HttpServletResponse response) throws IOException {
		InputStream sourceFile=fileService.getResourceFile(path, fileName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(sourceFile,response.getOutputStream());
	}
}	

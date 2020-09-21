package com.smartmenu.common.file;

import com.smartmenu.client.file.FileResponse;
import com.smartmenu.common.basemodel.service.ServiceResult;
import com.smartmenu.common.service.SftpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/file")
public class FileController {
	final private SftpService service;

	@PostMapping(value = "/upload")
	public ResponseEntity<FileResponse> uploadFile(@RequestParam("file0") MultipartFile file) {
		ServiceResult<String> serviceResult = service.uploadFile(file);
		return new ResponseEntity<>(new FileResponse(serviceResult.getValue()), serviceResult.getHttpStatus());
	}

	@PostMapping(value = "/upload-all")
	public ResponseEntity<Set<String>> uploadAllFiles(@RequestBody Set<MultipartFile> files) {
		ServiceResult<Set<String>> serviceResult = service.uploadAllFiles(files);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@DeleteMapping(value = "/delete/{fileName}")
	public ResponseEntity<Boolean> deleteFile(@PathVariable String fileName) {
		ServiceResult<Boolean> serviceResult = service.deleteFile(fileName);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping(value = "/delete-all")
	public ResponseEntity<Set<String>> deleteAllFiles(@RequestBody Set<String> fileNames) {
		ServiceResult<Set<String>> serviceResult = service.deleteAllFiles(fileNames);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}

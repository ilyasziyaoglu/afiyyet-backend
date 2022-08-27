package com.afiyyet.common.file;

import com.afiyyet.client.file.FileResponse;
import com.afiyyet.common.basemodel.service.ServiceResult;
import com.afiyyet.common.service.FileService;
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
	final private FileService service;

	@PostMapping(value = "/upload")
	public ResponseEntity<ServiceResult<FileResponse>> uploadFile(@RequestParam("file0") MultipartFile file) {
		ServiceResult<FileResponse> serviceResult = service.uploadFileLocal(file);
		return new ResponseEntity<>(serviceResult, serviceResult.getHttpStatus());
	}

	@PostMapping(value = "/upload-all")
	public ResponseEntity<Set<String>> uploadAllFiles(@RequestBody Set<MultipartFile> files) {
		ServiceResult<Set<String>> serviceResult = service.uploadAllFiles(files);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@DeleteMapping(value = "/delete/{fileName}")
	public ResponseEntity<Boolean> deleteFile(@PathVariable String fileName) {
		ServiceResult<Boolean> serviceResult = service.deleteFileLocal(fileName);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}

	@PostMapping(value = "/delete-all")
	public ResponseEntity<Set<String>> deleteAllFiles(@RequestBody Set<String> fileNames) {
		ServiceResult<Set<String>> serviceResult = service.deleteAllFiles(fileNames);
		return new ResponseEntity<>(serviceResult.getValue(), serviceResult.getHttpStatus());
	}
}

package com.afiyyet.client.file;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */


@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
	private String fileName;
	private Set<String> fileNames;

	public FileResponse(String fileName) {
		this.fileName = fileName;
	}

	public FileResponse(Set<String> fileNames) {
		this.fileNames = fileNames;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Set<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(Set<String> fileNames) {
		this.fileNames = fileNames;
	}
}

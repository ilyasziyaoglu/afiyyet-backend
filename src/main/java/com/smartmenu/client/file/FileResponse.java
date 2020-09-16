package com.smartmenu.client.file;

import lombok.Data;

import java.util.Set;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Data
public class FileResponse {
	private String fileName;
	private Set<String> fileNames;

	public FileResponse(String fileName) {
		this.fileName = fileName;
	}

	public FileResponse(Set<String> fileNames) {
		this.fileNames = fileNames;
	}
}

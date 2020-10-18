package com.smartmenu.common;

import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.*;

@Data
public class MailEvent implements Serializable {

	private String subject;

	private Set<String> to;

	private String from;

	private String vm;

	private Map<String, Object> dataModel = new HashMap<>();

	private List<File> files = new ArrayList<>();

	private String fileName;

	private Set<String> bcc;

	private Set<String> cc;
}

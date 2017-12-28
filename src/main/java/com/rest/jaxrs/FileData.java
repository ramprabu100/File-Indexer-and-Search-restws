package com.rest.jaxrs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FileList")
public class FileData {
	String fileName;
	String filePath;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public FileData(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}
	public FileData() {
		this.fileName = "";
		this.filePath = "";
	}
	
}

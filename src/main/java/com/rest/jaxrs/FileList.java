package com.rest.jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FileList")
public class FileList {
	@XmlElement(name = "FileData")
	List<FileData> FileData=new ArrayList<FileData>();
	FileList(){
		
	}
	public void addFileData(FileData fileData) {
		FileData.add(fileData);
	}
	
}

package com.rest.jaxrs;

import javax.ws.rs.core.Response;

public class FileSearch_usingLucene implements FileSearchService {

	@Override
	public FileList searchFile(String query) {
		FileList temp=null;
		/*temp.addFileData(new FileData("ram", "locationa"));
		temp.addFileData(new FileData("ram", "locationb"));*/
		Lucene_Searcher search_Instance=new Lucene_Searcher();
		try {
			System.out.println("the keyword to be searched"+ query);
			temp=search_Instance.Search(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}

	/*@Override
	public Response startIndexing() {
		// TODO Auto-generated method stub
		new Lucene_Searcher();
		response.
		return ;
	}*/

}

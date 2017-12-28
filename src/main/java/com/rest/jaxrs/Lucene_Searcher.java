package com.rest.jaxrs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;

public class Lucene_Searcher {
	int number_of_file_found;
	IndexReader reader;
	IndexSearcher searcher;
	Properties search_config=new Properties();
	InputStream input_properties;
	String index_file_location;
	FileList temp=new FileList();
	Lucene_Searcher() {
		new Lucene_Indexer();
		try {;
			
			input_properties=this.getClass().getClassLoader().getResourceAsStream("/file_searcher-config.properties");
			//					new FileInputStream("file_searcher-config.properties");
			search_config.load(input_properties);
			index_file_location=search_config.getProperty("index_file_location");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void searchIndex(String indexDir, String queryStr, int maxHits) throws Exception {        
        //Directory directory = FSDirectory.open(indexDir);
        
        //Analyzer analyser=new StandardAnalyzer();
    	  reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexDir)));
    	  searcher = new IndexSearcher(reader);
    	  Analyzer analyzer = new StandardAnalyzer();
    	  QueryBuilder parser=new QueryBuilder(analyzer);
    	  Query query = parser.createPhraseQuery("SEARCH_KEYS",queryStr);  
    	  System.out.println(query);
    	  
    	  TopDocs topDocs = searcher.search(query,maxHits);       
    	  ScoreDoc[] hits = topDocs.scoreDocs;
    	  number_of_file_found=hits.length;
    	  for (int i = 0; i < hits.length; i++) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println(d.get("FILE_Name")+d.get("FILE_PATH"));
            temp.addFileData(new FileData(d.get("FILE_Name"), d.get("FILE_PATH")));
    	  }
    	  System.out.println("Found " + hits.length);        
    }
	FileList Search(String qurey) throws Exception {
		searchIndex(index_file_location, qurey, 10000000);
		return temp;
	}
	/*public static void main(String args[]) {
		Lucene_Searcher i=new Lucene_Searcher();
		try {
			i.Search("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}

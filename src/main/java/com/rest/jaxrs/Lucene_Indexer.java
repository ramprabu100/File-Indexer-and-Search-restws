package com.rest.jaxrs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Lucene_Indexer {
	List<String> directories_to_be_index;
	IndexWriter index_writer;
	IndexWriterConfig index_writer_config;
	Directory directory_to_be_indexed ;
	Path indexDirectoryPath;   
	Directory index_directory;
	Properties search_config;
	InputStream input;
	OutputStream output;
	
	public Lucene_Indexer() {
		//directories_to_be_index=new ArrayList<String>();
		//Collections.addAll(directories_to_be_index, paths.split(";"));
		
 		index_writer_config=new IndexWriterConfig(new StandardAnalyzer());
 		index_writer_config.setOpenMode(OpenMode.CREATE);
 		search_config = new Properties();
 		try {
			input = this.getClass().getClassLoader().getResourceAsStream("/file_searcher-config.properties");
 			//index_directory=prop.getProperty("directories_to_be_searched");
			
			search_config.load(input);
			index_directory=FSDirectory.open(Paths.get(search_config.getProperty("index_file_location")));
			index_writer=new IndexWriter(index_directory,index_writer_config);
			
			directories_to_be_index=new ArrayList<String>();
			Collections.addAll(directories_to_be_index, search_config.getProperty("directories_to_be_searched").split(";"));
			startIndexing();
			input.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}	
	}
	public void startIndexing() {
		for(String directory:directories_to_be_index){
			System.out.println(directory);
			indexDirectoryPath=Paths.get(directory);
			 //directory_to_be_indexed = FSDirectory.open(indexDirectoryPath);
			indexDirectory( index_writer,new File(directory));
			
			try {
				
				index_writer.commit();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	private Document getDocument(File file)  {
		   Document document = new Document();
		   FieldType myFieldType = new FieldType();
		   myFieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);  // tell indexer to store image token's positions, offsets, and payloads
		   myFieldType.setStored(true);
		   //smyFieldType.setTokenized(true);
		   Field filePathField;
		try {
			filePathField = new StringField("FILE_PATH",file.getCanonicalPath(),Field.Store.YES);
			Field search_keys=new Field("SEARCH_KEYS", file.getName().toLowerCase().replace('.',' '),myFieldType);
			   Field fileName = new StringField("FILE_Name", file.getName(),Field.Store.YES);
			   document.add(search_keys);
			   document.add(filePathField);
			   document.add(fileName);
			
			   return document;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
		   
		}
	private void indexDirectory(IndexWriter indexWriter, File dataDir) {
		if(dataDir.exists()) {
        File[] files = dataDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() ) {
            	
    	       
                indexDirectory(indexWriter, f);
            }
            else {
                try {
					indexFileWithIndexWriter(indexWriter, f);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
		}
		else {
			System.out.println("directory doesnt exist not indexed");
		}
    }
	private void indexFileWithIndexWriter(IndexWriter indexWriter, File f)  {
        if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
            return;
        }           
        System.out.println("Indexing file:... " + f.getName()); 
        Document doc =  getDocument(f);    
        try {indexWriter.updateDocument(new Term("SEARCH_KEYS"), doc);
			//indexWriter.addDocument(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
		
	
	
}

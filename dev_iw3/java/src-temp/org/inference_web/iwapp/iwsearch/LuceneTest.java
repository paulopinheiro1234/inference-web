package org.inference_web.iwapp.iwsearch;

import java.io.IOException;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;

public class LuceneTest {

        public static void main(String[] args) throws IOException {
            RAMDirectory directory = new RAMDirectory();
            IndexWriter writer = 
              new IndexWriter(directory, new SimpleAnalyzer(), true);
                
            Document doc = new Document(); 
            doc.add(new Field("partnum", "Q36", Field.Store.YES, Field.Index.UN_TOKENIZED));   
            doc.add(new Field("description", "Illidium Space Modulator", Field.Store.YES, Field.Index.TOKENIZED)); 
            writer.addDocument(doc); 
            writer.close();

            IndexSearcher searcher = new IndexSearcher(directory);
            Query query = new TermQuery(new Term("partnum", "Q36"));   
            TopDocs rs = searcher.search(query, null, 10);
            System.out.println(rs.totalHits);

            Document firstHit = searcher.doc(rs.scoreDocs[0].doc);
            System.out.println(firstHit.getField("partnum").name());
        }
}


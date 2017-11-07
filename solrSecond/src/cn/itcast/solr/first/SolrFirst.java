package cn.itcast.solr.first;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Test;

public class SolrFirst {
	
	@Test
	public void addDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", 3);
		document.addField("title", "文档3修改");
		document.addField("content", "文档3的修改内容");
		solrServer.add(document);
		solrServer.commit();
	}
	
	@Test
	public void testDeleteById() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		solrServer.deleteById("3");
		solrServer.commit();
	}
	
	@Test
	public void testDeleteByQuery() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		solrServer.deleteByQuery("title:修改");
		solrServer.commit();
	}
	
	@Test
	public void testQueryDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", "*:*");
		solrQuery.set("start", "0");
		solrQuery.set("rows", "10");
		QueryResponse queryResponse = solrServer.query(solrQuery);
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		System.out.println("查询总记录数:"+solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("title"));
			System.out.println(solrDocument.get("content"));
		}
		
		
	}

}

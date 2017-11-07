package cn.itcast.solr.second;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrSecond {
	
	@Test
	public void testQueryIndex() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("台灯");
		solrQuery.addFilterQuery("product_catalog_name:时尚卫浴");
		solrQuery.setSort("product_price", ORDER.desc);
		solrQuery.setStart(0);
		solrQuery.setRows(10);
		solrQuery.set("df", "product_keywords");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_name");
		solrQuery.setHighlightSimplePre("<font>");
		solrQuery.setHighlightSimplePost("</font>");
		QueryResponse queryResponse = solrServer.query(solrQuery);
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		SolrDocumentList documentList = queryResponse.getResults();
		System.out.println("总记录数:"+documentList.getNumFound());
		for (SolrDocument solrDocument : documentList) {
			System.out.println(solrDocument.get("id"));
			String product_name = "";
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			if(list != null && list.size() > 0 ){
				product_name = list.get(0);
			}else{
				product_name = solrDocument.get("product_name").toString();
			}
			System.out.println(product_name);
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_picture"));
			System.out.println(solrDocument.get("product_catalog_name"));
		}
	}

}

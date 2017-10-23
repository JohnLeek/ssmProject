import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class testSolrJ {
	@Test
	public void testSolrJ() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");
		//创建一个文档对象 
		SolrInputDocument solrDocument = new SolrInputDocument();
		solrDocument.addField("id", "solr02");
		solrDocument.addField("item_title", "测试商品");
		solrDocument.addField("item_sell_point", "卖点");
		//添加到索引库
		solrServer.add(solrDocument);
		//提交
		solrServer.commit();
	}
	//查询
	@Test
	public void testQuery() throws SolrServerException{
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.133:8080/solr");
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");//查询所有
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		for(SolrDocument list : solrDocumentList){
			System.out.println(list.get("id"));
			System.out.println(list.get("item_title"));
			System.out.println(list.get("item_sell_point"));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

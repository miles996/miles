package com.lsf.elasticsearch.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsf.elasticsearch.exception.ElasticsearchException;
import com.lsf.elasticsearch.model.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.ml.job.util.TimeUtil;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ElasticsearchUtil {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public Map getById(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsMap();
    }

    public Boolean checkIndexExist (String index) throws IOException {
        Response response = restHighLevelClient.getLowLevelClient().performRequest(new Request("HEAD", index));
        Boolean isExist = response.getStatusLine().getReasonPhrase().equals("OK");
        return isExist;
    }

    public void createIndex(String index) throws IOException, ElasticsearchException {
        if (!checkIndexExist(index)) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            if (createIndexResponse.isAcknowledged()) {
                logger.info("创建索引成功");
            } else {
                throw new ElasticsearchException("创建索引失败");
            }

        } else {
            throw new ElasticsearchException("索引已存在");
        }
    }

    public void deleteIndex (String index) throws IOException {
        if (checkIndexExist(index)) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        } else {
            logger.warn("索引" + index + "不存在");
            System.out.println("索引不存在");
        }
    }

    public void addData(String index, String id, Object object) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.id(id);
        indexRequest.source(mapper.writeValueAsString(object), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    public String addData(String index, Object object) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest.source(mapper.writeValueAsString(object), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.getId();
    }

    public void deleteById(String index, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
    }

    public int updateById(String index, String id, Object object) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.doc(mapper.writeValueAsString(object), XContentType.JSON);
        updateRequest.id(id);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return null != updateResponse && null != updateResponse ? updateResponse.getShardInfo().getSuccessful() : 0;
    }

    public void search(String... indices) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("phones.type", "iphonex"));
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_name").field("name.keyword");
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Arrays.stream(searchResponse.getHits().getHits()).forEach(searchHit -> {
            System.out.println(searchHit);
            Map source = searchHit.getSourceAsMap();
            source.forEach((key, value) -> {
                System.out.println(key + ":" + value);
            });
        });

    }

}

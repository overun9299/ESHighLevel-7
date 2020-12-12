package soap;


import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by ZhangPY on 2020/12/12
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain: 测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTest {

    @Autowired
    @Qualifier("restHighLevelClient")
    RestHighLevelClient client;

    /**
     * 测试创建索引
     */
    @Test
    public void testCreateIndex() throws IOException {

        /** 创建索引请求 **/
        CreateIndexRequest java_test = new CreateIndexRequest("java_test");
        /** 设置参数 number_of_replicas 是数据备份数，如果只有一台机器，设置为0 、number_of_shards  是数据分片数，默认为5，有时候设置为3 */
        java_test.settings(Settings.builder().put("number_of_shards","1").put("number_of_replicas","0"));
        /** 操作索引的客户端 */
        IndicesClient indices = client.indices();
        /** 客户端执行请求 indicesClient，请求后获得响应 **/
        CreateIndexResponse createIndexResponse = indices.create(java_test, RequestOptions.DEFAULT);
        /** 得到响应 */
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

    /**
     * 测试获取索引
     * @throws IOException
     */
    @Test
    public void testGetIndex() throws IOException {
        GetIndexRequest java_test = new GetIndexRequest("java_test");
        boolean exists = client.indices().exists(java_test, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 测试删除索引
     * @throws IOException
     */
    @Test
    public void testDeleteIndex() throws IOException {
        DeleteIndexRequest java_test = new DeleteIndexRequest("java_test");
        AcknowledgedResponse delete = client.indices().delete(java_test, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }
}

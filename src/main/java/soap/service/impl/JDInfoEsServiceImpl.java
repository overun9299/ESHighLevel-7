package soap.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soap.pojo.JDGoodsInfo;
import soap.pojo.MyUser;
import soap.service.JDInfoEsService;
import soap.utils.HtmlParseUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZhangPY on 2020/12/20
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain: JDInfoEsService实现类
 */
@Service
public class JDInfoEsServiceImpl implements JDInfoEsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private HtmlParseUtils htmlParseUtils;

    @Override
    public Boolean getJDInfoToES(String keyWord) {


        try {
            /** 判断是否存在索引 **/
            GetIndexRequest java_test = new GetIndexRequest("jd_goods");
            boolean exists = restHighLevelClient.indices().exists(java_test, RequestOptions.DEFAULT);
            if (!exists) {
                /** 创建索引请求 **/
                CreateIndexRequest jd_goods = new CreateIndexRequest("jd_goods");
                /** 客户端执行请求 indicesClient，请求后获得响应 **/
                CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(jd_goods, RequestOptions.DEFAULT);
                /** 得到响应 */
                boolean acknowledged = createIndexResponse.isAcknowledged();

                if (acknowledged) {
                    /** 爬取京东数据 **/
                    List<JDGoodsInfo> jdHtmlInfo = htmlParseUtils.getJDHtmlInfo(keyWord);
                    /** 批量插入数据 **/

                    /** 批量请求对象 **/
                    BulkRequest request = new BulkRequest();
                    /** 批量请求 **/
                    for (JDGoodsInfo jdGoodsInfo : jdHtmlInfo) {
                        request.add(new IndexRequest("java_test").source(JSONObject.toJSONString(jdGoodsInfo), XContentType.JSON));
                    }
                    /** 发送请求 **/
                    restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}

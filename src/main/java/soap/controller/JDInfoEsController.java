package soap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import soap.service.JDInfoEsService;

/**
 * Created by ZhangPY on 2020/12/20
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain: 爬取京东数据等相关
 */
@RestController
public class JDInfoEsController {

    @Autowired
    private JDInfoEsService jdInfoEsService;


    /**
     * 获取京东数据，并打入es
     * @param keyWord
     * @return
     */
    @GetMapping("/getJDInfoToES")
    public Boolean getJDInfoToES(String keyWord) {
        return jdInfoEsService.getJDInfoToES(keyWord);
    }
}

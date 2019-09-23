package com.xinyunfu.web.rest.pushtemp;

import com.xinyunfu.dto.Page;
import com.xinyunfu.dto.pushtemp.PushTemplateAddDTO;
import com.xinyunfu.dto.pushtemp.PushTemplateUpdateDTO;
import com.xinyunfu.entity.InfoCenterPushTemplateDTO;
import com.xinyunfu.service.pushtemp.PushTemplateMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/infoCenter/pushTemplate")
public class PushTemplateMangerResource {
    @Autowired private PushTemplateMangerService pushTemplateMangerService;

    /**
     * 添加模板
     */
    @PutMapping
    public void addTemplate(@RequestBody PushTemplateAddDTO pushTemplateAdd){
        log.info("REST request to add template. template: {}", pushTemplateAdd);
        pushTemplateMangerService.addTemplate(pushTemplateAdd);
        log.info("REST request to add template. success!");
    }

    /**
     * 查询模板
     */
    @GetMapping
    public InfoCenterPushTemplateDTO findByTemplateName(@RequestParam String templateName){
        log.info("REST request to find by templateName. templateName:{}", templateName);
        InfoCenterPushTemplateDTO template = pushTemplateMangerService.findByTemplateName(templateName);
        log.info("REST request to find by templateName. success! template id: {}",
                Objects.nonNull(template)? template.getId(): null);
        return template;
    }

    /**
     * 分页查询模板
     */
    @GetMapping("/page")
    public Page<InfoCenterPushTemplateDTO> pageQuery(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        log.info("REST request to page query. pageNo: {}, pageSize: {}", pageNo, pageSize);
        Page<InfoCenterPushTemplateDTO> templatePage = pushTemplateMangerService.pageQuery(pageNo, pageSize);
        log.info("REST request to page query. totalCount: {},pageCount: {}",
                templatePage.getTotalCount(), templatePage.getData().size());
        return templatePage;
    }

    /**
     * 更改模板
     */
    @PostMapping
    public void update(@RequestBody PushTemplateUpdateDTO pushTemplateUpdate){
        log.info("REST request to update template. template: {}", pushTemplateUpdate);
//        pushTemplateMangerService.update(pushTemplateUpdate);
        log.info("REST request to update template. success!");
    }
}

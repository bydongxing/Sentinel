package com.alibaba.csp.sentinel.dashboard.rule.nacos.auth;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthRuleNacosProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {

    @Autowired
    private ConfigService configService;

    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + NacosConfigUtil.AUTH_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID, 5000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }

        return JSON.parseArray(rules, AuthorityRuleEntity.class);
    }
}
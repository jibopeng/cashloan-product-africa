package com.ajaya.cashloan.rule.model.srule.config.rule;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.rule.model.srule.utils.RulePolicy;
import com.ajaya.cashloan.rule.model.srule.config.condition.Condition;

/**
 * Created by syq on 2016/12/17.
 */
public interface RuleBasic<T> {

    void setId(long id);

    void setColumn(String column);

    void setName(String name);

    void setValueType(Class<T> clazz);

    void setConditions(List<Condition<T>> conditions);

    void setRulePolicy(RulePolicy rulePolicy);

    void setPreLoad(Map<T, Integer> preLoad);
}

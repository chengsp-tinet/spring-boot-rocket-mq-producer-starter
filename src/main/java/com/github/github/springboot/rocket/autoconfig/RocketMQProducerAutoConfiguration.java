package com.github.github.springboot.rocket.autoconfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 自动配置生产者
 *
 * @author chengsp
 * @date 2020/5/29 16:16
 */
@Configurable
@Slf4j
@EnableConfigurationProperties(GlobalConfig.class)
@ConditionalOnProperty(prefix = GlobalConfig.CONFIG_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class RocketMQProducerAutoConfiguration {
    @Autowired
    private GlobalConfig globalConfig;

    @Bean
    @ConditionalOnProperty(prefix = GlobalConfig.CONFIG_PREFIX, name = "producer-type", havingValue = "default",
            matchIfMissing = true)
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = createProducer(true);
        producer.start();
        log.info("defaultMQProducer创建成功...");
        return producer;
    }

    @Bean
    @ConditionalOnProperty(prefix = GlobalConfig.CONFIG_PREFIX, name = "producer-type", havingValue = "transaction",
            matchIfMissing = false)
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        Integer checkRequestHoldMax = globalConfig.getCheckRequestHoldMax();
        Integer checkThreadPoolMaxSize = globalConfig.getCheckThreadPoolMaxSize();
        Integer checkThreadPoolMinSize = globalConfig.getCheckThreadPoolMinSize();
        TransactionMQProducer producer = (TransactionMQProducer) createProducer(false);
        if (checkThreadPoolMinSize != null) {
            producer.setCheckThreadPoolMinSize(checkThreadPoolMinSize);
        }
        if (checkThreadPoolMaxSize != null) {
            producer.setCheckThreadPoolMaxSize(checkThreadPoolMaxSize);
        }
        if (checkRequestHoldMax != null) {
            producer.setCheckRequestHoldMax(checkRequestHoldMax);
        }

        producer.start();
        log.info("transactionMQProducer创建成功...");
        return producer;
    }

    private DefaultMQProducer createProducer(boolean isDefault) {
        DefaultMQProducer producer;
        if (isDefault) {
            producer = new DefaultMQProducer();
        } else {
            producer = new TransactionMQProducer();
        }
        String namesrvAddr = globalConfig.getNamesrvAddr();
        String producerGroup = globalConfig.getProducerGroup();
        Integer clientCallbackExecutorThreads = globalConfig.getClientCallbackExecutorThreads();
        Integer pollNameServerInteval = globalConfig.getPollNameServerInteval();
        Integer heartbeatBrokerInterval = globalConfig.getHeartbeatBrokerInterval();
        Integer persistConsumerOffsetInterval = globalConfig.getPersistConsumerOffsetInterval();
        Integer defaultTopicQueueNums = globalConfig.getDefaultTopicQueueNums();
        Integer sendMsgTimeout = globalConfig.getSendMsgTimeout();
        Integer compressMsgBodyOverHowmuch = globalConfig.getCompressMsgBodyOverHowmuch();

        Integer retryTimesWhenSendFailed = globalConfig.getRetryTimesWhenSendFailed();
        Boolean retryAnotherBrokerWhenNotStoreOK = globalConfig.getRetryAnotherBrokerWhenNotStoreOK();
        Integer maxMessageSize = globalConfig.getMaxMessageSize();
        Boolean unitMode = globalConfig.getUnitMode();
        if (namesrvAddr == null) {
            throw new RuntimeException("namesrvAddr不能为空,请指定正确的namesrvAddr");
        }
        if (producerGroup == null) {
            throw new RuntimeException("producerGroup不能为空,请指定正确的producerGroup");
        }
        producer.setNamesrvAddr(namesrvAddr);
        producer.setProducerGroup(producerGroup);
        if (clientCallbackExecutorThreads != null) {
            producer.setClientCallbackExecutorThreads(clientCallbackExecutorThreads);
        }
        if (pollNameServerInteval != null) {
            producer.setPollNameServerInteval(pollNameServerInteval);
        }
        if (heartbeatBrokerInterval != null) {
            producer.setHeartbeatBrokerInterval(heartbeatBrokerInterval);
        }
        if (persistConsumerOffsetInterval != null) {
            producer.setPersistConsumerOffsetInterval(persistConsumerOffsetInterval);
        }
        if (defaultTopicQueueNums != null) {
            producer.setDefaultTopicQueueNums(defaultTopicQueueNums);
        }
        if (sendMsgTimeout != null) {
            producer.setSendMsgTimeout(sendMsgTimeout);
        }
        if (compressMsgBodyOverHowmuch != null) {
            producer.setCompressMsgBodyOverHowmuch(compressMsgBodyOverHowmuch);
        }
        if (retryTimesWhenSendFailed != null) {
            producer.setRetryTimesWhenSendFailed(retryTimesWhenSendFailed);
        }
        if (retryAnotherBrokerWhenNotStoreOK != null) {
            producer.setRetryAnotherBrokerWhenNotStoreOK(retryAnotherBrokerWhenNotStoreOK);
        }
        if (maxMessageSize != null) {
            producer.setMaxMessageSize(maxMessageSize);
        }
        if (unitMode != null) {
            producer.setUnitMode(unitMode);
        }

        log.info("globalConfig:{}", JSON.toJSONString(globalConfig));
        return producer;
    }
}

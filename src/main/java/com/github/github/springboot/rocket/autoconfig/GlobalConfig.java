package com.github.github.springboot.rocket.autoconfig;

import com.alibaba.rocketmq.common.MixAll;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 全局配置
 *
 * @author chengsp
 * @date 2020/5/29 14:54
 */
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = GlobalConfig.CONFIG_PREFIX)
public class GlobalConfig {
    public static final String CONFIG_PREFIX = "spring.rocket.producer";
    /**
     * 是否启用
     */
    private Boolean enable;
    /**
     * mq服务地址
     */
    private String namesrvAddr = System.getProperty(MixAll.NAMESRV_ADDR_PROPERTY, System.getenv(MixAll.NAMESRV_ADDR_ENV));
    /**
     * 生产者组名
     */
    private String producerGroup;
    /**
     * 生产者类型:普通类型或事务类型
     */
    private String producerType;
    /**
     * 客户端回调执行器线程数
     */
    private Integer clientCallbackExecutorThreads;
    /**
     * 从命名服务器提取主题信息间隔
     */
    private Integer pollNameServerInteval;
    /**
     * 消息代理的心跳间隔（以微秒为单位)
     */
    private Integer heartbeatBrokerInterval;
    /**
     * 消费者的偏移固定间隔
     */
    private Integer persistConsumerOffsetInterval;

    /**
     * 主题队列数
     */
    private volatile Integer defaultTopicQueueNums;
    /**
     * 发送超时时间(ms)
     */
    private Integer sendMsgTimeout;
    /**
     * 压缩消息体量
     */
    private Integer compressMsgBodyOverHowmuch;
    /**
     * 发送失败时重试次数
     */
    private Integer retryTimesWhenSendFailed;
    /**
     * 不存储时重试另一个代理，确定
     */
    private Boolean retryAnotherBrokerWhenNotStoreOK;
    /**
     * 最大消息数
     */
    private Integer maxMessageSize;
    /**
     *
     */
    private Boolean unitMode;

    /**
     * 检查线程池最小大小,事务消息有效
     */
    private Integer checkThreadPoolMinSize;
    /**
     * 检查线程池最大大小,事务消息有效
     */
    private Integer checkThreadPoolMaxSize;
    /**
     * 检查请求保留最大值,事务消息有效
     */
    private Integer checkRequestHoldMax;

}

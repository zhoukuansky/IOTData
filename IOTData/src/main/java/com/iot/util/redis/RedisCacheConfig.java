package com.iot.util.redis;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Map;

@Slf4j
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(factory);
        /**
         * StringRedisSerializer序列器：
         * 优点：开发者友好，轻量级，效率也比较高
         * 缺点：只能序列化String类型，如果key使用该序列化器，则key必须为String类型
         */
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(genericJackson2JsonRedisSerializer);
        template.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        //开启Redis事务
        /**
         * 为何要开启事务呢？redis本身是单线程执行的one by one 
         * 虽然都是原子性操作，那需要多个原子性的操作才算一个完整的事件呢
         * 譬如 redis get set可能就是一对操作才能保证操作的完整性
         * 譬如记录访问数 value 从0开始，在并发情况下多线程会同时查询到 value 为0 的情况，
         * 此时两线程都得到value 0 为此都 +1操作 执行结果 value=1,而我们期望的结果至少是2才对
         * 所以需要用事务来解决这一堆操作(get set)
         */
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> template) {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //配置多个缓存名称 这里我们用个能用的模板  这里有坑点往下看
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                // 设置key为String
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
                // 设置value 为自动转Json的Object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
                // 不缓存null
                .disableCachingNullValues();
        //这里对RedisCacheConfiguration不熟悉的可能会有疑问，我们这样批量config.entryTtl(Duration.ofMinutes(15))最后不都是同一个引用吗，过期时间会生效吗？
        Map<String, RedisCacheConfiguration> expires = ImmutableMap.<String, RedisCacheConfiguration>builder()
                .put("UserInfoCache", config.entryTtl(Duration.ofMinutes(20)))
                .put("SystemCache", config.entryTtl(Duration.ofMinutes(20)))
                .put("SensorCache", config.entryTtl(Duration.ofMinutes(30)))
                .put("DeviceCache", config.entryTtl(Duration.ofMinutes(10)))
                .put("LogCache", config.entryTtl(Duration.ofMinutes(10)))
                .put("EmailCache", config.entryTtl(Duration.ofMinutes(3)))
                .build();

        RedisCacheManager redisCacheManager =
                RedisCacheManager.RedisCacheManagerBuilder
                        // Redis 连接工厂
                        .fromConnectionFactory(template.getConnectionFactory())
                        // 默认缓存配置
                        .cacheDefaults(config.entryTtl(Duration.ofMinutes(15)))
                        // 配置同步修改或删除 put/evict
                        .transactionAware()
                        .initialCacheNames(expires.keySet())
                        .withInitialCacheConfigurations(expires)
                        .build();
        return redisCacheManager;
    }

    /**
     * 自定义生成key的规则
     *
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {//设置自定义key{ClassName + methodName + params}
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(",Method:");
            sb.append(method.getName());
            sb.append(",Params[");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].toString());
                if (i != (params.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append("]");
            log.debug("Data Caching Redis Key : {}", sb.toString());
            return sb.toString();
        };
    }

    //自定义keyGenerator，Key生成器
    @Bean
    public KeyGenerator updateByIdkeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(",Method:");
            sb.append("getById");
            sb.append(",Params[");
            try {
                Field id = params[0].getClass().getDeclaredField("id");
                id.setAccessible(true);
                sb.append(id.get(params[0]).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            sb.append("]");
            log.debug("Data Caching Redis Key : {}", sb.toString());
            return sb.toString();
        };
    }

    //自定义keyGenerator，Key生成器
    @Bean
    public KeyGenerator deleteByIdkeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(",Method:");
            sb.append("getById");
            sb.append(",Params[");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].toString());
                if (i != (params.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append("]");
            log.debug("Data Caching Redis Key : {}", sb.toString());
            return sb.toString();
        };
    }

}
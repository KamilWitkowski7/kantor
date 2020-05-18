package pl.kw.app.account.core.application;

import com.mongodb.WriteConcern;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnProperty(value = "mongodb.replace.in-memory", havingValue = "false", matchIfMissing = true)
@ConditionalOnPropertyNotEmpty("spring.data.mongodb.account.uri")
@EnableMongoRepositories(
    basePackages = "pl.kw.app.account.infrastructure.secondary.persistence",
    mongoTemplateRef = "mongoAccountTemplate"
)
@EnableConfigurationProperties({MongoAccountProperties.class})
@EnableMongoAuditing
@Slf4j
public class AccountMongoConfig {
    @Bean
    @Qualifier("mongoAccountDbFactory")
    public MongoDbFactory mongoAccountDbFactory(MongoAccountProperties properties) {
        return new SimpleMongoClientDbFactory(properties.getUri());
    }

    @Bean
    @Primary
    @Qualifier("mongoAccountMappingConverter")
    public MappingMongoConverter mappingMongoAccountConverter(
            @Qualifier("mongoAccountDbFactory") MongoDbFactory mongoDbFactory) {
        MongoMappingContext m = new MongoMappingContext();
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, m);
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null)); // No _clazz attribute

        mappingConverter.setMapKeyDotReplacement(","); // Map keys can not contain dots
        return mappingConverter;
    }


    @Bean
    @Qualifier("mongoAccountTemplate")
    MongoTemplate mongoAccountTemplate(
            @Qualifier("mongoAccountDbFactory") MongoDbFactory mongoDbFactory,
            @Qualifier("mongoAccountMappingConverter") MongoConverter converter
    ) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        return mongoTemplate;
    }
}

package egovframework.example.bat.job;

import org.egovframe.rte.bat.core.item.database.EgovMyBatisBatchItemWriter;
import org.egovframe.rte.bat.core.item.file.mapping.EgovObjectMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.PlatformTransactionManager;

import egovframework.example.bat.domain.trade.CustomerCredit;
import egovframework.example.bat.domain.trade.CustomerCreditIncreaseProcessor;

import egovframework.example.bat.transform.EgovDefaultLineMapper;
import egovframework.example.bat.transform.EgovFixedLengthTokenizer;

@Configuration
public class FixedLengthToMybatisJobConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(FixedLengthToMybatisJobConfig.class);

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private ResourceLoader resourceLoader;

    @Bean
    public Job fixedLengthToMybatisJob(Step fixedLengthToMybatisStep) {
        return new JobBuilder("fixedLengthToMybatisJob", jobRepository)
                .start(fixedLengthToMybatisStep)
                .build();
    }

    @Bean
    public Step fixedLengthToMybatisStep(JobRepository jobRepository,
                                         PlatformTransactionManager transactionManager,
                                         @Qualifier("fixedLengthToMybatisItemReader") FlatFileItemReader<CustomerCredit> fixedLengthItemReader,
                                         @Qualifier("fixedLengthToMybatisItemProcessor") CustomerCreditIncreaseProcessor itemProcessor,
                                         EgovMyBatisBatchItemWriter<CustomerCredit> myBatisBatchItemWriter) {
        return new StepBuilder("fixedLengthToMybatisStep", jobRepository)
                .<CustomerCredit, CustomerCredit>chunk(2, transactionManager)
                .reader(fixedLengthItemReader)
                .processor(itemProcessor)
                .writer(myBatisBatchItemWriter)
                .build();
    }
    
    @Bean(name = "fixedLengthToMybatisItemReader")
    @StepScope
    public FlatFileItemReader<CustomerCredit> fixedLengthItemReader(@Value("#{jobParameters['inputFile']}") String inputFile) {
    	
    	LOGGER.debug("===>>> inputFile = {}", inputFile);
    	Resource resource = resourceLoader.getResource(inputFile);
    	
        return new FlatFileItemReaderBuilder<CustomerCredit>()
        		.name("fixedLengthItemReader")
        		.resource(resource)
        		.lineMapper(defaultLineMapper())
        		.saveState(true)
        		.build();
    }

    @Bean(name = "fixedLengthToMybatisLineTokenizer")
    public EgovFixedLengthTokenizer lineTokenizer() {
    	EgovFixedLengthTokenizer fixedLengthTokenizer = new EgovFixedLengthTokenizer();
    	fixedLengthTokenizer.setColumns(new Range[]{new Range(1, 9), new Range(10, 11)});
    	return fixedLengthTokenizer;
    }
    
    @Bean(name = "fixedLengthToMybatisObjectMapper")
    public EgovObjectMapper<CustomerCredit> objectMapper() {
    	EgovObjectMapper<CustomerCredit> objectMapper = new EgovObjectMapper<>();
		objectMapper.setType(CustomerCredit.class);
		objectMapper.setNames(new String[] {"name","credit"});
		return objectMapper;
    }
    
    @Bean(name = "fixedLengthToMybatisDefaultLineMapper")
    public EgovDefaultLineMapper<CustomerCredit> defaultLineMapper() {
    	EgovDefaultLineMapper<CustomerCredit> lineMapper = new EgovDefaultLineMapper<>();
    	lineMapper.setLineTokenizer(lineTokenizer());
    	lineMapper.setObjectMapper(objectMapper());
    	return lineMapper;
    }
    
    @Bean(name = "fixedLengthToMybatisItemProcessor")
    public CustomerCreditIncreaseProcessor itemProcessor() {
        return new CustomerCreditIncreaseProcessor();
    }

    @Bean
    @StepScope
    public EgovMyBatisBatchItemWriter<CustomerCredit> myBatisBatchItemWriter(SqlSessionFactoryBean sqlSessionFactoryBean) {
    	
    	EgovMyBatisBatchItemWriter<CustomerCredit> myBatisBatchItemWriter	= new EgovMyBatisBatchItemWriter<>();
    	try {
			myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	myBatisBatchItemWriter.setStatementId("Customer.updateCredit");
		
    	return myBatisBatchItemWriter;
    }
    
}

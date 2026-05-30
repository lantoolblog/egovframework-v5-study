package egovframework.example.bat.job;

import org.egovframe.rte.bat.core.item.file.mapping.EgovObjectMapper;
import org.egovframe.rte.bat.core.item.file.transform.EgovFieldExtractor;
import org.egovframe.rte.bat.core.item.file.transform.EgovFixedLengthLineAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.transaction.PlatformTransactionManager;

import egovframework.example.bat.domain.trade.CustomerCredit;
import egovframework.example.bat.domain.trade.CustomerCreditIncreaseProcessor;

import egovframework.example.bat.transform.EgovDefaultLineMapper;
import egovframework.example.bat.transform.EgovFixedLengthTokenizer;

@Configuration
public class FixedLengthToFixedLengthJobConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(FixedLengthToFixedLengthJobConfig.class);

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private ResourceLoader resourceLoader;
	
    @Bean
    public Job fixedLengthToFixedLengthJob(Step fixedLengthToFixedLengthStep) {
        return new JobBuilder("fixedLengthToFixedLengthJob", jobRepository)
                .start(fixedLengthToFixedLengthStep)
                .build();
    }

    @Bean
    public Step fixedLengthToFixedLengthStep(JobRepository jobRepository,
                                             PlatformTransactionManager transactionManager,
                                             @Qualifier("fixedLengthToFixedLengthItemReader") FlatFileItemReader<CustomerCredit> fixedLengthItemReader,
                                             @Qualifier("fixedLengthToFixedLengthItemProcessor") CustomerCreditIncreaseProcessor itemProcessor,
                                             FlatFileItemWriter<CustomerCredit> fixedLengthItemWriter) {
        return new StepBuilder("fixedLengthToFixedLengthStep", jobRepository)
                .<CustomerCredit, CustomerCredit>chunk(2, transactionManager)
                .reader(fixedLengthItemReader)
                .processor(itemProcessor)
                .writer(fixedLengthItemWriter)
                .build();
    }
    
    @Bean(name = "fixedLengthToFixedLengthItemReader")
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

    @Bean(name = "fixedLengthToFixedLengthLineTokenizer")
    public EgovFixedLengthTokenizer lineTokenizer() {
    	EgovFixedLengthTokenizer fixedLengthTokenizer = new EgovFixedLengthTokenizer();
    	fixedLengthTokenizer.setColumns(new Range[]{new Range(1, 9), new Range(10, 11)});
    	return fixedLengthTokenizer;
    }
    
    @Bean(name = "fixedLengthToFixedLengthObjectMapper")
    public EgovObjectMapper<CustomerCredit> objectMapper() {
    	EgovObjectMapper<CustomerCredit> objectMapper = new EgovObjectMapper<>();
		objectMapper.setType(CustomerCredit.class);
		objectMapper.setNames(new String[] {"name","credit"});
		return objectMapper;
    }
    
    @Bean(name = "fixedLengthToFixedLengthDefaultLineMapper")
    public EgovDefaultLineMapper<CustomerCredit> defaultLineMapper() {
    	EgovDefaultLineMapper<CustomerCredit> lineMapper = new EgovDefaultLineMapper<>();
    	lineMapper.setLineTokenizer(lineTokenizer());
    	lineMapper.setObjectMapper(objectMapper());
    	return lineMapper;
    }
    
    @Bean(name = "fixedLengthToFixedLengthItemProcessor")
    public CustomerCreditIncreaseProcessor itemProcessor() {
        return new CustomerCreditIncreaseProcessor();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<CustomerCredit> fixedLengthItemWriter(@Value("#{jobParameters['outputFile']}") String outputFile) {
    	
    	LOGGER.debug("===>>> outputFile = "+outputFile);
    	
    	WritableResource resource = new FileSystemResource(outputFile);
    	
    	return new FlatFileItemWriterBuilder<CustomerCredit>()
    			.name("fixedLengthItemWriter")
    			.resource(resource)
    			.lineAggregator(lineAggregator())
    			.saveState(true)
    			.build();
    }
    
    @Bean
    public EgovFieldExtractor<CustomerCredit> fieldExtractor() {
    	EgovFieldExtractor<CustomerCredit> fieldExtractor = new EgovFieldExtractor<>();
    	fieldExtractor.setNames(new String[] {"name","credit"});
    	return fieldExtractor;
    }
    
    @Bean
    public EgovFixedLengthLineAggregator<CustomerCredit> lineAggregator() {
    	EgovFixedLengthLineAggregator<CustomerCredit> lineAggregator = new EgovFixedLengthLineAggregator<>();
    	lineAggregator.setFieldRanges(new int[] {9,2});
    	lineAggregator.setFieldExtractor(fieldExtractor());
    	return lineAggregator;
    }
}

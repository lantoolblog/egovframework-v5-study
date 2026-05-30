package egovframework.example.bat.job;

import org.egovframe.rte.bat.core.item.file.mapping.EgovObjectMapper;
import org.egovframe.rte.bat.core.item.file.transform.EgovFieldExtractor;
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
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
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

import egovframework.example.bat.transform.EgovDelimitedLineTokenizer;
import egovframework.example.bat.transform.EgovLineTokenizer;
import egovframework.example.bat.transform.EgovDefaultLineMapper;

@Configuration
public class DelimitedToDelimitedJobConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DelimitedToDelimitedJobConfig.class);

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ResourceLoader resourceLoader;
	
    @Bean
    public Job delimitedToDelimitedJob(Step delimitedToDelimitedStep) {
        return new JobBuilder("delimitedToDelimitedJob", jobRepository)
                .start(delimitedToDelimitedStep)
                .build();
    }

    @Bean
    public Step delimitedToDelimitedStep(JobRepository jobRepository,
                                         PlatformTransactionManager transactionManager,
                                         FlatFileItemReader<CustomerCredit> delimitedItemReader,
                                         @Qualifier("delimitedToDelimitedItemProcessor") CustomerCreditIncreaseProcessor itemProcessor,
                                         FlatFileItemWriter<CustomerCredit> delimitedItemWriter) {
        return new StepBuilder("delimitedToDelimitedStep", jobRepository)
                .<CustomerCredit, CustomerCredit>chunk(2, transactionManager)
                .reader(delimitedItemReader)
                .processor(itemProcessor)
                .writer(delimitedItemWriter)
                .build();
    }
    
    @Bean
    @StepScope
    public FlatFileItemReader<CustomerCredit> delimitedItemReader(@Qualifier("delimitedToDelimitedJob.delimitedLineMapper") EgovDefaultLineMapper<CustomerCredit> defaultLineMapper,
				@Value("#{jobParameters['inputFile']}") String inputFile) {
		
		LOGGER.debug("===>>> inputFile = {}", inputFile);
		
		Resource resource = resourceLoader.getResource(inputFile);
		
		return new FlatFileItemReaderBuilder<CustomerCredit>()
				.name("delimitedItemReader")
				.resource(resource)
				.lineMapper(defaultLineMapper)
				.saveState(true)
				.build();
    }

    @Bean(name="delimitedToDelimitedJob.delimitedLineTokenizer")
    public EgovDelimitedLineTokenizer lineTokenizer() {
        EgovDelimitedLineTokenizer lineTokenizer = new EgovDelimitedLineTokenizer();
    	lineTokenizer.setDelimiter(",");
    	return lineTokenizer;
    }
    
    @Bean(name="delimitedToDelimitedJob.objectMapper")
    public EgovObjectMapper<CustomerCredit> objectMapper() {
    	EgovObjectMapper<CustomerCredit> objectMapper = new EgovObjectMapper<>();
		objectMapper.setType(CustomerCredit.class);
		objectMapper.setNames(new String[] {"name","credit"});
		return objectMapper;
    }
    
    @Bean(name="delimitedToDelimitedJob.delimitedLineMapper")
    public EgovDefaultLineMapper<CustomerCredit> defaultLineMapper(@Qualifier("delimitedToDelimitedJob.delimitedLineTokenizer") EgovDelimitedLineTokenizer lineTokenizer
    		,@Qualifier("delimitedToDelimitedJob.objectMapper") EgovObjectMapper<CustomerCredit> objectMapper) {
    	EgovDefaultLineMapper<CustomerCredit> lineMapper = new EgovDefaultLineMapper<>();
    	lineMapper.setLineTokenizer((EgovLineTokenizer) lineTokenizer);
    	lineMapper.setObjectMapper(objectMapper);
    	return lineMapper;
    }
    
    @Bean(name = "delimitedToDelimitedItemProcessor")
    public CustomerCreditIncreaseProcessor itemProcessor() {
        return new CustomerCreditIncreaseProcessor();
    }

    @Bean
    @StepScope
    public FlatFileItemWriter<CustomerCredit> delimitedItemWriter(@Qualifier("delimitedToDelimitedJob.delimitedLineAggregator") DelimitedLineAggregator<CustomerCredit> lineAggregator,
				@Value("#{jobParameters['outputFile']}") String outputFile) {
		
		WritableResource resource = new FileSystemResource(outputFile);
		
		return new FlatFileItemWriterBuilder<CustomerCredit>()
				.name("delimitedItemWriter")
				.resource(resource)
				.lineAggregator(lineAggregator)
				.saveState(true)
				.build();
    }
    
    @Bean(name="delimitedToDelimitedJob.fieldExtractor")
    public EgovFieldExtractor<CustomerCredit> fieldExtractor() {
    	EgovFieldExtractor<CustomerCredit> fieldExtractor = new EgovFieldExtractor<>();
    	fieldExtractor.setNames(new String[] {"name","credit"});
    	return fieldExtractor;
    }
    
    @Bean(name="delimitedToDelimitedJob.delimitedLineAggregator")
    public DelimitedLineAggregator<CustomerCredit> lineAggregator(@Qualifier("delimitedToDelimitedJob.fieldExtractor") EgovFieldExtractor<CustomerCredit> fieldExtractor) {
    	DelimitedLineAggregator<CustomerCredit> lineAggregator = new DelimitedLineAggregator<>();
    	lineAggregator.setDelimiter(",");
    	lineAggregator.setFieldExtractor(fieldExtractor);
    	return lineAggregator;
    }
}

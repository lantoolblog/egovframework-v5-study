package egovframework.example.bat.job;

import javax.sql.DataSource;

import org.egovframe.rte.bat.core.item.database.EgovJdbcBatchItemWriter;
import org.egovframe.rte.bat.core.item.database.support.EgovMethodMapItemPreparedStatementSetter;
import org.egovframe.rte.bat.core.item.file.mapping.EgovObjectMapper;
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
import jakarta.servlet.ServletContext;

@Configuration
public class FixedLengthToJdbcJobConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(FixedLengthToJdbcJobConfig.class);

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private ServletContext servletContext;

    @Bean
    public Job fixedLengthToJdbcJob(Step fixedLengthToJdbcStep) {
        return new JobBuilder("fixedLengthToJdbcJob", jobRepository)
                .start(fixedLengthToJdbcStep)
                .build();
    }

    @Bean
    public Step fixedLengthToJdbcStep(JobRepository jobRepository,
                                      PlatformTransactionManager transactionManager,
                                      @Qualifier("fixedLengthToJdbcItemReader") FlatFileItemReader<CustomerCredit> fixedLengthItemReader,
                                      @Qualifier("fixedLengthToJdbcItemProcessor") CustomerCreditIncreaseProcessor itemProcessor,
                                      EgovJdbcBatchItemWriter<CustomerCredit> jdbcBatchItemWriter
                                      //FlatFileItemWriter<CustomerCredit> fixedLengthItemWriter
                                      ) {
        return new StepBuilder("fixedLengthToJdbcStep", jobRepository)
                .<CustomerCredit, CustomerCredit>chunk(2, transactionManager)
                .reader(fixedLengthItemReader)
                .processor(itemProcessor)
                .writer(jdbcBatchItemWriter)
                //.writer(fixedLengthItemWriter)
                .build();
    }
    
    @Bean(name = "fixedLengthToJdbcItemReader")
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

    @Bean(name = "fixedLengthToJdbcLineTokenizer")
    public EgovFixedLengthTokenizer lineTokenizer() {
    	EgovFixedLengthTokenizer fixedLengthTokenizer = new EgovFixedLengthTokenizer();
    	fixedLengthTokenizer.setColumns(new Range[]{new Range(1, 9), new Range(10, 11)});
    	return fixedLengthTokenizer;
    }
    
    @Bean(name = "fixedLengthToJdbcObjectMapper")
    public EgovObjectMapper<CustomerCredit> objectMapper() {
    	EgovObjectMapper<CustomerCredit> objectMapper = new EgovObjectMapper<>();
		objectMapper.setType(CustomerCredit.class);
		objectMapper.setNames(new String[] {"name","credit"});
		return objectMapper;
    }
    
    @Bean(name = "fixedLengthToJdbcDefaultLineMapper")
    public EgovDefaultLineMapper<CustomerCredit> defaultLineMapper() {
    	EgovDefaultLineMapper<CustomerCredit> lineMapper = new EgovDefaultLineMapper<>();
    	lineMapper.setLineTokenizer(lineTokenizer());
    	lineMapper.setObjectMapper(objectMapper());
    	return lineMapper;
    }
    
    @Bean(name = "fixedLengthToJdbcItemProcessor")
    public CustomerCreditIncreaseProcessor itemProcessor() {
        return new CustomerCreditIncreaseProcessor();
    }

    @Bean
    @StepScope
    public EgovJdbcBatchItemWriter<CustomerCredit> jdbcBatchItemWriter(DataSource dataSource) {
    	
		EgovJdbcBatchItemWriter<CustomerCredit> jdbcBatchItemWriter	= new EgovJdbcBatchItemWriter<>();
		jdbcBatchItemWriter.setAssertUpdates(true);
		jdbcBatchItemWriter.setItemPreparedStatementSetter(itemPreparedStatementSetter());
		jdbcBatchItemWriter.setSql("UPDATE CUSTOMER set credit =? where name =?");
		jdbcBatchItemWriter.setParams(new String[] {"credit","name"});
		jdbcBatchItemWriter.setDataSource(dataSource);
		
    	return jdbcBatchItemWriter;
    }
    
    @Bean
    public EgovMethodMapItemPreparedStatementSetter<CustomerCredit> itemPreparedStatementSetter() {
    	return new EgovMethodMapItemPreparedStatementSetter<>();
    }
    
    /*
    @Bean
    @StepScope
    public FlatFileItemWriter<CustomerCredit> fixedLengthItemWriter() {

    	// WTP 배포 경로에서 프로젝트 루트 → {프로젝트루트}/target/outputs
    	String outputDir;
    	String realPath = servletContext.getRealPath("/");
    	int metadataIdx = realPath.indexOf(".metadata");
    	if (metadataIdx > 0) {
    		String workspaceRoot = realPath.substring(0, metadataIdx);
    		String contextName = servletContext.getContextPath().replace("/", "");
    		outputDir = workspaceRoot + contextName + File.separator + "target" + File.separator + "outputs";
    	} else {
    		outputDir = realPath + "outputs";
    	}
    	File outputDirectory = new File(outputDir);
    	if (!outputDirectory.exists()) {
    		outputDirectory.mkdirs();
    	}
    	File outputFile = new File(outputDirectory, "txtData-study.txt");
    	String outputFilePath = outputFile.getAbsolutePath();
    	LOGGER.debug("===>>> outputFile = {}", outputFilePath);

    	WritableResource resource = new FileSystemResource(outputFilePath);

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
	*/
}

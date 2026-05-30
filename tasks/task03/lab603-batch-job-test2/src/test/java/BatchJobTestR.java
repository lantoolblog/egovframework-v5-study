import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.egovframe.rte.bat.core.launch.support.EgovBatchRunner;
@ExtendWith(SpringExtension.class)

/** 
 * Test File Information 
 * Job:: /egovframework/batch/job/delimitedToDelimitedJob.xml
 * Job Launcher:: /egovframework/batch/context-batch-job-launcher.xml
 * job Parameters:: Date_Default Timestamp
 */ 

@ContextConfiguration(locations = { "/egovframework/batch/context-batch-job-launcher.xml", "/egovframework/batch/job/delimitedToDelimitedJob.xml", "/egovframework/batch/context-batch-datasource.xml" })
public class BatchJobTestR{

	@Autowired
	@Qualifier("eGovBatchRunner")
	private EgovBatchRunner egovBatchRunner;

	@Test
	public void testJobRun() throws Exception {

		String jobName = "delimitedToDelimitedJob";

		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addLong("timestamp", new Date().getTime());
		

		Properties jobParameters = egovBatchRunner.convertJobParametersToString(jobParametersBuilder.toJobParameters());

		long executionId = egovBatchRunner.start(jobName, jobParameters);

		assertEquals(BatchStatus.COMPLETED, egovBatchRunner.getJobExecution(executionId).getStatus());

	}
}
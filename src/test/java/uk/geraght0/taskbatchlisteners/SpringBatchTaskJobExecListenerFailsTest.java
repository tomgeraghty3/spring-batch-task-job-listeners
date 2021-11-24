package uk.geraght0.taskbatchlisteners;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.task.batch.listener.TaskBatchExecutionListener;
import org.springframework.context.annotation.Import;

@SpringBatchTest
@Import(BatchConfig.class)
@SpringBootTest(properties = {"spring.batch.job.enabled=false", "listener.throw-exception=true"})
class SpringBatchTaskJobExecListenerFailsTest {

  @Autowired
  private JobLauncherTestUtils testUtils;

  @Autowired
  @SpyBean
  private TaskBatchExecutionListener taskBatchExecutionListener;

  @Autowired
  @SpyBean
  private MyJobListener myJobListener;

  @Test
  void testJobRun_whenListenerException_thenTaskListenerNotFireSoNoTaskBatchAssociation() throws Exception {
    assertThat(myJobListener).isInstanceOf(JobListenerException.class);
    final JobExecution je = testUtils.launchJob();

    //Our registered job listener fires, then throws an exception. Check that the beforeJob was called
    verify(myJobListener).beforeJob(je);

    //Verify here that the task batch execution listener is not fired
    verify(taskBatchExecutionListener, times(0)).beforeJob(je);
  }
}

package uk.geraght0.taskbatchlisteners;

import org.springframework.batch.core.JobExecution;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "listener.throw-exception", havingValue = "false")
public class JobListenerNoException implements MyJobListener {

  @Override
  public void beforeJob(final JobExecution jobExecution) {
    System.out.println("Before executed");
  }

  @Override
  public void afterJob(final JobExecution jobExecution) {

  }
}

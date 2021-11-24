package uk.geraght0.taskbatchlisteners;

import org.springframework.batch.core.JobExecution;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "listener.throw-exception", havingValue = "true", matchIfMissing = true)
public class JobListenerException implements MyJobListener {

  @Override
  public void beforeJob(final JobExecution jobExecution) {
    throw new IllegalArgumentException("Simulate the job listener throwing an exception");
  }

  @Override
  public void afterJob(final JobExecution jobExecution) {

  }
}

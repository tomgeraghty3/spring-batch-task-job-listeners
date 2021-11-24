package uk.geraght0.taskbatchlisteners;

import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableTask
@EnableBatchProcessing
public class BatchConfig {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final MyJobListener listener;

  public BatchConfig(final JobBuilderFactory jobBuilderFactory, final StepBuilderFactory stepBuilderFactory, final MyJobListener listener) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.listener = listener;
  }

  @Bean
  public Job job() {
    final SimpleJobBuilder builder = this.jobBuilderFactory.get("testJob")
                                                           .listener(listener)
                                                           .start(step());
    return builder.build();
  }


  public Step step() {
    return this.stepBuilderFactory.get("step")
                                  .chunk(10)
                                  .reader(new IR())
                                  .processor(new IP())
                                  .writer(new IW())
                                  .build();
  }

  public static class IR implements ItemReader<Object> {

    private int count = 0;

    @Override
    public Object read() {
      if (count == 0) {
        count++;
        return Object.class;
      } else {
        return null;
      }
    }
  }

  public static class IP implements ItemProcessor<Object, Object> {

    @Override
    public Object process(final Object item) {
      return item;
    }
  }

  public static class IW implements ItemWriter<Object> {

    @Override
    public void write(final List<?> items) {
      for (final Object item : items) {
        System.out.println("Written item " + item);
      }
    }
  }


}

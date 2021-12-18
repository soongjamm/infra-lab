package com.soongjamm.example;

import com.soongjamm.example.batch.MoneyCopyProcessor;
import com.soongjamm.example.domain.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class JobTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

//    @MockBean
//    private MoneyCopyProcessor moneyCopyProcessor;

    @Test
    public void testMyJob() throws Exception {
//        given(moneyCopyProcessor.process(any())).willReturn(null);

        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();


        jobExecution.getStepExecutions().stream().forEach(x -> System.out.println( x.getFilterCount()));
        Assertions.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}

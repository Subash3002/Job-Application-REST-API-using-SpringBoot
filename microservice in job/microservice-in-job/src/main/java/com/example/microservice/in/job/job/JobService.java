package com.example.microservice.in.job.job;

import com.example.microservice.in.job.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO> findAll();
    void createJob(Job job);

    Job getJobById(Long id);

    Boolean deleteById(Long id);

    Boolean updateById(Long id,Job updatedJob);
}

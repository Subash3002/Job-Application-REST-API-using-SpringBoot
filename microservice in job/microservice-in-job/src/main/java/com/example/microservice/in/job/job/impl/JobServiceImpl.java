package com.example.microservice.in.job.job.impl;


import com.example.microservice.in.job.job.Job;
import com.example.microservice.in.job.job.JobRepository;
import com.example.microservice.in.job.job.JobService;
import com.example.microservice.in.job.job.dto.JobWithCompanyDTO;
import com.example.microservice.in.job.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;
    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs=jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs=new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDto(Job job){
        JobWithCompanyDTO jobWithCompanyDTO=new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        RestTemplate restTemplate=new RestTemplate();
        Company company=restTemplate.getForObject("http://localhost:8081/companies/"+job.getCompanyId(),Company.class);

        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }



    @Override
    public void createJob(Job job) {

        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
//        for(Job job:jobs){
//            if(job.getId().equals(id)){
//                jobs.remove(job);
//                return true;
//            }
//        }
//        return false;
    }

    @Override
    public Boolean updateById(Long id, Job updatedJob) {
        Optional<Job> optional=jobRepository.findById(id);

        if(optional.isPresent()){
            Job job=optional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}

package dataprovider.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Job {
	
	private int id;
	private String company;
	private String title;
	private String details;

	private String degree;
	private String experience;
	
	private String publishTime;
	private String jobSite;
	private String url;
	
	
	public static List<Job> fromList(List<Map<String, String>> list){
		List<Job> jobs = new ArrayList<Job>();
		for(Map<String, String> map : list){
			Job job = fromMap(map);
			jobs.add(job);
		}
		return jobs;
	}
	
	public static Job fromMap(Map<String, String> map){
		String company = map.get("company");
		String title = map.get("title");
		String details = map.get("details");
		String publishTime = map.get("publishTime");
		String jobSite = map.get("jobSite");
		String url = map.get("url");

		Job job = new Job();
		job.setCompany(company);
		job.setTitle(title);
		job.setDetails(details);
		job.setPublishTime(publishTime);
		job.setJobSite(jobSite);
		job.setUrl(url);
		return job;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}


	public String getPublishTime() {
		return publishTime;
	}


	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}


	public String getJobSite() {
		return jobSite;
	}


	public void setJobSite(String jobSite) {
		this.jobSite = jobSite;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}

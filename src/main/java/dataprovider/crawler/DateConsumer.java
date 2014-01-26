package dataprovider.crawler;

import java.util.ArrayList;
import java.util.List;

import dataprovider.dao.JobDaoImpl;
import dataprovider.model.Job;

public class DateConsumer {
	
	private static ArrayList<Job> queue;
	
	public static synchronized void add(List<Job> jobs){
		if(queue == null){
			queue = new ArrayList<Job>();
		}
		queue.addAll(jobs);
		if(queue.size() > 4000){
			JobDaoImpl jdi = new JobDaoImpl();
			jdi.batchInsert(queue);
			jdi.close();
			queue = new ArrayList<Job>();
		}
	}

}

package dataprovider.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dataprovider.model.Job;

public class JobDaoImpl {
	
	private Connection conn;
	
	public JobDaoImpl(){
		conn = DBSource.getConnection();
	}
	
	public void batchInsert(List<Job> jobs){
		if(jobs!=null && jobs.size() > 0){
			try {
				if(conn == null || conn.isClosed()){
					conn = DBSource.getConnection();
				}
				conn.setAutoCommit(false);
				PreparedStatement psmt = conn.prepareStatement(INSERTJOB,
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				for (Job job : jobs) {
					psmt.setString(1, job.getTitle());
					psmt.setString(2, job.getCompany());
					psmt.setString(3, job.getPublishTime());
					psmt.setString(4, job.getJobSite());
					psmt.setString(5, job.getDetails());
					psmt.setString(6, job.getUrl());
					psmt.setString(7, job.getDescription());
					psmt.addBatch();
				}
				psmt.executeBatch();
				conn.commit();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("本次没有任何更新操作.");
		}
	}
	
	static String INSERTJOB = "insert into jobs(title, company, publishTime, jobsite, details, url, description) values(?,?,?,?,?,?,?)";
	public void insert(Job job){
		try {
			PreparedStatement psmt = conn.prepareStatement(INSERTJOB);
			psmt.setString(1, job.getTitle());
			psmt.setString(2, job.getCompany());
			psmt.setString(3, job.getPublishTime());
			psmt.setString(4, job.getJobSite());
			psmt.setString(5, job.getDetails());
			psmt.execute();
			psmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

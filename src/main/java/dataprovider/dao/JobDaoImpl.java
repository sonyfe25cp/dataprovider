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
				psmt.addBatch();
			}
			psmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static String INSERTJOB = "insert into jobs(title, company, publishTime, jobsite, details) values(?,?,?,?,?)";
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

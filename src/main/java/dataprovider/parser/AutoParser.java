package dataprovider.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import dataprovider.crawler.DateConsumer;
import dataprovider.model.Job;
import bit.crawl.store.PageStoreReader;
import bit.crawl.store.StoredPage;
/**
 * 暂时只为智联招聘的职位最终页服务
 * @author omar
 *
 */
public class AutoParser {

	
	public static void main(String[] args){
		String regex = "/Users/omar/workspace/dataprovider/src/test/resources/zhilian-details";
		HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup(regex);
		
		
		String folderPath = "/Users/omar/software/crawlerengine/crawled-pages/";
		String backupPath = "/Users/omar/data/zhilian-backup/";
		File BackupFolder = new File(backupPath);
		
		File folderOld = new File(folderPath);
		for(File file : folderOld.listFiles()){
			
			PageStoreReader psr=new PageStoreReader(file);
			ArrayList<StoredPage> list=psr.loadAll();
			int i = 0;
			for(StoredPage page:list){
				String html = page.getContent();
				hpwj.parseFromContent(html);
//				hpwj.show();
				List<Map<String, String>> mapList = hpwj.getValueList();
				if(mapList.size() == 0){
					continue;
				}
				List<Job> jobs = Job.fromList(mapList);
				for(Job job : jobs){//特殊情况特殊对待吧...
					String url = page.getHeader("URL");
					job.setUrl(url);
				}
				DateConsumer.add(jobs);
				i += mapList.size();
			}
			System.out.println("find "+i+" 个职位");
			psr.close();
			try {
				FileUtils.copyFileToDirectory(file, BackupFolder);
				file.delete();
				System.out.println(file.getName() + " -- processing over");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DateConsumer.close();
		
	}
	
}

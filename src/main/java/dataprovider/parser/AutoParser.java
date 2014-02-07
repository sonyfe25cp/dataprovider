package dataprovider.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import bit.crawl.store.PageStoreReader;
import bit.crawl.store.StoredPage;
import dataprovider.crawler.DateConsumer;
import dataprovider.model.Job;
import dataprovider.utils.bf.BloomFilter;
import dataprovider.utils.bf.BloomFilterUtils;
/**
 * 暂时只为智联招聘的职位最终页服务
 * @author omar
 *
 */
public class AutoParser {

	
	public static void main(String[] args){
		
		String bloomFilterPath = "/Users/omar/software/crawlerengine/jobs/zhilianzhaopin.bf";
		
		BloomFilter<String> bloomFilter = BloomFilterUtils.bootBloomFilter(bloomFilterPath);
		
		String regex = "/Users/omar/workspace/dataprovider/src/test/resources/zhilian-details";
		HtmlParserWithJsoup hpwj = new HtmlParserWithJsoup(regex);
		
		
		String folderPath = "/Users/omar/software/crawlerengine/crawled-pages/";
//		String folderPath = "/Users/omar/software/crawlerengine/test/";
		String backupPath = "/Users/omar/data/zhilian-backup/";
		File BackupFolder = new File(backupPath);
		File folderOld = new File(folderPath);
		int newJobs = 0;
		int totalJobs = 0;
		for(File file : folderOld.listFiles()){
			
			PageStoreReader psr=new PageStoreReader(file);
			ArrayList<StoredPage> list=psr.loadAll();
			int i = 0;
			for(StoredPage page:list){
				String url = page.getHeader("URL");
				if(bloomFilter != null){
					boolean flag = bloomFilter.contains(url);
					if(!flag){
						bloomFilter.add(url);
						newJobs ++;
					}else{
						System.out.println("已经存储过这个招聘岗位信息了..."+url);
						continue;
					}
				}
				String html = page.getContent();
				hpwj.parseFromContent(html);
//				hpwj.show();
				List<Map<String, String>> mapList = hpwj.getValueList();
				if(mapList.size() == 0){
					continue;
				}
				List<Job> jobs = Job.fromList(mapList);
				for(Job job : jobs){//特殊情况特殊对待吧...
					job.setUrl(url);
				}
				DateConsumer.add(jobs);
				i += mapList.size();
				totalJobs+=i;
			}
			System.out.println("本文件中发现 "+i+" 个新职位");
			psr.close();
			try {
				FileUtils.copyFileToDirectory(file, BackupFolder);
				file.delete();
				System.out.println(file.getName() + " -- processing over");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("共找到"+totalJobs+" 个职位，其中"+newJobs+"个新增职位");
		DateConsumer.close();
		if(bloomFilter != null){
			BloomFilterUtils.outputBloomFilter(bloomFilter, bloomFilterPath);
		}
		
	}
	
}

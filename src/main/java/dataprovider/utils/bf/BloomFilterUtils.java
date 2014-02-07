package dataprovider.utils.bf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BloomFilterUtils {
	
	//序列化bloomfilter
	public static void outputBloomFilter(BloomFilter bf, String bloomPath){
      	FileOutputStream fos = null;
      	try {
      		fos = new FileOutputStream(bloomPath);
      	} catch (FileNotFoundException e) {
      		e.printStackTrace();
      	}
      	ObjectOutputStream oos = null;
      	try {
     		oos = new ObjectOutputStream(fos);
      		oos.writeObject(bf);
      		oos.close();
      		fos.close();
      		System.out.println("bloomFilter serialize successfully!!!");
      	} catch (IOException e) {
      		e.printStackTrace();
      	}
	}

	// 加载BloomFilter
	public static BloomFilter<String> bootBloomFilter(String bloomPath){
		BloomFilter<String> bloomFilter = null;
		File tmpFile = new File(bloomPath);
		if(!tmpFile.exists()){
			try {
				tmpFile.createNewFile();
				bloomFilter = new BloomFilter<String>(0.0000001, 10000 * 10000);
				return bloomFilter;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(bloomPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(fis);
			bloomFilter = (BloomFilter<String>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bloomFilter;
	}
}

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.nfs.nfs3.request.MKDIR3Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class fileOption {
	
	FileSystem fs1=null;
	@Before

	public void uploadFile() throws IllegalArgumentException, IOException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
		
	FileSystem fs = FileSystem.get(new URI("hdfs://192.168.100.101:9000"), conf, "root");}
	@Test
	public void uploadFile1() throws IllegalArgumentException, IOException {
		fs1.copyFromLocalFile(new Path("D:/data/01.txt"), new Path("/01"));
		System.out.println("success");
	}

	@Test
	public void downloadFile() throws IllegalArgumentException, IOException {
		/*
		 * ctrl+o 查看方法
		 * ctel+shift+r查找
		 * ctel+d 删除代码
		 * ctel+1 方法补全
		 * ctel+shift+/ 块注释
		 1 true 代表是否删除原文件
		 2 Path hdfs上的文件路径 
		 3 Path 本地文件路径
		 4 true 是否开启文件校验
		 
		 */
		fs1.copyToLocalFile(true, new Path("hdfs://192.168.100.101:8020/01/01.txt"), new Path("D:/01/01.txt"), true);
		
	}
	@Test
	private void delete() throws IllegalArgumentException, IOException {
		fs1.delete(new Path(""), true);	
	}
	//创建
	private void mkdir() {
		// TODO Auto-generated method stub

	}
	@After
	public void close() throws IOException {
		fs1.close();
	}

}

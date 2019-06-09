import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;



public class TestHadoop1 {
	FileSystem fs=null;
	@Before
	public void before() throws IOException, InterruptedException, URISyntaxException {
		Configuration conf=new Configuration();
		fs=FileSystem.get(new URI("hdfs://192.168.100.101:9000"), conf,"root");
	}
	@Test
	public void uploadFile() throws IOException{
		// 创建输入流
		FileInputStream inStream=new FileInputStream(new File("d:01.txt"));
		//获取输出路径
		String putFileName="hdfs://localhost:9000/01.txt";
		Path writePath=new Path(putFileName);
		//创建输出流
		FSDataOutputStream outStream=fs.create(writePath);
		//		流对接
		IOUtils.copyBytes(inStream, outStream, 4096,false);
		
		IOUtils.closeStream(inStream);
		IOUtils.closeStream(outStream);
	}@Test
	//下载第一块
	public void readFileSeek1() throws Exception{
		//获取输入流路径
		Path path = new Path("hdfs://localhost:9000/01.text");
		//打开输入流
		FSDataInputStream open = fs.open(path);
		
		//创建输出流
		FileOutputStream fos = new FileOutputStream("E:/01.text.path");
		//流对接
		byte[]  buf=	new byte[1024];
		for(int i=0;i<128*1024;i++) {
			open.read();
			open.wait();
		}
		//关闭流
		IOUtils.closeStream(open);
		IOUtils.closeStream(fos);
	}
	//下载第2块
		public void readFileSeek2() throws Exception{
			//获取输入流路径
			Path path = new Path("hdfs://localhost:9000/01.text");
			//打开输入流
			FSDataInputStream open = fs.open(path);
			
			//创建输出流
			FileOutputStream fos = new FileOutputStream("E:/01.text.path");
			 //定位
			open.seek(128*1024*1024);
			IOUtils.copyBytes(open, fos, 1024,false);
			
			//关闭流
			IOUtils.closeStream(open);
			IOUtils.closeStream(fos);
		}
		public void close() throws IOException {
			fs.close();
		}
}

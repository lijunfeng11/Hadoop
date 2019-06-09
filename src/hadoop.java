import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class hadoop {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		// hadoop的配置文件可以写在这里
		// 这个对象主要是设置一些hdfs中的配件
		Configuration conf = new Configuration();
		// 拿到文件系统
		FileSystem fs = FileSystem.get(new URI("hdfs://192.168.100.101:9000"), conf, "root");

		System.out.println(fs.toString());
		System.out.println("连接成功");
		fs.close();

	}

}

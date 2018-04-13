package exServerServlet;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class App {
	public static void main(String[] args) throws InterruptedException {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		CloseableHttpClient httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .build();

		// URIs to perform GETs on
		String uri = "http://localhost:9092/db";
		int nThreads= 1000;
		// create a thread for each URI
		GetThread[] threads = new GetThread[nThreads];
		for (int i = 0; i < threads.length; i++) {
		    HttpGet httpget = new HttpGet(uri);
		    threads[i] = new GetThread(httpClient, httpget);
		}

		// start the threads
		for (int j = 0; j < threads.length; j++) {
		    threads[j].start();
		}

		// join the threads
		for (int j = 0; j < threads.length; j++) {
		    threads[j].join();
		}
	}
}

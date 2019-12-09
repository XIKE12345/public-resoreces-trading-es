package com.jieyun.common.resoreces.trading.es.utils;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author ：ren
 * @date ：Created in 2019/5/10 10:23
 */
@Configuration
public class EsRestClient {

	@Value("${es.hostName}")
	public String hostName;

	@Bean
	public RestClient getClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		// 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议
		RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(hostName, 9200, "http"));

		// 1.设置请求头
		Header[] defaultHeaders = {new BasicHeader("header", "value")};
		clientBuilder.setDefaultHeaders(defaultHeaders);

		//2. 设置超时时间，多次尝试同一请求时应该遵守的超时。默认值为30秒，与默认套接字超时相同。若自定义套接字超时，则应相应地调整最大重试超时
		clientBuilder.setMaxRetryTimeoutMillis(60000);

		// 最后配置好的clientBuilder再build一下即可得到真正的Client
		return clientBuilder.build();
	}

	@Bean(name = "restHighLevelClient")
	public RestHighLevelClient restHighLevelClient() throws Exception{

		RestClient restClient = this.getClient();
//		RestClient restClient = this.getClient();
		// 最后配置好的clientBuilder再build一下即可得到真正的Client
		return new  RestHighLevelClient(restClient);
	}

}

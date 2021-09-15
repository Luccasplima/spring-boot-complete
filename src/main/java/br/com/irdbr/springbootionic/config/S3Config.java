package br.com.irdbr.springbootionic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@PropertySource(value={"application.properties"})
@Configuration
public class S3Config {
	
	@Value("${aws.access_key_id:}")
	private String accessKeyId;
	
	@Value("${aws.secret_access_key:}")
	private String secretAccessKey;
	
	@Value("${s3.region:}")
	private String region;
	
	@Bean
	public AmazonS3 s3client() {
		
		BasicAWSCredentials awsCred = new BasicAWSCredentials(accessKeyId, secretAccessKey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCred)).build();
		
		return s3client;
	}

}

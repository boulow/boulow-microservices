package com.boulow.document.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;

@Configuration
public class AWSConfig {

	@Autowired
    BoulowProperties boulowProperties;
	
	@Bean
    public AmazonS3 getAmazonS3Client() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(boulowProperties.getAmazonS3Props().getAccessKey(), boulowProperties.getAmazonS3Props().getSecretKey());
        AmazonS3 s3Client = null;
        try {
	        // Get Amazon S3 client and return the S3 client object
	        s3Client = AmazonS3ClientBuilder
	                .standard()
	                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
	                .withRegion(boulowProperties.getAmazonS3Props().getRegion())
	                .build();
	        if (!s3Client.doesBucketExistV2(boulowProperties.getAmazonS3Props().getBucketName())) {
	            // Because the CreateBucketRequest object doesn't specify a region, the
	            // bucket is created in the region specified in the client.
	            s3Client.createBucket(new CreateBucketRequest(boulowProperties.getAmazonS3Props().getBucketName()));
	
	            // Verify that the bucket was created by retrieving it and checking its location.
	            String bucketLocation = s3Client.getBucketLocation(new GetBucketLocationRequest(boulowProperties.getAmazonS3Props().getBucketName()));
	            System.out.println("Bucket location: " + bucketLocation);
	        }
        }catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it and returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
        
        return s3Client;
    }
}

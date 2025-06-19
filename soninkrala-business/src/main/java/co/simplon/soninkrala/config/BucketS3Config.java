package co.simplon.soninkrala.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class BucketS3Config {

    @Value("${co.simplon.soninkrala.aws.access.key}")
    String awsAccessKey;

    @Value("${co.simplon.soninkrala.aws.secret.key}")
    String awsSecretKey;

    @Bean
    public S3Client getAmazonS3Client() {
        AwsCredentials credentials = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);
        return S3Client.builder()
                .region(Region.EU_WEST_3)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}

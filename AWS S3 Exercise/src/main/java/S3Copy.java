import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.File;


public class S3Copy {

    public static void main(String[] args) {

        // Create AmazonS3 object for doing S3 operations
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();

        if (args.length != 2) {
            System.out.print("usage: <file-name> <bucket-name> ");
            return;
        }

        String bucketName = args[1];
        String key = args[0];
        File file = new File(key);

        System.out.println("Bucket Name : " + bucketName);
        System.out.println("File name: " + key);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);

        s3.putObject(putObjectRequest);

    }
}
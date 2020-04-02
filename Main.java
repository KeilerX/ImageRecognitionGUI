import java.io.FileNotFoundException;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;

public class Main {
    public static void main(String[] args){
        IamAuthenticator authenticator = new IamAuthenticator("PQNuZwCyBaiu2nZkCzwxJI6ExiAMSxyxw08IoYzeyZQX");
        VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", authenticator);
        visualRecognition.setServiceUrl("https://api.us-south.visual-recognition.watson.cloud.ibm.com/instances/827967ac-1a24-4f50-8f50-44b55426f7d3");

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .url("https://watson-developer-cloud.github.io/doc-tutorial-downloads/visual-recognition/640px-IBM_VGA_90X8941_on_PS55.jpg")
                .build();
        ClassifiedImages result = visualRecognition.classify(classifyOptions).execute().getResult();
        System.out.println(result);
    }
}

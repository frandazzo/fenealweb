package applica.feneal.services.skebby;

/**
 * Created by applica on 11/20/15.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SkebbySmsSender {




    public  void send(String skebbyUsername, String skebbyPwd, String smssenderNumber, String smsSenderAlias, String[] recs, String text) throws Exception {

        String [] recipients = recs;//new String[]{"391234567890","391234567891"};

        String username = skebbyUsername;
        String password = skebbyPwd;


        // SMS CLASSIC dispatch with custom numeric sender
        String result ;
        if (StringUtils.isEmpty(smsSenderAlias))
          result = skebbyGatewaySendSMS(username, password, recipients, text, "send_sms_classic_report", smssenderNumber, null);
        else
            result = skebbyGatewaySendSMS(username, password, recipients, text, "send_sms_classic_report", null, smsSenderAlias);

        //String result = skebbyGatewaySendSMS(username, password, recipients, text, "send_sms_classic", null, smsSenderAlias);


        System.out.println("result: "+result);
    }

    protected  String skebbyGatewaySendSMS(String username, String password, String [] recipients, String text, String smsType, String senderNumber, String senderString) throws IOException{
        return skebbyGatewaySendSMS(username, password, recipients, text, smsType,  senderNumber,  senderString, "UTF-8");
    }

    protected  String skebbyGatewaySendSMS(String username, String password, String [] recipients, String text, String smsType, String senderNumber, String senderString, String charset) throws IOException{

        if (!charset.equals("UTF-8") && !charset.equals("ISO-8859-1")) {

            throw new IllegalArgumentException("Charset not supported.");
        }

        String endpoint = "http://gateway.skebby.it/api/send/smseasy/advanced/http.php";
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10*1000);
        DefaultHttpClient httpclient = new DefaultHttpClient(params);
        HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
        paramsBean.setVersion(HttpVersion.HTTP_1_1);
        paramsBean.setContentCharset(charset);
        paramsBean.setHttpElementCharset(charset);

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("method", smsType));
        formparams.add(new BasicNameValuePair("username", username));
        formparams.add(new BasicNameValuePair("password", password));
        if(null != senderNumber)
            formparams.add(new BasicNameValuePair("sender_number", senderNumber));
        if(null != senderString)
            formparams.add(new BasicNameValuePair("sender_string", senderString));

        for (String recipient : recipients) {
            formparams.add(new BasicNameValuePair("recipients[]", recipient));
        }
        formparams.add(new BasicNameValuePair("text", text));
        formparams.add(new BasicNameValuePair("charset", charset));


        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charset);
        HttpPost post = new HttpPost(endpoint);
        post.setEntity(entity);

        HttpResponse response = httpclient.execute(post);
        HttpEntity resultEntity = response.getEntity();
        if(null != resultEntity){
            return EntityUtils.toString(resultEntity);
        }
        return null;
    }

}

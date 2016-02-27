package HipChartV2.HipChartV2;

import com.hipchart.v2.model.HipChatClient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
      HipchatUtils.sendMessageRoom("", "test amessage", HipChatClient.Color_Msg_Green);
    }
}

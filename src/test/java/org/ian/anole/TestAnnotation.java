package org.ian.anole;


import okhttp3.*;
import org.ian.anole.time.DateUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Mike Ian
 * @date 2022/10/12
 **/
public class TestAnnotation {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException {
//        String result = DateUtils.getTimeOffsetStr(DateUtils.now(), TimeUnit.DAYS, 1,
//                DateUtils.YMD,
//                DateUtils.YMD);
//        System.out.println(result);

        sendWechatMessage();
    }

    public static void sendWechatMessage(){

//        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send";
        String url = "https://www.baidu.com";

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.toString());
//                System.out.println(response.body().string());
            }
        });

    }

}

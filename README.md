# YzyHttp
手写网络访问框架  
基于HttpUrlConnection封装。支持对泛型数据的json解析。支持多任务请求队列。  
- 如果觉得有用，不吝啬在右上角给我一个Star。谢谢！！  
![](https://raw.githubusercontent.com/yzytmac/yzytmac.github.io/master/images/star.png)  
使用方式：  

        //url字符串
        String urlString = "https://gitee.com/yzytmac/resource/raw/master/test";
        //返回数据的实体类的type
        Type vType = new TypeToken<Userbean>() {}.getType();
        //请求回调监听
        IHttpListener<Userbean> vListener = new IHttpListener<Userbean>() {
            @Override
            public void onSuccess(Userbean pResponse) {
                
            }

            @Override
            public void onFail(int code, String msg) {

            }
        };
        //Builder模式构建网络请求，填入三个必要参数，然后调用request方法发起请求。
        new YzyHttpClient.RequestBuilder<>(urlString, vType, vListener).request();
        //如果有其他参数需要设置可以在request方法调用之前继续设置
        new YzyHttpClient.RequestBuilder<>(urlString, vType, vListener)
            .setRequestMethod()//设置请求方式GET或POST
            .setHeards()//设置请求头，HashMap键值对形式
            .setParams()//设置请求的参数，HashMap键值对形式。POST和GET两种请求的参数都支持，都是HashMap键值对
            .setTimeOut()//设置超时时间，默认是20000
            .request();//发起网络请求  
后期会继续完善，支持更多功能及优化，欢迎指出问题。邮箱 yzytmac@163.com   



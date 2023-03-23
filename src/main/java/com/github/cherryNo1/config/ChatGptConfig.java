package com.github.cherryNo1.config;

import com.github.cherryNo1.ChatGPT;
import com.github.cherryNo1.utils.Model;
import lombok.Data;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author maqingbo
 * @date 2023/3/18 13:02
 * @email qingbo.my@gmail.com
 */
@Data
@Component
@ConfigurationProperties(prefix = "openai")
public class ChatGptConfig {
    /**
     * your openai key
     */
    private String key;
    /**
     * Chat completion API URL
     * <p>
     * see: <a href=https://platform.openai.com/docs/api-reference/chat/create>https://platform.openai.com/docs/api-reference/chat/create</a>
     */
    private String url;

    private boolean  enableProxy;

    private Model model = Model.GPT_3_5_TURBO;

    @Bean
    public ChatGPT chatGPT(@Autowired(required = false) @Nullable Proxy proxy){
        ChatGPT chatGPT = new ChatGPT();
        chatGPT.setApiUrl(url);
        chatGPT.setApiKey(key);
        chatGPT.setModel(model);
        if (proxy!=null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder().
                    proxy(proxy)
                    .build();
            chatGPT.setClient(okHttpClient);
        }
        return chatGPT;
    }


    /**
     * @author maqingbo
     */
    @Data
    @Component
    @ConfigurationProperties(prefix = "openai.proxy")
    static class ProxyConfig {
        private String proxyHost;
        private Integer proxyPort;
        private Proxy.Type type;

        @Bean
        @ConditionalOnProperty(name = {"openai.enable-proxy","openai.enableProxy"} ,havingValue = "true")
        public Proxy proxy() {
            Assert.notNull(proxyHost,"配置文件中未找到opai.proxy.proxy-host相关配置，请配置opai.proxy.proxy-port，或者设置enable-proxy为false来取消使用代理");
            Assert.notNull(proxyPort,"配置文件中未找到opai.proxy.proxy-port相关配置，请配置opai.proxy.proxy-port，或者设置enable-proxy为false来取消使用代理");
            Assert.notNull(type,"配置文件中未找到opai.proxy.type相关配置，请配置opai.proxy.proxy-port，或者设置enable-proxy为false来取消使用代理");
            return new Proxy(type, new InetSocketAddress(proxyHost, proxyPort));
        }
    }

}

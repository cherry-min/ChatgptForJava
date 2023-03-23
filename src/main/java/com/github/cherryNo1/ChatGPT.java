package com.github.cherryNo1;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cherryNo1.constants.Constants;
import com.github.cherryNo1.dto.ChatGPTRequest;
import com.github.cherryNo1.dto.ChatGPTResponse;
import com.github.cherryNo1.dto.Message;
import com.github.cherryNo1.exception.BaseException;
import com.github.cherryNo1.utils.Error;
import com.github.cherryNo1.utils.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


/**
 * 参考 {@link com.qaai.admin.chatgpt.infra.config.ChatGptConfig}
 *
 *
 * @author maqingbo
 * @date 2023/3/11 12:33
 * @email qingbo.my@gmail.com
 */
@Slf4j
@Data
@NoArgsConstructor
@SuppressWarnings("all")
public class ChatGPT {
    private String apiUrl;
    private String apiKey;
    private  OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Message> userMessageList ;
    private Model model;

    /**
     * 以user角色发送
     * @param content
     * @param messageList 用户和AI对话的消息上下文
     * @return
     */
    public ChatGPTResponse chat(String content, List<Message> messageList) {
        this.userMessageList = messageList;
        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder().model(model.getName()).messages(userMessageList).build();
        Request request = buildRequest(chatGPTRequest);
        return doChat(request);
    }

    /**
     * 构建http请求
     *
     * @param chatGPTRequest
     * @return
     */
    private Request buildRequest(ChatGPTRequest chatGPTRequest) {

        RequestBody body = RequestBody.create(JSON.toJSONString(chatGPTRequest), MediaType.get("application/json; charset=utf-8"));


        return new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();
    }

    /**
     * 发送请求到openai
     *
     * @param request
     * @return
     */
    private ChatGPTResponse doChat(Request request) {
        ChatGPTResponse chatGPTResponse =null;
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                if (response.body() == null) {
                    log.error("请求失败: {}, 请重试", response.message());
                    throw new BaseException(response.code(), "请求失败");
                } else {
                    log.error("请求失败: {}, 请重试", response.body().string());
                    throw new BaseException(response.code(), response.body().string());
                }
            } else {
                assert response.body() != null;
                String bodyString = response.body().string();
                chatGPTResponse = JSONObject.parseObject(bodyString, ChatGPTResponse.class);
            }

//            saveSessionInformation(chatGPTResponse);

            return chatGPTResponse;
        } catch (IOException e) {
            log.error("请求失败: {}", e.getMessage());
            throw new BaseException(Error.SERVER_HAD_AN_ERROR.getCode(), e.getMessage());
        }
    }

}

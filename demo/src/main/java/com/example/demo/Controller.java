package com.example.demo;

import cn.hutool.jwt.JWTUtil;
import com.github.cherryNo1.ChatGPT;
import com.github.cherryNo1.constants.Constants;
import com.github.cherryNo1.dto.ChatGPTResponse;
import com.github.cherryNo1.dto.Message;
import com.github.cherryNo1.dto.RequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maqingbo
 * @date 2023/3/18 16:16
 * @email qingbo.my@gmail.com
 */
@RestController
public class Controller {

    @Autowired
    ChatGPT chatGPT;

    /**
     * @author maqingbo
     * @date 2023/3/18 16:16
     * @email qingbo.my@gmail.com
     * @param requestDto
     * @param request
     * @return
     */
    @PostMapping("/chat")
    public ChatGPTResponse chat(RequestDto requestDto, HttpServletRequest request){
        //获取token，开始认证
        String authorization = request.getHeader("Authorization");
        //认证成功...


        //为了联系上下文并且实现会话隔离(多个用户聊天不会串台)，将用户发来的消息每一条消息存放到数据库(或者其他地方)，唯一标识为token中Payloads的userid,
        //假设取出的userid=1
        Long userId = 1L;
        //首先将这次用户的问题存入数据库
        ChatMessage userMessage = new ChatMessage();
        userMessage.setUserId(userId);
        userMessage.setContent(requestDto.getContent());
        userMessage.setRole(Constants.Role.ROLE_USER);
        saveMessage(userMessage);
        //假设下面是从数据库中userid为1的最近10条消息，取出的消息不要太多，会浪费chatGpt的token，但是这样可能会联系不了太多的上下文
        //下面将用户和AI的3次对话(用户3次，AI 3次)取出来放到List里
        List<ChatMessage> chatMessages = new ArrayList<>();
        //模拟数据库查询userId=1的最近消息
        queryRecentMeaasgeTop6(chatMessages,userId);

        List<Message> list= chatMessages.stream().map(cm -> {
            Message message = new Message();
            BeanUtils.copyProperties(cm, message);
            return message;
        }).collect(Collectors.toList());

        //将list的对话内容发送给ChatGpt
        ChatGPTResponse chat = chatGPT.chat(requestDto.getContent(), list);

        //将chatgpt返回回来的答案继续存入数据库，以便下次请求携带
        List<ChatGPTResponse.Choice> choices = chat.getChoices();
        for (ChatGPTResponse.Choice choice : choices) {
            Message message = choice.getMessage();
            ChatMessage chatMessage = new ChatMessage();
            BeanUtils.copyProperties(message,chatMessage);
            chatMessage.setUserId(userId);
            //之后将 ChatMessage存入数据库
            saveMessage(chatMessage);
        }

        return  chat;
    }

    private void saveMessage(ChatMessage chatMessage) {
        //...保存到数据库
    }

    /**
     * 模拟从数据库中查userId对应最近的消息，数据库可用limit
     * @author maqingbo
     * @date 2023/3/18 16:16
     * @email qingbo.my@gmail.com
     * @param list
     * @param userId
     */
    private void queryRecentMeaasgeTop6(List<ChatMessage> list,long userId) {
        //用户问的
        list.add((ChatMessage) new ChatMessage().setLastChatTime(new Date()).setUserId(userId).setRole(Constants.Role.ROLE_USER).setContent("我想喝咖啡"));
        //AI答的
        list.add((ChatMessage) new ChatMessage().setLastChatTime(new Date()).setUserId(userId).setRole(Constants.Role.ROLE_ASSISTANT).setContent("很好的选择，你可以去附近的咖啡馆或者咖啡店，享受一杯香浓的咖啡。如果你在家里，也可以自己煮咖啡哦！"));

        list.add((ChatMessage) new ChatMessage().setLastChatTime(new Date()).setUserId(userId).setRole(Constants.Role.ROLE_USER).setContent("请推荐几个"));
        list.add((ChatMessage) new ChatMessage().setLastChatTime(new Date()).setUserId(userId).setRole(Constants.Role.ROLE_ASSISTANT).setContent("如果你在中国，以下是几个咖啡品牌或咖啡店，供你参考：\\n\\n1. 星巴克 (Starbucks)\\n2. COSTA COFFEE\\n3. Pacific Coffee\\n4. 85°C Bakery Cafe (85度C)\\n5. 伊莎咖啡 (ECCO Coffee)\\n\\n当然，还有很多其他的咖啡品牌或咖啡店可以选择。如果你喜欢探索新的咖啡馆或品牌，可以尝试一下。"));

        list.add((ChatMessage) new ChatMessage().setLastChatTime(new Date()).setUserId(userId).setRole(Constants.Role.ROLE_USER).setContent("小孩可以用吗"));


    }

}

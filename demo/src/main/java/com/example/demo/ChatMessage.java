package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.cherryNo1.dto.Message;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 假设有数据表 CHAT_MESSAGE
 * @author maqingbo
 * @date 2023/3/18 17:39
 * @email qingbo.my@gmail.com
 */
@Data
@Accessors(chain = true)
public class ChatMessage extends Message {
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Date lastChatTime;
}

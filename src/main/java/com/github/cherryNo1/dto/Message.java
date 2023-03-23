package com.github.cherryNo1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 消息实体类
 *
 * @author maqingbo
 * @date 2023/3/11 13:39
 * @email qingbo.my@gmail.com
 * @github
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Message {

    @JsonProperty(value = "role")
    public String role;

    @JsonProperty(value = "content")
    public String content;


}

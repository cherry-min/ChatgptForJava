package com.github.cherryNo1.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 响应实体类
 * @author maqingbo
 * @date 2023/3/11 12:42
 * @email qingbo.my@gmail.com
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatGPTResponse {
    @JsonProperty(value = "id")
    public String id;
    @JsonProperty(value = "object")
    public String object;
    @JsonProperty(value = "created")
    public Long created;
    @JsonProperty(value = "model")
    public String model;
    @JsonProperty(value = "usage")
    public Usage usage;
    @JsonProperty(value = "choices")
    public List<Choice> choices;



    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
 public static   class Choice{
        @JsonProperty(value = "message")
        public Message message;
        @JsonProperty(value = "finish_reason")
        public String finishReason;
        @JsonProperty(value = "index")
        public Integer index;
    }
    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    public  static  class Usage{
        @JsonProperty(value = "prompt_tokens")
        private Long promptTokens;
        @JsonProperty(value = "completion_tokens")
        private Long completionTokens;
        @JsonProperty(value = "total_tokens")
        private Long totalTokens;
    }

}

package com.github.cherryNo1.dto;

import lombok.Data;

import java.util.List;

/**
 * @author maqingbo
 * @date 2023/3/9 22:49
 * @email qingbo.my@gmail.com
 */

@Data
public class ResponseDto {
    private String role;

    private String id;

    private String parentMessageId;

    private String text;

    private String delta;

    private Detail detail;

    @Data
    class Delta {
        private String content;

    }

    @Data
    class Choices {
        private Delta delta;

        private int index;

        private String finish_reason;

    }

    @Data
    class Detail {
        private String id;

        private String object;

        private int created;

        private String model;

        private List<Choices> choices;


    }

}

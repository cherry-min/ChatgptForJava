package com.github.cherryNo1.utils;

/**
 * ChatGPT 模型
 * @author maqingbo
 * @date 2023/3/11
 * @email qingbo.my@gmail.com
 */

public enum Model {
    /**
     * gpt-3.5-turbo
     */
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    /**
     * text-davinci-003
     */
    TEXT_DAVINCI_003("text-davinci-003"),
    /**
     * text-davinci-002
     */
    TEXT_DAVINCI_002("text-davinci-002"),
    /**
     * text-davinci-001
     */
    TEXT_DAVINCI_001("text-davinci-001");

    private final String name;

    private Model(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

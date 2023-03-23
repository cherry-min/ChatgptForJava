# 请耐心阅读本文档
[English](./README.md) | 简体中文 
## 引入依赖
```xml
      <dependency>
            <groupId>io.github.cherryNo1</groupId>
            <artifactId>chatGptForJava</artifactId>
            <version>1.0.0</version>
      </dependency>
```
yaml配置文件 请配置您的API_KEY,api的接口，使用的模型，目前暂不支持GPT4.
openai.proxy是代理的配置，如果您在国内调试，请配置该项

```yaml
openai:
  key: your api key
  url: https://api.openai.com/v1/chat/completions
  model: gpt_3_5_turbo
#  enableProxy: true
#  proxy:
#    proxy-host: 127.0.0.1
#    proxy-port: 7890
#    type: http
```

## 隔离会话、联系上下文聊天
请仔细阅读`demo`中的代码.需要用到数据库,或者redis来存储用户与ChatGpt的对话

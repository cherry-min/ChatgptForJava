
# Please read this document patiently.
English | [简体中文](./README-zh_CN.md)

## Introduction of Dependencies
```xml
<dependency>
    <groupId>io.github.cherryNo1</groupId>
    <artifactId>chatGptForJava</artifactId>
    <version>1.0.0</version>
</dependency>
```

Please configure your API_KEY, API endpoint, and the model you will be using in the YAML configuration file. Currently, GPT4 is not supported.

If you are debugging in China, please configure openai.proxy.

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

## Isolating Sessions and Connecting Contextual Conversations
Please carefully read the code in the `demo`. You will need to use a database or Redis to store the conversation between the user and ChatGpt.

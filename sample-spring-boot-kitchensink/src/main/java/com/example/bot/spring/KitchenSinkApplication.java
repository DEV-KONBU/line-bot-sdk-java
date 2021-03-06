/*
 * Copyright 2018 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.LineBotProperties;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootApplication
@LineMessageHandler
public class KitchenSinkApplication {
    
    @Autowired
    public LineMessagingClient lineMessagingClient;
    
    @Autowired
    private LineBotProperties lineBotProperties;

    static Path downloadedContentDir;

    public static void main(String[] args) throws IOException {
        downloadedContentDir = Files.createTempDirectory("line-bot");
        SpringApplication.run(KitchenSinkApplication.class, args);
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        final LineMessagingClient client = LineMessagingClient
                .builder(lineBotProperties.getChannelToken())
                .build();

        final TextMessage textMessage = new TextMessage(event.getMessage().getText());

        // 返信用画像(image)のURL
//        final String originalContentUrl = "https://art4.photozou.jp/pub/784/784/photo/17561757.v1524899011.jpg";
//        final String previewImageUrl = "https://art4.photozou.jp/pub/784/784/photo/17561757_thumbnail.v1524899011.jpg";

        // 返信用画像(image)メッセージ
//        ImageMessage imageMessage = new ImageMessage(originalContentUrl, previewImageUrl);

//        final ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(), Arrays.asList(imageMessage, textMessage));
        final ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(), Arrays.asList(textMessage));

        final BotApiResponse botApiResponse;
//        String replyToken = event.getReplyToken();
        
        try {
//            botApiResponse = client.replyMessage(replyMessage).get();
              if (("マルちゃん").equals(client.replyMessage(replyMessage).get())
              || ("マル").equals(client.replyMessage(replyMessage).get())) {
//                  botApiResponse = client.replyMessage(replyMessage).get();
                  new TextMessage("呼んだ？");
              
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }

        log.info("handleDefaultMessageEvent = {}", event);
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        log.info("handleDefaultMessageEvent = {}", event);
    }

}

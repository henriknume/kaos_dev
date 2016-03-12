/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaos.resources;

import kaos.core.Chat;

/**
 *
 * @author Davidf
 */
public enum SingletonChat {
    
    INSTANCE;

    private final Chat chat = Chat.newInstance();

    public Chat getChat() {
        return chat;
    }
}
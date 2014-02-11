package de.minestar.Webpanel.services;

import de.minestar.Webpanel.pagehandler.main.ChatPageHandler;

public class ServerService extends RegisterService {

    @Override
    protected void registerTemplates() {
        // register server-templates
        this.registerTemplate("chat", "/server/chat.html");
    }

    @Override
    protected void registerPages() {
        // register server-pages
        this.registerPage(new ChatPageHandler(), "/chat.html");
    }
}

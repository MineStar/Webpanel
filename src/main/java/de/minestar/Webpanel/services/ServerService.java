package de.minestar.Webpanel.services;

public class ServerService extends RegisterService {

    @Override
    protected void registerTemplates() {
        // register server-templates
        this.registerTemplate("chat", "/server/chat.html");
    }

    @Override
    protected void registerPages() {
        // register server-pages
    }
}

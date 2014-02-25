package de.minestar.Webpanel.services;

import de.minestar.Webpanel.pagehandler.AbstractHTMLHandler;
import de.minestar.Webpanel.template.Template;
import de.minestar.Webpanel.template.TemplateHandler;
import de.minestar.Webpanel.units.HandlerList;

public abstract class RegisterService {

    public RegisterService() {
        this.registerTemplates();
        this.registerPages();
    }

    protected void registerTemplate(String name, String path) {
        TemplateHandler.addTemplate(new Template(name, path));
    }

    protected void registerPage(AbstractHTMLHandler handler, String path) {
        HandlerList.registerHandler(path, handler);
    }

    protected abstract void registerTemplates();

    protected abstract void registerPages();
}

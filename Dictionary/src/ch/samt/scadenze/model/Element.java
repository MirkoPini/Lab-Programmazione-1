package ch.samt.scadenze.model;

import java.util.Date;

public abstract class Element {
    private String idCode;
    private Date creationDate;

    public Element(String idCode, Date creationDate){
        this.idCode = idCode;
        this.creationDate = creationDate;
    }

    public abstract Date dataScadenza();

    public boolean scaduto(Element el){
        Date today = new Date();
        if (today.after(el.dataScadenza())) {
            return true;
        }else{
            return false;
        }
    }
}

package DTO;

public class NoticeDTO {
    private int N_NO;
    private int NO;
    private String NAME;
    private String N_TITLE;
    private String N_CONTENT;
    private String N_DATE;
    private String D_DATE;
    private String ID;
    private int REPLY;
    private int RE_NO;


    public NoticeDTO() {
        super();
    }
    
    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public int getN_NO() {
        return N_NO;
    }

    public  void setN_NO(int n_NO) {
        N_NO = n_NO;
    }

    public int getNO() {
        return NO;
    }

    public void setNO(int nO) {
        NO = nO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String nAME) {
        NAME = nAME;
    }

    public String getN_TITLE() {
        return N_TITLE;
    }

    public void setN_TITLE(String n_TITLE) {
        N_TITLE = n_TITLE;
    }

    public String getN_CONTENT() {
        return N_CONTENT;
    }

    public void setN_CONTENT(String n_CONTENT) {
        N_CONTENT = n_CONTENT;
    }

    public String getN_DATE() {
        return N_DATE;
    }

    public void setN_DATE(String n_DATE) {
        N_DATE = n_DATE;
    }

    public String getD_DATE() {
        return D_DATE;
    }

    public void setD_DATE(String d_DATE) {
        D_DATE = d_DATE;
    }

    public int getREPLY() {
        return REPLY;
    }

    public void setREPLY(int rEPLY) {
        REPLY = rEPLY;
    }

    public int getRE_NO() {
        return RE_NO;
    }

    public void setRE_NO(int rE_NO) {
        RE_NO = rE_NO;
    }
}

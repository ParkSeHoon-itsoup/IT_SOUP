package DTO;

public class FileDTO {
    private int N_NO;
    private String F_NAME;
    private String F_REALNAME;
    private int F_NO;
    
    public FileDTO(){
        super();
    }

    public int getN_NO() {
        return N_NO;
    }

    public void setN_NO(int n_NO) {
        N_NO = n_NO;
    }

    public String getF_NAME() {
        return F_NAME;
    }

    public void setF_NAME(String f_NAME) {
        F_NAME = f_NAME;
    }

    public String getF_REALNAME() {
        return F_REALNAME;
    }

    public void setF_REALNAME(String f_REALNAME) {
        F_REALNAME = f_REALNAME;
    }

    public int getF_NO() {
        return F_NO;
    }

    public void setF_NO(int f_NO) {
        F_NO = f_NO;
    }
}

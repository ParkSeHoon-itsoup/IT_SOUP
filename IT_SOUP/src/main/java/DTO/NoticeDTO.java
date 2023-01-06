package DTO;

public class NoticeDTO {
    private static int N_NO;
    private static int NO;
    private static String NAME;
    private static String N_TITLE;
    private static String N_CONTENT;
    private static String N_DATE;
    private static String D_DATE;
    public static int getN_NO() {
        return N_NO;
    }
    
    public NoticeDTO(int N_NO, int NO, String NAME, String N_TITLE, String N_CONTENT, String N_DATE, String D_DATE) {
        super();
        
        NoticeDTO.N_NO = N_NO;
        NoticeDTO.NO = NO;
        NoticeDTO.NAME = NAME;
        NoticeDTO.N_TITLE = N_TITLE;
        NoticeDTO.N_CONTENT = N_CONTENT;
        NoticeDTO.N_DATE = N_DATE;
        NoticeDTO.D_DATE = D_DATE;
    }

    public static void setN_NO(int n_NO) {
        N_NO = n_NO;
    }

    public static int getNO() {
        return NO;
    }

    public static void setNO(int nO) {
        NO = nO;
    }

    public static String getNAME() {
        return NAME;
    }

    public static void setNAME(String nAME) {
        NAME = nAME;
    }

    public static String getN_TITLE() {
        return N_TITLE;
    }

    public static void setN_TITLE(String n_TITLE) {
        N_TITLE = n_TITLE;
    }

    public static String getN_CONTENT() {
        return N_CONTENT;
    }

    public static void setN_CONTENT(String n_CONTENT) {
        N_CONTENT = n_CONTENT;
    }

    public static String getN_DATE() {
        return N_DATE;
    }

    public static void setN_DATE(String n_DATE) {
        N_DATE = n_DATE;
    }

    public static String getD_DATE() {
        return D_DATE;
    }

    public static void setD_DATE(String d_DATE) {
        D_DATE = d_DATE;
    }

    public NoticeDTO() {
        super();
    }
}

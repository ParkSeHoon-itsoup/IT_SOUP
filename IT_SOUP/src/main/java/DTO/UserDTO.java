package DTO;

public class UserDTO {
    private static int NO;
    private static String ID;
    private static String PASSWORD;
    private static String NAME;
    private static String HPNO;
    private static String SSN;
    private static String EMAIL;
    private static String GRADE_CD;
    private static String LEVEL;
    private static String CHG_PW_YN;
    private static String ADDR;
    private static String ADDR2;
    private static String JOIN_DD;;
    private static String MOD_DD;
    
    public UserDTO(){
        super();
    }
    
    public UserDTO(int NO, String ID, String PASSWORD, String NAME, String HPNO, String SSN, String EMAIL, String GRADE_CD, String LEVEL,String CHG_PW_YN, String ADDR, String ADDR2, String JOIN_DD, String MOD_DD) {
        super();
        UserDTO.NO = NO ;
        UserDTO.ID = ID ;
        UserDTO.PASSWORD =  PASSWORD;
        UserDTO.NAME = NAME ;
        UserDTO.HPNO = HPNO ;
        UserDTO.SSN = SSN ;
        UserDTO.EMAIL = EMAIL ;
        UserDTO.GRADE_CD = GRADE_CD ;
        UserDTO.LEVEL = LEVEL;
        UserDTO.CHG_PW_YN =  CHG_PW_YN;
        UserDTO.ADDR =  ADDR;
        UserDTO.ADDR2 = ADDR2;
        UserDTO.JOIN_DD = JOIN_DD;
        UserDTO.MOD_DD = MOD_DD;
    }

    public static String getADDR2() {
        return ADDR2;
    }

    public static void setADDR2(String aDDR2) {
        ADDR2 = aDDR2;
    }

    public static String getJOIN_DD() {
        return JOIN_DD;
    }

    public static void setJOIN_DD(String jOIN_DD) {
        JOIN_DD = jOIN_DD;
    }

    public static String getMOD_DD() {
        return MOD_DD;
    }

    public static void setMOD_DD(String mOD_DD) {
        MOD_DD = mOD_DD;
    }

    public int getNO() {
        return NO;
    }

    public  void setNO(int nO) {
        NO = nO;
    }

    public String getID() {
        return ID;
    }

    public  void setID(String iD) {
        ID = iD;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String nAME) {
        NAME = nAME;
    }

    public String getHPNO() {
        return HPNO;
    }

    public void setHPNO(String hPNO) {
        HPNO = hPNO;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String sSN) {
        SSN = sSN;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String eMAIL) {
        EMAIL = eMAIL;
    }

    public String getGRADE_CD() {
        return GRADE_CD;
    }

    public void setGRADE_CD(String gRADE_CD) {
        GRADE_CD = gRADE_CD;
    }

    public String getLEVE() {
        return LEVEL;
    }

    public void setLEVE(String lEVEL) {
        LEVEL = lEVEL;
    }

    public String getCHG_PW_YN() {
        return CHG_PW_YN;
    }

    public void setCHG_PW_YN(String cHG_PW_YN) {
        CHG_PW_YN = cHG_PW_YN;
    }

    public String getADDR() {
        return ADDR;
    }

    public void setADDR(String aDDR) {
        ADDR = aDDR;
    }
}

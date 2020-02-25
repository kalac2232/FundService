package cn.kalac.bean;

import java.util.List;

/**
 * @author ghn
 * @date 2019/12/12 10:43
 */
public class TTFundSearchResult {
    /**
     * ErrCode : 0
     * ErrMsg : null
     * Datas : [{"_id":"320003","CODE":"320003","NAME":"诺安先锋混合","JP":"NAXFHH","CATEGORY":700,"CATEGORYDESC":"基金","STOCKMARKET":"","BACKCODE":"","MatchCount":1,"FundBaseInfo":{"_id":"320003","FCODE":"320003","FSRQ":"2020-02-05","DWJZ":1.3834,"NAVURL":"http://fund.eastmoney.com/HH_jzzzl.html#os_0;isall_0;ft_;pt_3","SHORTNAME":"诺安先锋混合","JJGSID":"80049689","JJGS":"诺安基金","JJJLID":"30040459","JJJL":"杨谷","FUNDTYPE":"002","ISBUY":"1","FTYPE":"混合型","MINSG":100,"JJGSBID":40,"OTHERNAME":"诺安先锋"},"StockHolder":"","ZTJJInfo":[{"TTYPE":"ed197e208a437da2","TTYPENAME":"富时概念"},{"TTYPE":"de01b5daebb833ac","TTYPENAME":"证金持股"}]}]
     */

    private int ErrCode;
    private Object ErrMsg;
    private List<DatasBean> Datas;

    public int getErrCode() {
        return ErrCode;
    }

    public void setErrCode(int ErrCode) {
        this.ErrCode = ErrCode;
    }

    public Object getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(Object ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    public List<DatasBean> getDatas() {
        return Datas;
    }

    public void setDatas(List<DatasBean> Datas) {
        this.Datas = Datas;
    }

    public static class DatasBean {
        /**
         * _id : 320003
         * CODE : 320003
         * NAME : 诺安先锋混合
         * JP : NAXFHH
         * CATEGORY : 700
         * CATEGORYDESC : 基金
         * STOCKMARKET :
         * BACKCODE :
         * MatchCount : 1
         * FundBaseInfo : {"_id":"320003","FCODE":"320003","FSRQ":"2020-02-05","DWJZ":1.3834,"NAVURL":"http://fund.eastmoney.com/HH_jzzzl.html#os_0;isall_0;ft_;pt_3","SHORTNAME":"诺安先锋混合","JJGSID":"80049689","JJGS":"诺安基金","JJJLID":"30040459","JJJL":"杨谷","FUNDTYPE":"002","ISBUY":"1","FTYPE":"混合型","MINSG":100,"JJGSBID":40,"OTHERNAME":"诺安先锋"}
         * StockHolder :
         * ZTJJInfo : [{"TTYPE":"ed197e208a437da2","TTYPENAME":"富时概念"},{"TTYPE":"de01b5daebb833ac","TTYPENAME":"证金持股"}]
         */

        private String _id;
        private String CODE;
        private String NAME;
        private String JP;
        private int CATEGORY;
        private String CATEGORYDESC;
        private int MatchCount;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getJP() {
            return JP;
        }

        public void setJP(String JP) {
            this.JP = JP;
        }

        public int getCATEGORY() {
            return CATEGORY;
        }

        public void setCATEGORY(int CATEGORY) {
            this.CATEGORY = CATEGORY;
        }

        public String getCATEGORYDESC() {
            return CATEGORYDESC;
        }

        public void setCATEGORYDESC(String CATEGORYDESC) {
            this.CATEGORYDESC = CATEGORYDESC;
        }


        public static class FundBaseInfoBean {
            /**
             * _id : 320003
             * FCODE : 320003
             * FSRQ : 2020-02-05
             * DWJZ : 1.3834
             * NAVURL : http://fund.eastmoney.com/HH_jzzzl.html#os_0;isall_0;ft_;pt_3
             * SHORTNAME : 诺安先锋混合
             * JJGSID : 80049689
             * JJGS : 诺安基金
             * JJJLID : 30040459
             * JJJL : 杨谷
             * FUNDTYPE : 002
             * ISBUY : 1
             * FTYPE : 混合型
             * MINSG : 100
             * JJGSBID : 40
             * OTHERNAME : 诺安先锋
             */

            private String _id;
            private String FCODE;
            private String FSRQ;
            private double DWJZ;
            private String NAVURL;
            private String SHORTNAME;
            private String JJGSID;
            private String JJGS;
            private String JJJLID;
            private String JJJL;
            private String FUNDTYPE;
            private String ISBUY;
            private String FTYPE;
            private int MINSG;
            private String JJGSBID;
            private String OTHERNAME;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getFCODE() {
                return FCODE;
            }

            public void setFCODE(String FCODE) {
                this.FCODE = FCODE;
            }

            public String getFSRQ() {
                return FSRQ;
            }

            public void setFSRQ(String FSRQ) {
                this.FSRQ = FSRQ;
            }

            public double getDWJZ() {
                return DWJZ;
            }

            public void setDWJZ(double DWJZ) {
                this.DWJZ = DWJZ;
            }

            public String getNAVURL() {
                return NAVURL;
            }

            public void setNAVURL(String NAVURL) {
                this.NAVURL = NAVURL;
            }

            public String getSHORTNAME() {
                return SHORTNAME;
            }

            public void setSHORTNAME(String SHORTNAME) {
                this.SHORTNAME = SHORTNAME;
            }

            public String getJJGSID() {
                return JJGSID;
            }

            public void setJJGSID(String JJGSID) {
                this.JJGSID = JJGSID;
            }

            public String getJJGS() {
                return JJGS;
            }

            public void setJJGS(String JJGS) {
                this.JJGS = JJGS;
            }

            public String getJJJLID() {
                return JJJLID;
            }

            public void setJJJLID(String JJJLID) {
                this.JJJLID = JJJLID;
            }

            public String getJJJL() {
                return JJJL;
            }

            public void setJJJL(String JJJL) {
                this.JJJL = JJJL;
            }

            public String getFUNDTYPE() {
                return FUNDTYPE;
            }

            public void setFUNDTYPE(String FUNDTYPE) {
                this.FUNDTYPE = FUNDTYPE;
            }

            public String getISBUY() {
                return ISBUY;
            }

            public void setISBUY(String ISBUY) {
                this.ISBUY = ISBUY;
            }

            public String getFTYPE() {
                return FTYPE;
            }

            public void setFTYPE(String FTYPE) {
                this.FTYPE = FTYPE;
            }

            public int getMINSG() {
                return MINSG;
            }

            public void setMINSG(int MINSG) {
                this.MINSG = MINSG;
            }

            public String getJJGSBID() {
                return JJGSBID;
            }

            public void setJJGSBID(String JJGSBID) {
                this.JJGSBID = JJGSBID;
            }

            public String getOTHERNAME() {
                return OTHERNAME;
            }

            public void setOTHERNAME(String OTHERNAME) {
                this.OTHERNAME = OTHERNAME;
            }
        }

        public static class ZTJJInfoBean {
            /**
             * TTYPE : ed197e208a437da2
             * TTYPENAME : 富时概念
             */

            private String TTYPE;
            private String TTYPENAME;

            public String getTTYPE() {
                return TTYPE;
            }

            public void setTTYPE(String TTYPE) {
                this.TTYPE = TTYPE;
            }

            public String getTTYPENAME() {
                return TTYPENAME;
            }

            public void setTTYPENAME(String TTYPENAME) {
                this.TTYPENAME = TTYPENAME;
            }
        }
    }
}

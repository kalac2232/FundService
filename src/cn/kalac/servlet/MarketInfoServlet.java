package cn.kalac.servlet;

import cn.kalac.bean.ResultBean;
import cn.kalac.http.HttpHelper;
import cn.kalac.http.ResultCode;
import cn.kalac.http.ResultHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MarketInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {

        //long l = System.currentTimeMillis();
        String result = HttpHelper.
                doGet("http://push2.eastmoney.com/api/qt/ulist.np/get?secids=1.000001,0.399001,0.399006&fields=f2,f3,f4,f12,f14");

        List<MarketInfo> list = packageJson(result);

        if (list == null || list.isEmpty()) {
            ResultHandler.onResult(response,new ResultBean(ResultCode.ERROR,"covert data error"));
        } else {
            ResultHandler.onResult(response,new ResultBean(ResultCode.SUCCESS,list));
        }
        //System.out.println("耗时" + (System.currentTimeMillis() - l));
    }

    private List<MarketInfo> packageJson(String json) {

        TianTianInfo tianTianInfo = new Gson().fromJson(json, TianTianInfo.class);
        ArrayList<MarketInfo> marketInfos = new ArrayList<>();
        for (TianTianInfo.DataBean.DiffBean diffBean : tianTianInfo.data.diff) {
            marketInfos.add(new MarketInfo(diffBean.f14,diffBean.f2 / 100d,diffBean.f4 / 100d,diffBean.f3 / 100d,diffBean.f12));
        }

        return marketInfos;
    }


    class MarketInfo{
        String name;
        /**
         * 当前指数
         */
        double currentIndex;
        double floatNum;
        double floatPercent;
        String code;

        public MarketInfo(String name, double currentIndex, double floatNum, double floatPercent, String code) {
            this.name = name;
            this.currentIndex = currentIndex;
            this.floatNum = floatNum;
            this.floatPercent = floatPercent;
            this.code = code;
        }
    }

    class TianTianInfo {

        private int rc;
        private int rt;
        private int lt;
        private int full;
        private DataBean data;

        public int getRc() {
            return rc;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public int getRt() {
            return rt;
        }

        public void setRt(int rt) {
            this.rt = rt;
        }

        public int getLt() {
            return lt;
        }

        public void setLt(int lt) {
            this.lt = lt;
        }

        public int getFull() {
            return full;
        }

        public void setFull(int full) {
            this.full = full;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        class DataBean {
            /**
             * total : 3
             * diff : [{"f2":297653,"f3":-275,"f4":-8422,"f12":"000001","f14":"上证指数"},{"f2":1068190,"f3":-352,"f4":-39016,"f12":"399001","f14":"深证成指"},{"f2":192774,"f3":-332,"f4":-6616,"f12":"399006","f14":"创业板指"}]
             */

            private int total;
            private List<DiffBean> diff;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public List<DiffBean> getDiff() {
                return diff;
            }

            public void setDiff(List<DiffBean> diff) {
                this.diff = diff;
            }

            class DiffBean {
                /**
                 * f2 : 297653
                 * f3 : -275
                 * f4 : -8422
                 * f12 : 000001
                 * f14 : 上证指数
                 */

                private int f2;
                private int f3;
                private int f4;
                private String f12;
                private String f14;

                public int getF2() {
                    return f2;
                }

                public void setF2(int f2) {
                    this.f2 = f2;
                }

                public int getF3() {
                    return f3;
                }

                public void setF3(int f3) {
                    this.f3 = f3;
                }

                public int getF4() {
                    return f4;
                }

                public void setF4(int f4) {
                    this.f4 = f4;
                }

                public String getF12() {
                    return f12;
                }

                public void setF12(String f12) {
                    this.f12 = f12;
                }

                public String getF14() {
                    return f14;
                }

                public void setF14(String f14) {
                    this.f14 = f14;
                }
            }
        }
    }

}

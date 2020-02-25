package cn.kalac.servlet;

import cn.kalac.bean.ResultBean;
import cn.kalac.http.HttpHelper;
import cn.kalac.http.ResultHandler;
import cn.kalac.utils.JDBCUtils;
import cn.kalac.utils.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "FundRealTimeValuationServlet",urlPatterns = "/FundRealTimeValuationServlet")
public class FundRealTimeValuationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");

        if (StringUtil.isEmpty(userId)) {
            ResultHandler.onError(response,"userId is empty");
            return;
        }

        //获取基本的基金列表
        ArrayList<FundRealTimeValuation> fundRealTimeValuations = new ArrayList<>();
        fillFundList(fundRealTimeValuations,userId);
        //获取实时估值
        for (FundRealTimeValuation fundRealTimeValuation : fundRealTimeValuations) {
            String result = HttpHelper.doGet("http://fundgz.1234567.com.cn/js/"+ fundRealTimeValuation.code +".js");
            String json = result.substring(8,result.length()-4);

            if(StringUtil.isEmpty(json)) {
                fundRealTimeValuation.realTimeValuation = 0.0;
            } else {
                Type type = new TypeToken<HashMap<String, Object>>() {}.getType();
                HashMap hashMap = new Gson().fromJson(json, type);
                fundRealTimeValuation.realTimeValuation = Double.parseDouble((String) hashMap.get("gszzl"));
            }

        }


        ResultHandler.onResult(response,new ResultBean(fundRealTimeValuations));

    }

    private void fillFundList(ArrayList<FundRealTimeValuation> fundRealTimeValuations,String userId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = JDBCUtils.getConnection();
            String sql = "select fund_code,fund_name,remarks from table_user_fund where user_Id = ?";
            //获取执行sql的对象
            statement = connection.prepareStatement(sql);
            statement.setString(1,userId);

            //执行sql
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                FundRealTimeValuation fundRealTimeValuation = new FundRealTimeValuation();
                fundRealTimeValuation.code = resultSet.getString(1);
                fundRealTimeValuation.name = resultSet.getString(2);
                fundRealTimeValuation.remarks = resultSet.getString(3);
                fundRealTimeValuations.add(fundRealTimeValuation);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement,connection);
        }

    }

    class FundRealTimeValuation{
        String code;
        String name;
        String remarks;
        double realTimeValuation;
        //今日盈利
        double todayProfit = 0;
        //总盈利
        double totalProfit = 0;
    }

}

package cn.kalac.servlet;

import cn.kalac.bean.ResultBean;
import cn.kalac.bean.TTFundSearchResult;
import cn.kalac.http.HttpHelper;
import cn.kalac.http.ResultHandler;
import cn.kalac.utils.JDBCUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(name = "InsertFundServlet",urlPatterns = "/InsertFundServlet")
public class InsertFundServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fundCode = request.getParameter("fundCode");
        String userId = request.getParameter("userId");


        if (userId == null || userId.isEmpty()) {
            ResultHandler.onError(response,"userId is empty");
            return;
        }

        if (fundCode == null || fundCode.isEmpty()) {
            ResultHandler.onError(response,"fundCode is empty");
            return;
        }
        //查询是否已经存在就不用添加了
        if (isExisted(userId, fundCode)) {
            ResultHandler.onError(response,"fundCode is already add");
            return;
        }


        String url = "http://fundsuggest.eastmoney.com/FundSearch/api/FundSearchAPI.ashx?m=1&key=" + URLEncoder.encode(fundCode,"utf-8");
        String result = HttpHelper.doGet(url);

        TTFundSearchResult ttFundSearchResult;
        try {
            ttFundSearchResult = new Gson().fromJson(result, TTFundSearchResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            ResultHandler.onError(response,"format is empty");
            return;
        }

        TTFundSearchResult.DatasBean bean = ttFundSearchResult.getDatas().get(0);


        int i = insertFund(userId, fundCode,bean == null ? "":bean.getNAME());
        if (i == 0) {
            ResultHandler.onError(response,"insert error");
        } else {
            ResultHandler.onResult(response,new ResultBean("insert success"));
        }

    }

    private boolean isExisted(String userId, String fundCode) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean isExisted = false;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select * from table_user_fund where user_Id = ? and fund_code = ?";
            //获取执行sql的对象
            statement = connection.prepareStatement(sql);
            statement.setString(1,userId);
            statement.setString(2,fundCode);

            //执行sql
            isExisted = statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement,connection);
        }

        return isExisted;
    }


    private int insertFund(String userId, String fundCode,String fundName) {
        Connection connection = null;
        PreparedStatement statement = null;
        int count = 0;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into table_user_fund(user_Id,fund_code,fund_name) values(?,?,?)";
            //获取执行sql的对象
            statement = connection.prepareStatement(sql);
            statement.setString(1,userId);
            statement.setString(2,fundCode);
            statement.setString(3,fundName);

            //执行sql
            count = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement,connection);
        }

        return count;
    }
}

package cn.kalac.servlet;

import cn.kalac.bean.FundSearchResult;
import cn.kalac.bean.ResultBean;
import cn.kalac.bean.TTFundSearchResult;
import cn.kalac.http.HttpHelper;
import cn.kalac.http.ResultCode;
import cn.kalac.http.ResultHandler;
import cn.kalac.utils.JDBCUtils;
import cn.kalac.utils.StringUtil;
import cn.kalac.utils.TokenUtil;
import com.google.gson.Gson;

import javax.naming.directory.SearchResult;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FundSearchServlet",urlPatterns = "/FundSearchServlet")
public class FundSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        String userId = request.getParameter("userId");

        if (StringUtil.isEmpty(key)) {
            ResultHandler.onError(response,"key is empty");
            return;
        }

        String url = "http://fundsuggest.eastmoney.com/FundSearch/api/FundSearchAPI.ashx?m=1&key=" + URLEncoder.encode(key,"utf-8");

        String result = HttpHelper.doGet(url);
        TTFundSearchResult ttFundSearchResult;
        try {
            ttFundSearchResult = new Gson().fromJson(result, TTFundSearchResult.class);
        } catch (Exception e) {
            e.printStackTrace();
            ResultHandler.onError(response,"format is empty");
            return;
        }


        List<FundSearchResult> searchResults = new ArrayList<>();

        for (TTFundSearchResult.DatasBean data : ttFundSearchResult.getDatas()) {

            if (!data.getCATEGORYDESC().contains("基金")) {
                break;
            }
            FundSearchResult fundSearchResult = new FundSearchResult(data.getNAME(), data.getCODE());

            if (!StringUtil.isEmpty(userId)) {
                //查询该基金是否已经被该用户添加
                fundSearchResult.setAdd(isFundCodeExisted(userId,fundSearchResult.getCode()));
            }
            searchResults.add(fundSearchResult);
        }

        ResultHandler.onResult(response,new ResultBean(ResultCode.SUCCESS,searchResults));
    }


    private boolean isFundCodeExisted(String userId, String fundCode) {
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
}

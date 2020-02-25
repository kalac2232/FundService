package cn.kalac.servlet;

import cn.kalac.bean.ResultBean;
import cn.kalac.http.HttpHelper;
import cn.kalac.http.ResultCode;
import cn.kalac.http.ResultHandler;
import cn.kalac.utils.JDBCUtils;
import cn.kalac.utils.StringUtil;
import cn.kalac.utils.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

@WebServlet(name = "LoginServlet" ,urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String passwordMd5 = request.getParameter("passwordMd5");


        if (userName == null || userName.isEmpty()) {
            ResultHandler.onError(response,"userName is empty");
            return;
        }

        if (passwordMd5 == null || passwordMd5.isEmpty()) {
            ResultHandler.onError(response,"passwordMd5 is empty");
            return;
        }
        //查询是否存在该用户
        String userId = queryUserId(userName);

        if (userId == null || userId.isEmpty()) {
            //没有该用户则自动注册
            userId = register(userName, passwordMd5);
        } else {
            userId = UserLogin(userName,passwordMd5);

            if (StringUtil.isEmpty(userId)) {
                ResultHandler.onError(response,"password is error");
                return;
            }
        }

        ResultHandler.onResult(response,new ResultBean(ResultCode.SUCCESS,new TempUser(userId, TokenUtil.generateToken())));

    }



    private String register(String userName, String passwordMd5) {
        Connection connection = null;
        PreparedStatement statement = null;
        String userId = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "insert into table_user(user_id,user_name,password_md5) values(?,?,?)";
            //获取执行sql的对象
            statement = connection.prepareStatement(sql);
            userId = UUID.randomUUID().toString().replace("-","");
            statement.setString(1,userId );
            statement.setString(2,userName);
            statement.setString(3,passwordMd5);

            //执行sql
            int count = statement.executeUpdate();
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(statement,connection);
        }

        return userId;
    }

    private String queryUserId(String userName) {
        Connection connection = null;
        PreparedStatement statement = null;
        String user_id = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select user_id from table_user where user_name = ?";
            //获取执行sql的对象
            statement = connection.prepareStatement(sql);

            statement.setString(1,userName);
            //statement.setString(2,passwordMd5);

            //执行sql
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user_id = resultSet.getString("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(resultSet,statement,connection);
        }

        return user_id;
    }

    private String UserLogin(String userName,String passwordMd5) {
        Connection connection = null;
        PreparedStatement statement = null;
        String user_id = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql = "select user_id from table_user where user_name = ? and password_md5 = ?";
            //获取执行sql的对象
            statement = connection.prepareStatement(sql);

            statement.setString(1,userName);
            statement.setString(2,passwordMd5);

            //执行sql
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user_id = resultSet.getString("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(resultSet,statement,connection);
        }

        return user_id;
    }


    class TempUser {
        String userId;
        String token;

        public TempUser(String userId, String token) {
            this.userId = userId;
            this.token = token;
        }
    }
}
